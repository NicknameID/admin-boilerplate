package com.mufeng.admin.boilerplate.common.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.mufeng.admin.boilerplate.common.exception.HttpClientException;
import okhttp3.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author HuangTianyu
 * @Date 2019-11-24 18:13
 * @Version 1.0
 */
@Component
public class HttpUtil {
    private static final MediaType JSON_TYPE = MediaType.parse("utils/json; charset=utf-8");

    @Resource
    private OkHttpClient okHttpClient;

    public String doRequest(HttpMethod method, String url, Map<String, String> headers, RequestBody body) {
        if (okHttpClient == null) throw new RuntimeException("OkHttpClient NullPointerException");
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .method(method.name(), body)
                .build();
        try {
            final Response response = okHttpClient.newCall(request).execute();
            ResponseBody resBody = response.body();
            if (resBody == null) return null;
            return resBody.string();
        }catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientException(e.getMessage());
        }
    }

    public String get(String url, Map<String, String> headers, Map<String, Object> query) {
        String reqUrl = url;
        String queryString = QueryStringUtil.stringify(query);
        if (!queryString.isEmpty()) {
            reqUrl = String.join("?", url, queryString);
        }
        return doRequest(HttpMethod.GET, reqUrl, headers, null);
    }

    public String postJsonBody(String url, Map<String, Object> body) {
        RequestBody reqBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(body, SerializerFeature.WriteMapNullValue));
        return doRequest(HttpMethod.POST, url, Maps.newHashMap(), reqBody);
    }

    public String postHeader(String url, Map<String, String> headers) {
        return doRequest(HttpMethod.POST, url, headers, null);
    }
}
