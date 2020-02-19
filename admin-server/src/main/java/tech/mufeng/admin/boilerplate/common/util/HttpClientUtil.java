package tech.mufeng.admin.boilerplate.common.util;

import lombok.Cleanup;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpClientUtil {

    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 连接存活时间，单位毫秒
    private static final int CONNECT_TIME_TO_LIVE = 30000;

    // 连接池限制
    private static final int CONNECTION_POOL_MAX = 200;

    // preRouter限制
    private static final int PER_ROUTE_MAX = 200;

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;

    private static CloseableHttpClient httpClient = initHttpClient();

    // 初始化client实例
    private static CloseableHttpClient initHttpClient() {
        return HttpClients
                .custom()
                .setDefaultRequestConfig(customRequestConfig()) // 默认请求配置
                .setConnectionManager(poolingHttpClientConnectionManager()) // 自定义连接管理器
                .evictIdleConnections(CONNECT_TIME_TO_LIVE, TimeUnit.MICROSECONDS) // 删除空闲连接时间
                .disableAutomaticRetries() // 关闭自动重试
                .build();
    }

    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager(CONNECT_TIME_TO_LIVE, TimeUnit.MICROSECONDS);
        connectionManager.setMaxTotal(CONNECTION_POOL_MAX);
        connectionManager.setDefaultMaxPerRoute(PER_ROUTE_MAX);
        return connectionManager;
    }

    private static RequestConfig customRequestConfig() {
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        return RequestConfig
                .custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
    }

    // 封装的响应体
    @Data
    public static class HttpClientResult implements Serializable {
        private static final long serialVersionUID = -7421171673609899440L;
        /**
         * 响应状态码
         */
        private int code;

        /**
         * 响应数据
         */
        private String content;

        public HttpClientResult(int code, String content) {
            this.code = code;
            this.content = content;
            requestLog(code, content);
        }

        public HttpClientResult(int code) {
            this.code = code;
            this.content = null;
            requestLog(code, null);
        }

        private void requestLog(int code, String content) {
            if (code > 200 && code < 400) {
                log.warn("HTTP WARNING code={}; content={}", code, content);
            }else {
                log.error("HTTP ERROR code={}; content={}", code, content);
            }
        }
    }

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url) throws Exception {
        return doGetWithQuery(url, null);
    }

    // 发送get请求；带请求参数
    public static HttpClientResult doGetWithQuery(String url, Map<String, String> query) throws Exception {
        return doGetWithHeaderAndQuery(url, null, query);
    }

    // 发送get请求；带请求头和请求参数
    public static HttpClientResult doGetWithHeaderAndQuery(String url, Map<String, String> headers, Map<String, String> query) throws Exception {
        // 创建http对象
        HttpGet httpGet = new HttpGet();
        return doRequestWithQuery(httpGet, url, headers, query);
    }

    // 发送post请求；不带请求头和请求参数
    public static HttpClientResult doPost(String url) throws Exception {
        return doPost(url, null, null, null, null);
    }

    // 发送post请求；带请求参数
    public static HttpClientResult doPostWithUrlEncodeForm(String url, Map<String, String> params) throws Exception {
        return doPost(url, null, null, params, null);
    }

    // 普通json的post请求
    public static HttpClientResult doPostWithBody(String url, String body) throws Exception {
        return doPost(url, null, null, null, body);
    }

    // 带请求头的json的post请求
    public static HttpClientResult doPostWithHeaderAndBody(String url, Map<String, String> headers, String body) throws Exception {
        return doPost(url, headers, null, null, body);
    }

    // 发送post请求；带请求头和请求参数
    public static HttpClientResult doPost(
            String url,
            Map<String, String> headers,
            Map<String, String> query,
            Map<String, String> params,
            String body
    ) throws Exception {
        // 创建http对象
        HttpPost httpPost = new HttpPost();
        return doRequestWithEntity(httpPost, url, headers, query, params, body);
    }

    // 发送put请求；不带请求参数
    public static HttpClientResult doPut(String url) throws Exception {
        return doPut(url, null, null, null, null);
    }

    public static HttpClientResult doPutWithBody(String url, String body) throws Exception {
        return doPut(url, null, null, null, null);
    }

    public static HttpClientResult doPutWithHeaderAndBody(
            String url, Map<String, String> headers, String body) throws Exception {
        return doPut(url, headers, null, null, null);
    }

    // 发送put请求；带请求参数
    public static HttpClientResult doPut(
            String url,
            Map<String, String> headers,
            Map<String, String> query,
            Map<String, String> params,
            String body
    ) throws Exception {
        HttpPut httpPut = new HttpPut(url);
        return doRequestWithEntity(httpPut, url, headers, query, params, body);
    }

    // 发送delete请求；不带请求参数
    public static HttpClientResult doDelete(String url) throws Exception {
        HttpDelete httpDelete = new HttpDelete(url);
        return doRequestWithQuery(httpDelete, url, null, null);
    }

    public static HttpClientResult doDeleteWithQuery(String url, Map<String, String> query) throws Exception{
        HttpDelete httpDelete = new HttpDelete(url);
        return doRequestWithQuery(httpDelete, url, null, query);
    }

    public static HttpClientResult doDeleteWithHeaderAndQuery(String url, Map<String, String> header, Map<String, String> query) throws Exception{
        HttpDelete httpDelete = new HttpDelete(url);
        return doRequestWithQuery(httpDelete, url, header, query);
    }

    private static void packageURI(
            String url, Map<String, String> params, HttpRequestBase httpRequestBase) throws URISyntaxException{
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        httpRequestBase.setURI(uriBuilder.build());
    }

    private static HttpClientResult doRequestWithQuery(
            HttpRequestBase httpRequestBase,
            String url,
            Map<String, String> headers,
            Map<String, String> query
    ) throws Exception {
        packageHeader(headers, httpRequestBase);
        packageURI(url, query, httpRequestBase);
        return getHttpClientResult(httpRequestBase);
    }

    private static HttpClientResult doRequestWithEntity(
            HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase,
            String url,
            Map<String, String> headers,
            Map<String, String> query,
            Map<String, String> params,
            String body
    ) throws Exception {
        // 创建http对象
        packageHeader(headers, httpEntityEnclosingRequestBase);
        packageURI(url, query, httpEntityEnclosingRequestBase);
        if (params != null && StringUtils.isNotEmpty(body)) {
            throw new IllegalArgumentException("表单和请求体单次请求只支持其中一种");
        }
        packageUrlEncodeForm(params, httpEntityEnclosingRequestBase);
        packageJsonBody(body, httpEntityEnclosingRequestBase);
        return getHttpClientResult(httpEntityEnclosingRequestBase);
    }

    // 封装请求头
    private static void packageHeader(Map<String, String> headers, HttpRequestBase httpMethod) {
        // 设置请求头
		/*httpPost.setHeader("Cookie", "");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");*/
        // 封装请求头
        if (headers != null) {
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    // 封装请求参数
    private static void packageUrlEncodeForm(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs, ENCODING));
        }
    }

    private static void packageJsonBody(String body, HttpEntityEnclosingRequestBase httpRequest) {
        if (body != null) {
            httpRequest.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        }
    }

    // 获得响应结果
    private static HttpClientResult getHttpClientResult(HttpRequestBase httpMethod)
            throws Exception {
        // 执行请求
        @Cleanup
        CloseableHttpResponse httpResponse = httpClient.execute(httpMethod);

        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            String content = "";
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
            return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
