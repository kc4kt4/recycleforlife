package com.recycleforlife.controller;

import com.recycleforlife.domain.dto.IntroduceInfoResponse;
import com.recycleforlife.service.IntroduceInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/introduce-info")
@AllArgsConstructor
public class IntroduceInfoController {
    private final IntroduceInfoService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public IntroduceInfoResponse findAll() {
        return service.findAll();
    }
}
