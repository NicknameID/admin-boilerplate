package tech.mufeng.admin.boilerplate.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * HuangTianyu
 * 2020-01-10 16:58
 */
public class TimeConst {
    public static final String standardDateTime = "yyyy-MM-dd HH:mm:ss";

    public static final String standardDate = "yyyy-MM-dd";

    public static final DateTimeFormatter standardDateTimeFormatter = DateTimeFormatter.ofPattern(standardDateTime);

    public static final DateTimeFormatter standardDateFormatter = DateTimeFormatter.ofPattern(standardDate);

}
