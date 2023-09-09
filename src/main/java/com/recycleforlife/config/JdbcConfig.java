package com.recycleforlife.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recycleforlife.domain.repository.converter.WorkingHoursReadingConverter;
import com.recycleforlife.domain.repository.converter.WorkingHoursWritingConverter;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@AllArgsConstructor
public class JdbcConfig extends AbstractJdbcConfiguration {
    private final ObjectMapper objectMapper;

    @Override
    @NotNull
    protected List<?> userConverters() {
        return Arrays.asList(
                new WorkingHoursReadingConverter(objectMapper),
                new WorkingHoursWritingConverter(objectMapper)
        );
    }
}
