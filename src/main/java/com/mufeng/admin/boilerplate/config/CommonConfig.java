package com.mufeng.admin.boilerplate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mufeng.admin.boilerplate.common.constant.TimeConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * HuangTianyu
 * 2020-01-09 15:57
 */
@Configuration
public class CommonConfig {
    @Bean
    public ObjectMapper configObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TimeConst.standardDateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(TimeConst.standardDateTimeFormatter));
        mapper.registerModule(javaTimeModule);
        return mapper;
    }
}
