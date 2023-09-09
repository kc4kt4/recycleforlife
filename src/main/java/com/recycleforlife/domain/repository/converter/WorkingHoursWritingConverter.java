package com.recycleforlife.domain.repository.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recycleforlife.domain.model.WorkingHours;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class WorkingHoursWritingConverter implements Converter<WorkingHours, PGobject> {
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public PGobject convert(final @NotNull WorkingHours source) {
        final PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(objectMapper.writeValueAsString(source));
        return jsonObject;
    }
}
