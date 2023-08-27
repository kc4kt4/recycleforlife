package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.CreateReceivingPointRequest;
import com.recycleforlife.domain.dto.ReceivingPointDto;
import com.recycleforlife.domain.model.ReceivingPoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ReceivingPointMapper {

    @Mapping(target = "fractionUuids", source = "fractionUuids")
    ReceivingPointDto toDto(ReceivingPoint receivingPoint, List<UUID> fractionUuids);

    @Mapping(target = "id", ignore = true)
    ReceivingPoint toModel(CreateReceivingPointRequest request);
}
