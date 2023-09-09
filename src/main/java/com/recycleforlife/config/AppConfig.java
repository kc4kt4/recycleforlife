package com.recycleforlife.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    @Bean
    ObjectMapper objectMapper(final @NotNull Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // deserializers
        jackson2ObjectMapperBuilder.deserializers(new LocalTimeDeserializer(timeFormatter));
        jackson2ObjectMapperBuilder.deserializers(new LocalDateDeserializer(dateFormatter));
        jackson2ObjectMapperBuilder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

        // serializers
        jackson2ObjectMapperBuilder.serializers(new LocalTimeSerializer(timeFormatter));
        jackson2ObjectMapperBuilder.serializers(new LocalDateSerializer(dateFormatter));
        jackson2ObjectMapperBuilder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));

        return jackson2ObjectMapperBuilder
                .build()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
