package com.recycleforlife.service;

import com.recycleforlife.domain.dto.IntroduceInfoDto;
import com.recycleforlife.domain.dto.IntroduceInfoResponse;
import com.recycleforlife.domain.mapper.IntroduceInfoDtoMapper;
import com.recycleforlife.domain.repository.IntroduceInfoRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IntroduceInfoService {
    private final IntroduceInfoDtoMapper introduceInfoDtoMapper;
    private final IntroduceInfoRepository introduceInfoRepository;

    @NotNull
    public IntroduceInfoResponse findAll() {
        final List<IntroduceInfoDto> introduceInfoDtos = introduceInfoRepository.findAll()
                .stream()
                .map(introduceInfoDtoMapper::toDto)
                .toList();

        return new IntroduceInfoResponse()
                .setIntroduceInfoDtos(introduceInfoDtos);
    }
}
