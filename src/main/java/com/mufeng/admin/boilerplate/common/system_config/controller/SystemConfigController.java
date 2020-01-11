package com.mufeng.admin.boilerplate.common.system_config.controller;

import com.mufeng.admin.boilerplate.common.context.RequestContext;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import com.mufeng.admin.boilerplate.common.system_config.model.dto.ConfigParam;
import com.mufeng.admin.boilerplate.common.system_config.model.entity.Config;
import com.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/common/system-config")
public class SystemConfigController {
    @Resource
    private ConfigService configService;
    @Resource
    private RequestContext context;

    @GetMapping("/configs")
    @PreAuthorize("hasAnyAuthority('rumtime_config')")
    public Result list() {
        List<Config> configs = configService.list();
        return Result.success(context.getRequestId(), configs);
    }

    @PostMapping("/config")
    @PreAuthorize("hasAnyAuthority('rumtime_config')")
    public Result set(@Valid @RequestBody ConfigParam configParam) {
        configService.set(configParam.getKey(), configParam.getValue());
        return Result.success(context.getRequestId());
    }

    @GetMapping("/config/{key}")
    @PreAuthorize("hasAnyAuthority('rumtime_config')")
    public Result detail(@PathVariable String key) {
        return Result.success(context.getRequestId(), configService.get(key));
    }
}
