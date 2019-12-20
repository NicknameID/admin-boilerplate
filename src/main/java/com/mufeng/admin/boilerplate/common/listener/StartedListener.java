package com.mufeng.admin.boilerplate.common.listener;

import com.mufeng.admin.boilerplate.common.acl.service.impl.PermissionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author HuangTianyu
 * @Date 2019-12-10 18:24
 * @Version 1.0
 */
@Configuration
@Slf4j
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {
    @Resource
    private PermissionServiceImpl permissionServiceImpl;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("开始同步权限到数据库....start");
        permissionServiceImpl.syncToDB();
        log.info("权限同步任务完成....success!");
    }
}
