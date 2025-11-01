#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * ┌────────────────────────────────────────────┐
 * │               主线程提交任务              │
 * │  submit(Callable<T>, retries, delay)      │
 * └────────────────────────────────────────────┘
 * │
 * ▼
 * ┌─────────────────────────────┐
 * │  封装 TaskWrapper 对象       │
 * │ (包含 Callable + Future + 重试参数) │
 * └─────────────────────────────┘
 * │
 * ▼
 * ┌─────────────────────────────┐
 * │ 入队到 LinkedBlockingDeque  │◄───────┐
 * └─────────────────────────────┘        │
 * │                        │（等待重试任务重新入队）
 * ▼                        │
 * ┌──────────────────────────────────────┐
 * │     Dispatcher Thread 从队列取任务    │
 * └──────────────────────────────────────┘
 * │
 * ▼
 * ┌────────────────────────────┐
 * │ 提交给线程池 Executor 执行 │
 * └────────────────────────────┘
 * │
 * ▼
 * ┌────────────────────────────────────────────┐
 * │        执行 task.call() (Callable<T>)      │
 * └────────────────────────────────────────────┘
 * │                         │
 * ▼                         ▼
 * ┌──────────────┐        ┌──────────────────────┐
 * │   成功执行   │        │    异常抛出/失败     │
 * └──────────────┘        └──────────────────────┘
 * │                         │
 * ▼                         ▼
 * ┌────────────────────┐   ┌────────────────────────────────────┐
 * │ future.complete(v) │   │ retryCount < maxRetries ?          │
 * └────────────────────┘   │   → Thread.sleep(retryDelay)       │
 * │   → 重新放入队列执行               │
 * │ 否则 future.completeExceptionally  │
 * └────────────────────────────────────┘
 */
@Slf4j
public class AsyncTaskProcessor {
    private final LinkedBlockingDeque<TaskWrapper<?>> taskQueue = new LinkedBlockingDeque<>();
    private final ExecutorService executor;
    private final Thread dispatcherThread;
    private volatile boolean running = true;

    public AsyncTaskProcessor(int workerThreads) {
        this.executor = Executors.newFixedThreadPool(workerThreads);

        this.dispatcherThread = new Thread(() -> {
            while (running || !taskQueue.isEmpty()) {
                try {
                    TaskWrapper<?> taskWrapper = taskQueue.take();
                    executor.submit(() -> taskWrapper.execute(taskQueue));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "async-dispatcher");

        this.dispatcherThread.setDaemon(true);
        this.dispatcherThread.start();
    }

    public <T> CompletableFuture<T> submit(
            Callable<T> task,
            int maxRetries,
            long retryDelayMillis
    ) {
        CompletableFuture<T> future = new CompletableFuture<>();
        TaskWrapper<T> wrapper = new TaskWrapper<>(task, future, maxRetries, retryDelayMillis);
        taskQueue.offer(wrapper);
        return future;
    }

    public void shutdown() {
        running = false;
        dispatcherThread.interrupt();
        executor.shutdown();
    }

    private static class TaskWrapper<T> {
        private final Callable<T> task;
        private final CompletableFuture<T> future;
        private final int maxRetries;
        private final long retryDelayMillis;
        private int retryCount = 0;

        public TaskWrapper(Callable<T> task, CompletableFuture<T> future, int maxRetries, long retryDelayMillis) {
            this.task = task;
            this.future = future;
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        public void execute(BlockingQueue<TaskWrapper<?>> queue) {
            try {
                T result = task.call();
                future.complete(result);
            } catch (Exception e) {
                retryCount++;
                if (retryCount <= maxRetries) {
                    log.info("Retry {} due to: {}", retryCount, e.getMessage());
                    try {
                        Thread.sleep(retryDelayMillis);
                    } catch (InterruptedException ignored) {
                    }
                    queue.offer(this);
                } else {
                    future.completeExceptionally(e);
                }
            }
        }
    }
}
