package com.recycleforlife.controller;

import com.recycleforlife.domain.dto.CategoryFractionsResponse;
import com.recycleforlife.domain.dto.FractionDto;
import com.recycleforlife.service.FractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/fractions")
@RequiredArgsConstructor
public class FractionController {
    private final FractionService service;

    @GetMapping(path = "/categories/{categoryUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryFractionsResponse findByCategoryUuid(final @PathVariable UUID categoryUuid) {
        return service.findByCategoryUuid(categoryUuid);
    }

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FractionDto findByUuid(final @PathVariable UUID uuid) {
        return service.findByUuid(uuid);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FractionDto create(final @RequestBody FractionDto request) {
        return service.create(request);
    }
}
