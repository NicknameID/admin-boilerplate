package com.mufeng.admin.boilerplate.common.util;

        import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 17:19
 * @Version 1.0
 */
public class IdUtils {
    public static Long generalLongId() {
        return (System.currentTimeMillis() << 20) | (ThreadLocalRandom.current().nextLong(0, 1 << 20));
    }
}
