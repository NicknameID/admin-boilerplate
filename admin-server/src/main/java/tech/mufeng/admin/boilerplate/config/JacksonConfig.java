package tech.mufeng.admin.boilerplate.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.mufeng.admin.boilerplate.common.constant.TimeConst;

import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(TimeConst.standardDateTimeFormatter));
            builder.deserializerByType(LocalDateTime.class, new LocalDateDeserializer(TimeConst.standardDateTimeFormatter));
        };
    }
}
