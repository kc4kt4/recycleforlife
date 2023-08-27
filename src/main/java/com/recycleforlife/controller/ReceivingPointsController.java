package com.recycleforlife.controller;

import com.recycleforlife.domain.dto.CreateReceivingPointRequest;
import com.recycleforlife.domain.dto.GetReceivingPointResponse;
import com.recycleforlife.domain.dto.ReceivingPointDto;
import com.recycleforlife.service.ReceivingPointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/receiving-points")
@RequiredArgsConstructor
public class ReceivingPointsController {
    private final ReceivingPointService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GetReceivingPointResponse findAll() {
        return service.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReceivingPointDto create(final @RequestBody @Valid CreateReceivingPointRequest request) {
        return service.create(request);
    }

    @PostMapping(path = "/{pointUuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReceivingPointDto bindFraction(final @PathVariable UUID pointUuid, final @RequestParam UUID fractionUuid) {
        return service.bindFraction(pointUuid, fractionUuid);
    }
}
