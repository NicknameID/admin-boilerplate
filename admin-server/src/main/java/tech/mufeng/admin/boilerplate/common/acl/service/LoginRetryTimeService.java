package tech.mufeng.admin.boilerplate.common.acl.service;

public interface LoginRetryTimeService {
    /**
     * 是否允许登陆
     * @return true 能够登陆；false 不能登陆
     */
    boolean enableLogin(long uid);

    /**
     * 标记一次登陆失败
     */
    void increment(long uid);

    /**
     * 清除登陆失败次数标记
     */
    void clear(long uid);
}
