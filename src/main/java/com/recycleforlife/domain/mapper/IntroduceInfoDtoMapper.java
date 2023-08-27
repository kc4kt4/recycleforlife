package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.IntroduceInfoDto;
import com.recycleforlife.domain.model.IntroduceInfo;
import org.mapstruct.Mapper;

@Mapper
public interface IntroduceInfoDtoMapper {

    IntroduceInfoDto toDto(IntroduceInfo introduceInfo);
}
