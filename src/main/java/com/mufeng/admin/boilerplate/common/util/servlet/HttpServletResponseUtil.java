package com.mufeng.admin.boilerplate.common.util.servlet;

import com.mufeng.admin.boilerplate.common.constant.HeaderConst;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author HuangTianyu
 * @Date 2019-12-09 17:53
 * @Version 1.0
 */
public class HttpServletResponseUtil {
    public static void reponse(HttpServletResponse response, String charset, String contentType, Integer statusCode, String resBody) {
//        response.reset();
        response.setCharacterEncoding(charset);
        response.setContentType(contentType);
        response.setStatus(statusCode);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(resBody);
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }

    public static void response(HttpServletResponse response, String resBody) {
        reponse(response, "UTF-8", HeaderConst.JSON_CONTENT_TYPE_UTF_8, 200, resBody);
    }

    public static void response(HttpServletResponse response, Integer statusCode, String resBody) {
        reponse(response, "UTF-8", HeaderConst.JSON_CONTENT_TYPE_UTF_8, statusCode, resBody);
    }
}
