package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.FractionDto;
import com.recycleforlife.domain.model.Fraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FractionDtoMapper {

    @Mapping(target = "categoryUuid", source = "categoryUuid")
    @Mapping(target = "receivingPointUuids", source = "receivingPointUuids")
    FractionDto toDto(Fraction fraction, UUID categoryUuid, List<UUID> receivingPointUuids);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoryId", source = "categoryId")
    Fraction toInner(FractionDto dto, long categoryId);
}
