package com.mufeng.admin.boilerplate.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 针对Okhttp的日志拦截器
 * @Author HuangTianyu
 * @Date 2019-09-01 01:05
 * @Version 1.0
 */
public class OkHttpLogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        MediaType contentType = null;
        String responseBodyString = null;
        if (response.body() != null) {
            contentType = response.body().contentType();
            responseBodyString = response.body().string();
        }

        // 请求响应时间
        double time = (t2 - t1) / 1e6d;

        // 打印日志
        logJson(request, response, responseBodyString, time);

        if (response.body() != null) {
            // 打印body后原ResponseBody会被清空，需要重新设置body
            ResponseBody body = ResponseBody.create(contentType, responseBodyString);
            return response.newBuilder().body(body).build();
        } else {
            return response;
        }
    }

    public void logJson(Request request, Response response, String responseBodyString, double time) {
        if (request == null || response == null) {
            return;
        }
        JSONObject json = new JSONObject(true);
//        JSONObject requestJson = new JSONObject(true);
//        JSONObject responseJson = new JSONObject(true);

        json.put("timeCost", time);
        json.put("timeUnit", "ms");
        // 请求构造
        json.put("method", request.method());
        json.put("url", request.url().toString());
        json.put("requestHeaders", buildHeader(request));
        json.put("requestBody", buildRequestBody(request));
        // 响应构造
        json.put("httpCode", response.code());
        json.put("responseHeaders", buildHeader(response));
        json.put("responseBody", JSONObject.parse(responseBodyString));
        // 打印日志到stdout
        System.out.println(json.toJSONString());
    }

    public JSONObject buildHeader(Request request) {
        if (request.headers() != null) {
            Map<String, List<String>> requsetHeadersMap = request.headers().toMultimap();
            return buildBaseHeader(requsetHeadersMap);
        }
        return null;
    }

    public JSONObject buildHeader(Response response) {
        if (response.headers() != null) {
            Map<String, List<String>> responseHeadersMap = response.headers().toMultimap();
            return buildBaseHeader(responseHeadersMap);
        }
        return null;
    }

    public JSONObject buildBaseHeader(Map<String, List<String>> headersMap) {
        JSONObject headerJson = new JSONObject(true);
        Set<String> requsetKeys = headersMap.keySet();
        for (String key : requsetKeys) {
            List<String> value = headersMap.get(key);
            headerJson.put(key, value.get(0));
        }
        return headerJson;
    }

    public JSONObject buildRequestBody(Request request) {
        try {
            Request copy = request.newBuilder().build();
            if (copy != null) {
                RequestBody body = copy.body();
                if (body != null) {
                    Buffer buffer = new Buffer();
                    body.writeTo(buffer);
                    return JSON.parseObject(buffer.readUtf8());
                } else {
                    return null;
                }

            } else {
                return null;
            }
        } catch (final IOException e) {
            return null;
        }
    }
}
