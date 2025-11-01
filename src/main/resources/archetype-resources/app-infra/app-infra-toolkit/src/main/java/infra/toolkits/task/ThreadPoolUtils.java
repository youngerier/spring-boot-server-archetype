#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.task;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程池工具类
 * 提供多种预配置的线程池和统一的管理功能
 * 
 * @author qbit
 */
@Slf4j
public class ThreadPoolUtils {
    
    /**
     * 线程池注册表，用于统一管理所有线程池
     */
    private static final Map<String, ExecutorService> THREAD_POOLS = new ConcurrentHashMap<>();
    
    /**
     * 默认线程池配置
     */
    private static final int DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_MAX_POOL_SIZE = DEFAULT_CORE_POOL_SIZE * 2;
    private static final long DEFAULT_KEEP_ALIVE_TIME = 60L;
    private static final int DEFAULT_QUEUE_CAPACITY = 1000;
    
    /**
     * 创建固定大小的线程池
     * 
     * @param poolName 线程池名称
     * @param nThreads 线程数量
     * @return ExecutorService
     */
    public static ExecutorService createFixedThreadPool(String poolName, int nThreads) {
        ThreadFactory threadFactory = createNamedThreadFactory(poolName);
        ExecutorService executor = Executors.newFixedThreadPool(nThreads, threadFactory);
        registerThreadPool(poolName, executor);
        log.info("Created fixed thread pool: {} with {} threads", poolName, nThreads);
        return executor;
    }
    
    /**
     * 创建缓存线程池
     * 
     * @param poolName 线程池名称
     * @return ExecutorService
     */
    public static ExecutorService createCachedThreadPool(String poolName) {
        ThreadFactory threadFactory = createNamedThreadFactory(poolName);
        ExecutorService executor = Executors.newCachedThreadPool(threadFactory);
        registerThreadPool(poolName, executor);
        log.info("Created cached thread pool: {}", poolName);
        return executor;
    }
    
    /**
     * 创建单线程池
     * 
     * @param poolName 线程池名称
     * @return ExecutorService
     */
    public static ExecutorService createSingleThreadPool(String poolName) {
        ThreadFactory threadFactory = createNamedThreadFactory(poolName);
        ExecutorService executor = Executors.newSingleThreadExecutor(threadFactory);
        registerThreadPool(poolName, executor);
        log.info("Created single thread pool: {}", poolName);
        return executor;
    }
    
    /**
     * 创建自定义线程池
     * 
     * @param poolName 线程池名称
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 线程空闲时间
     * @param unit 时间单位
     * @param queueCapacity 队列容量
     * @return ThreadPoolExecutor
     */
    public static ThreadPoolExecutor createCustomThreadPool(String poolName,
                                                           int corePoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime,
                                                           TimeUnit unit,
                                                           int queueCapacity) {
        ThreadFactory threadFactory = createNamedThreadFactory(poolName);
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                // 拒绝策略：调用者运行
                new ThreadPoolExecutor.CallerRunsPolicy() 
        );
        
        registerThreadPool(poolName, executor);
        log.info("Created custom thread pool: {} [core={}, max={}, keepAlive={}{}]", 
                poolName, corePoolSize, maximumPoolSize, keepAliveTime, unit);
        return executor;
    }
    
    /**
     * 创建默认配置的线程池
     * 
     * @param poolName 线程池名称
     * @return ThreadPoolExecutor
     */
    public static ThreadPoolExecutor createDefaultThreadPool(String poolName) {
        return createCustomThreadPool(
                poolName,
                DEFAULT_CORE_POOL_SIZE,
                DEFAULT_MAX_POOL_SIZE,
                DEFAULT_KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                DEFAULT_QUEUE_CAPACITY
        );
    }
    
    /**
     * 创建定时任务线程池
     * 
     * @param poolName 线程池名称
     * @param corePoolSize 核心线程数
     * @return ScheduledExecutorService
     */
    public static ScheduledExecutorService createScheduledThreadPool(String poolName, int corePoolSize) {
        ThreadFactory threadFactory = createNamedThreadFactory(poolName);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(corePoolSize, threadFactory);
        registerThreadPool(poolName, executor);
        log.info("Created scheduled thread pool: {} with {} threads", poolName, corePoolSize);
        return executor;
    }
    
    /**
     * 获取已注册的线程池
     * 
     * @param poolName 线程池名称
     * @return ExecutorService
     */
    public static ExecutorService getThreadPool(String poolName) {
        return THREAD_POOLS.get(poolName);
    }
    
    /**
     * 关闭指定的线程池
     * 
     * @param poolName 线程池名称
     * @param timeout 超时时间
     * @param unit 时间单位
     */
    public static void shutdownThreadPool(String poolName, long timeout, TimeUnit unit) {
        ExecutorService executor = THREAD_POOLS.remove(poolName);
        if (executor != null) {
            shutdownExecutor(executor, poolName, timeout, unit);
        }
    }
    
    /**
     * 关闭所有线程池
     * 
     * @param timeout 超时时间
     * @param unit 时间单位
     */
    public static void shutdownAllThreadPools(long timeout, TimeUnit unit) {
        log.info("Shutting down all thread pools...");
        THREAD_POOLS.forEach((name, executor) -> {
            shutdownExecutor(executor, name, timeout, unit);
        });
        THREAD_POOLS.clear();
        log.info("All thread pools have been shut down");
    }
    
    /**
     * 获取线程池状态信息
     * 
     * @param poolName 线程池名称
     * @return ThreadPoolStatus
     */
    public static ThreadPoolStatus getThreadPoolStatus(String poolName) {
        ExecutorService executor = THREAD_POOLS.get(poolName);
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) executor;
            return new ThreadPoolStatus(
                    poolName,
                    tpe.getCorePoolSize(),
                    tpe.getMaximumPoolSize(),
                    tpe.getActiveCount(),
                    tpe.getPoolSize(),
                    tpe.getQueue().size(),
                    tpe.getCompletedTaskCount(),
                    tpe.getTaskCount(),
                    tpe.isShutdown(),
                    tpe.isTerminated()
            );
        }
        return null;
    }
    
    /**
     * 打印所有线程池状态
     */
    public static void printAllThreadPoolStatus() {
        log.info("=== Thread Pool Status ===");
        THREAD_POOLS.forEach((name, executor) -> {
            ThreadPoolStatus status = getThreadPoolStatus(name);
            if (status != null) {
                log.info("{}", status);
            } else {
                log.info("Pool: {} - Type: {}", name, executor.getClass().getSimpleName());
            }
        });
        log.info("=========================");
    }
    
    /**
     * 创建命名线程工厂
     * 
     * @param poolName 线程池名称
     * @return ThreadFactory
     */
    private static ThreadFactory createNamedThreadFactory(String poolName) {
        return new NamedThreadFactory(poolName);
    }
    
    /**
     * 注册线程池
     * 
     * @param poolName 线程池名称
     * @param executor 线程池实例
     */
    private static void registerThreadPool(String poolName, ExecutorService executor) {
        THREAD_POOLS.put(poolName, executor);
    }
    
    /**
     * 关闭线程池
     * 
     * @param executor 线程池实例
     * @param poolName 线程池名称
     * @param timeout 超时时间
     * @param unit 时间单位
     */
    private static void shutdownExecutor(ExecutorService executor, String poolName, long timeout, TimeUnit unit) {
        try {
            log.info("Shutting down thread pool: {}", poolName);
            executor.shutdown();
            if (!executor.awaitTermination(timeout, unit)) {
                log.warn("Thread pool {} did not terminate gracefully, forcing shutdown", poolName);
                executor.shutdownNow();
                if (!executor.awaitTermination(timeout, unit)) {
                    log.error("Thread pool {} did not terminate after forced shutdown", poolName);
                }
            }
            log.info("Thread pool {} has been shut down successfully", poolName);
        } catch (InterruptedException e) {
            log.error("Interrupted while shutting down thread pool: {}", poolName, e);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 命名线程工厂
     */
    private static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        
        public NamedThreadFactory(String poolName) {
            this.namePrefix = poolName + "-thread-";
        }
        
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, namePrefix + threadNumber.getAndIncrement());
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }
    
    /**
     * 线程池状态信息
     */
    @Getter
    public static class ThreadPoolStatus {
        private final String poolName;
        private final int corePoolSize;
        private final int maximumPoolSize;
        private final int activeCount;
        private final int poolSize;
        private final int queueSize;
        private final long completedTaskCount;
        private final long taskCount;
        private final boolean isShutdown;
        private final boolean isTerminated;
        
        public ThreadPoolStatus(String poolName, int corePoolSize, int maximumPoolSize,
                              int activeCount, int poolSize, int queueSize,
                              long completedTaskCount, long taskCount,
                              boolean isShutdown, boolean isTerminated) {
            this.poolName = poolName;
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.activeCount = activeCount;
            this.poolSize = poolSize;
            this.queueSize = queueSize;
            this.completedTaskCount = completedTaskCount;
            this.taskCount = taskCount;
            this.isShutdown = isShutdown;
            this.isTerminated = isTerminated;
        }

        @Override
        public String toString() {
            return String.format(
                "Pool: %s [core=%d, max=%d, active=%d, pool=%d, queue=%d, completed=%d, total=%d, shutdown=%s, terminated=%s]",
                poolName, corePoolSize, maximumPoolSize, activeCount, poolSize, 
                queueSize, completedTaskCount, taskCount, isShutdown, isTerminated
            );
        }
    }
}