package tech.mufeng.admin.boilerplate.common.system_config.controller;

import tech.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import tech.mufeng.admin.boilerplate.common.model.dto.Result;
import tech.mufeng.admin.boilerplate.common.system_config.model.dto.ConfigParam;
import tech.mufeng.admin.boilerplate.common.system_config.model.entity.Config;
import tech.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import org.springframework.web.bind.annotation.*;
import tech.mufeng.admin.boilerplate.config.PermissionModuleEnum;

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

    @GetMapping("/configs")
    @RequirePermission(PermissionModuleEnum.RUNTIME_CONFIGURATION)
    public Result list() {
        List<Config> configs = configService.list();
        return Result.success(configs);
    }

    @PostMapping("/config")
    @RequirePermission(PermissionModuleEnum.RUNTIME_CONFIGURATION)
    public Result set(@Valid @RequestBody ConfigParam configParam) {
        configService.set(configParam.getKey(), configParam.getValue());
        return Result.success();
    }

    @GetMapping("/config/{key}")
    @RequirePermission(PermissionModuleEnum.RUNTIME_CONFIGURATION)
    public Result detail(@PathVariable String key) {
        return Result.success(configService.get(key));
    }
}
