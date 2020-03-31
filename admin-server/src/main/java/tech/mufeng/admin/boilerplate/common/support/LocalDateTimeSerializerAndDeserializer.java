package tech.mufeng.admin.boilerplate.common.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;
import tech.mufeng.admin.boilerplate.common.constant.TimeConst;

import java.io.IOException;
import java.time.LocalDateTime;

@JsonComponent
public class LocalDateTimeSerializerAndDeserializer {
    public static class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) { return; }
            String localDateTimeFormat = value.format(TimeConst.standardDateTimeFormatter);
            gen.writeString(localDateTimeFormat);
        }
    }

    public static class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String value = p.readValueAs(String.class);
            if (StringUtils.isEmpty(value)) { return null; }
            return LocalDateTime.parse(value, TimeConst.standardDateTimeFormatter);
        }
    }
}
