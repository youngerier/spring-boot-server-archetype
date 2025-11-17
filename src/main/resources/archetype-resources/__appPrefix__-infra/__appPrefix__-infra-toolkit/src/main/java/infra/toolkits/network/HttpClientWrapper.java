#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.network;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HTTP客户端包装类，提供常用的HTTP请求方法和配置
 */
@Slf4j
public class HttpClientWrapper {

    /**
     * -- GETTER --
     *  获取底层RestTemplate实例
     *
     * @return RestTemplate实例
     */
    @Getter
    private final RestTemplate restTemplate;
    private final HttpClientConfig config;

    /**
     * 默认构造函数，使用默认配置
     */
    public HttpClientWrapper() {
        this(new HttpClientConfig());
    }

    /**
     * 使用自定义配置构造HTTP客户端
     *
     * @param config 客户端配置
     */
    public HttpClientWrapper(HttpClientConfig config) {
        this.config = config;
        this.restTemplate = createRestTemplate();
    }

    /**
     * 创建并配置RestTemplate
     */
    private RestTemplate createRestTemplate() {
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(createJdkRequestFactory());
        RestTemplate template = new RestTemplate(requestFactory);

        // 设置自定义错误处理器，以防止在4xx/5xx响应时抛出异常
        template.setErrorHandler(new CustomResponseErrorHandler());

        // 添加请求/响应日志拦截器
        if (config.isEnableLogging()) {
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
            interceptors.add(new LoggingInterceptor());
            template.setInterceptors(interceptors);
        }

        return template;
    }

    /**
     * 创建一个JdkClientHttpRequestFactory实例
     */
    private ClientHttpRequestFactory createJdkRequestFactory() {
        // 配置并创建 HttpClient 实例
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(config.getConnectTimeoutMillis()))
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        // 使用配置好的 HttpClient 创建请求工厂
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofMillis(config.getReadTimeoutMillis()));
        return factory;
    }

    /**
     * 执行GET请求
     *
     * @param url 请求URL
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T get(String url, Class<T> responseType) {
        return get(url, null, null, responseType);
    }

    /**
     * 执行GET请求，支持查询参数
     *
     * @param url 请求URL
     * @param queryParams 查询参数
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T get(String url, Map<String, String> queryParams, Class<T> responseType) {
        return get(url, queryParams, null, responseType);
    }

    /**
     * 执行GET请求，支持查询参数和请求头
     *
     * @param url 请求URL
     * @param queryParams 查询参数
     * @param headers 请求头
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T get(String url, Map<String, String> queryParams, HttpHeaders headers, Class<T> responseType) {
        URI uri = buildUri(url, queryParams);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * 执行GET请求，返回复杂泛型类型
     *
     * @param url 请求URL
     * @param queryParams 查询参数
     * @param headers 请求头
     * @param responseType 响应类型引用
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T get(String url, Map<String, String> queryParams, HttpHeaders headers,
                     ParameterizedTypeReference<T> responseType) {
        URI uri = buildUri(url, queryParams);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * 执行POST请求，发送JSON数据
     *
     * @param url 请求URL
     * @param requestBody 请求体
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T post(String url, Object requestBody, Class<T> responseType) {
        return post(url, requestBody, null, responseType);
    }

    /**
     * 执行POST请求，发送JSON数据，支持自定义请求头
     *
     * @param url 请求URL
     * @param requestBody 请求体
     * @param headers 请求头
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T post(String url, Object requestBody, HttpHeaders headers, Class<T> responseType) {
        HttpHeaders requestHeaders = headers != null ? headers : new HttpHeaders();
        if (requestHeaders.getContentType() == null) {
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * 执行POST请求，发送表单数据
     *
     * @param url 请求URL
     * @param formData 表单数据
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T postForm(String url, Map<String, String> formData, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (formData != null) {
            formData.forEach(map::add);
        }

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * 执行PUT请求
     *
     * @param url 请求URL
     * @param requestBody 请求体
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T put(String url, Object requestBody, Class<T> responseType) {
        return put(url, requestBody, null, responseType);
    }

    /**
     * 执行PUT请求，支持自定义请求头
     *
     * @param url 请求URL
     * @param requestBody 请求体
     * @param headers 请求头
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T put(String url, Object requestBody, HttpHeaders headers, Class<T> responseType) {
        HttpHeaders requestHeaders = headers != null ? headers : new HttpHeaders();
        if (requestHeaders.getContentType() == null) {
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * 执行DELETE请求
     *
     * @param url 请求URL
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T delete(String url, Class<T> responseType) {
        return delete(url, null, responseType);
    }

    /**
     * 执行DELETE请求，支持自定义请求头
     *
     * @param url 请求URL
     * @param headers 请求头
     * @param responseType 响应类型
     * @param <T> 响应类型泛型
     * @return 响应结果
     */
    public <T> T delete(String url, HttpHeaders headers, Class<T> responseType) {
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * 构建请求URI，包含查询参数
     *
     * @param url 基础URL
     * @param queryParams 查询参数
     * @return 构建好的URI
     */
    private URI buildUri(String url, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (!CollectionUtils.isEmpty(queryParams)) {
            queryParams.forEach(builder::queryParam);
        }
        return builder.build().encode().toUri();
    }

    /**
     * 自定义错误处理器
     */
    private static class CustomResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            // 返回 false, 表示无论响应状态码是什么（4xx 或 5xx），都不抛出异常
            // 这样我们就可以在业务代码中获取到完整的响应实体，包括响应头和响应体
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // hasError 返回 false，这个方法将不会被调用
            log.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());
        }
    }

    /**
     * 日志拦截器，记录请求和响应信息
     */
    private static class LoggingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            // 记录请求和响应信息
            log.info("HTTP Request: {} {}${symbol_escape}nHeaders: {}${symbol_escape}nBody: {}",
                    request.getMethod(),
                    request.getURI(),
                    request.getHeaders(),
                    new String(body, StandardCharsets.UTF_8));

            long startTime = System.nanoTime();
            ClientHttpResponse response = execution.execute(request, body);
            long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

            log.info("HTTP Response:${symbol_escape}nStatus: {}${symbol_escape}nTime: {} ms",
                    response.getStatusCode(),
                    duration);

            return response;
        }
    }

    /**
     * HTTP客户端配置类
     */
    @Data
    public static class HttpClientConfig {
        private long connectTimeoutMillis = 5000;
        private long readTimeoutMillis = 5000;
        private boolean enableLogging = true;
        private int maxConnTotal = 100;
        private int maxConnPerRoute = 20;

        public HttpClientConfig() {
            // 默认构造函数
        }

        /**
         * 启用请求和响应日志记录
         *
         * @return 当前配置实例，用于链式调用
         */
        public HttpClientConfig enableLogging() {
            this.enableLogging = true;
            return this;
        }

        /**
         * 设置连接超时时间
         *
         * @param timeout 超时时间
         * @param unit 时间单位
         * @return 当前配置实例，用于链式调用
         */
        public HttpClientConfig connectTimeout(long timeout, TimeUnit unit) {
            this.connectTimeoutMillis = unit.toMillis(timeout);
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param timeout 超时时间
         * @param unit 时间单位
         * @return 当前配置实例，用于链式调用
         */
        public HttpClientConfig readTimeout(long timeout, TimeUnit unit) {
            this.readTimeoutMillis = unit.toMillis(timeout);
            return this;
        }
    }
}
