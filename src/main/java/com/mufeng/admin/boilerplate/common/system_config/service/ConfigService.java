package com.mufeng.admin.boilerplate.common.system_config.service;

import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.system_config.mapper.ConfigMapper;
import com.mufeng.admin.boilerplate.common.system_config.model.entity.Config;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 14:54
 * @Version 1.0
 */
@Service
public class ConfigService extends ServiceImpl<ConfigMapper, Config> {

    @Cacheable(value = "ConfigService.get",key = "#key")
    public String get(String key) {
        Config config = getById(key);
        if (Objects.isNull(config)) {
            throw new NullPointerException(String.format("无法找到该配置: %s", key));
        }
        return config.getConfigValue();
    }

    @CacheEvict(value = "ConfigService.get", key = "#key")
    public void set(String key, String value) {
        Config config = new Config();
        config.setConfigName(key);
        config.setConfigValue(value);
        config.setUpdatedTime(LocalDateTime.now());
        saveOrUpdate(config);
    }

    public <T> T get(String key, Class<T> clazz) {
        String value = get(key);
        if (Objects.isNull(value)) {
            throw new NullPointerException(String.format("该配置值为Null: %s", key));
        }
        return TypeUtils.castToJavaBean(value, clazz);
    }
}
