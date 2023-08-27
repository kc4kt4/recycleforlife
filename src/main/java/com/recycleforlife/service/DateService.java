package com.recycleforlife.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class DateService {

    public LocalDateTime timeNow() {
        return LocalDateTime.now(ZoneId.of("UTC")).withNano(0);
    }

    public Instant instantNow() {
        return timeNow().toInstant(ZoneOffset.UTC);
    }

    public LocalDate dateNow() {
        return LocalDate.now(ZoneId.of("UTC"));
    }
}
