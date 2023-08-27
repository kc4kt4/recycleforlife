package com.recycleforlife.controller;

import com.recycleforlife.domain.dto.FindNewsResponse;
import com.recycleforlife.domain.dto.NewsDto;
import com.recycleforlife.service.NewsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/v1/news")
@AllArgsConstructor
public class NewsController {
    private final NewsService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FindNewsResponse findAll(
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().minusMonths(1)}") final LocalDate from,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}") final LocalDate to
    ) {
        return service.findAll(from, to);
    }

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsDto find(final @PathVariable UUID uuid) {
        return service.find(uuid);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsDto create(final @RequestBody @Valid NewsDto request) {
        return service.create(request);
    }
}
