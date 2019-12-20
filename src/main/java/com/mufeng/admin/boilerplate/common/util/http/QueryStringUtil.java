package com.mufeng.admin.boilerplate.common.util.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author HuangTianyu
 * @Date 2019-11-24 19:21
 * @Version 1.0
 */
public class QueryStringUtil {
    public static String stringify(Map<String, Object> obj) {
        return stringify(obj, "&", "=");
    }

    public static String stringify(Map<String, Object> obj, String sep, String eq) {
        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        for (String key : obj.keySet()) {
            if (key != null && obj.get(key) != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    builder.append(sep); // &
                }
                builder.append(key)
                        .append(eq) // =
                        .append(obj.get(key));
            }
        }
        return builder.toString();
    }

    public static Map<String, String> parse(String queryString, String sep, String eq) {
        Map<String, String> query = new HashMap<>();
        if (queryString == null) return query;
        String[] queryKVParts = queryString.split(sep);
        for (String queryKVPart : queryKVParts) {
            String[] KV = queryKVPart.split(eq);
            query.put(KV[0], KV[1]);
        }
        return query;
    }

    public static Map<String, String> parse(String queryString) {
        return parse(queryString, "&", "=");
    }
}