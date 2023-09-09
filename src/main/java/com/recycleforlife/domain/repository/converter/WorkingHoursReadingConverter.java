package com.recycleforlife.domain.repository.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recycleforlife.domain.model.WorkingHours;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;

import java.lang.reflect.Type;

@AllArgsConstructor
public class WorkingHoursReadingConverter implements Converter<PGobject, WorkingHours> {
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public WorkingHours convert(final PGobject source) {
        return objectMapper.readValue(source.getValue(), new TypeReference<>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }
}