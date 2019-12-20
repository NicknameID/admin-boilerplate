package com.mufeng.admin.boilerplate.common.util.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 15:33
 * @Version 1.0
 */
public class HeaderUtil {
    public static Map<String, String> mapHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    public static Map<String, String> mapHeaders(HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();
        Iterator<String> headerNamesIterator = headerNames.iterator();
        Map<String, String> headers = new HashMap<>();
        while (headerNamesIterator.hasNext()) {
            String name = headerNamesIterator.next();
            String value = response.getHeader(name);
            headers.put(name, value);
        }
        return headers;
    }
}
