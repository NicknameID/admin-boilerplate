package tech.mufeng.admin.boilerplate.common.util;

import java.util.UUID;

public class SimpleCommonUtil {
    public static String getUppercaseUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
