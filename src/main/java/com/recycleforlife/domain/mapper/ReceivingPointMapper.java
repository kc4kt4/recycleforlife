package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.CreateReceivingPointRequest;
import com.recycleforlife.domain.dto.ReceivingPointDto;
import com.recycleforlife.domain.dto.WorkingHour;
import com.recycleforlife.domain.model.ReceivingPoint;
import com.recycleforlife.domain.model.WorkingHours;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ReceivingPointMapper {

    @Mapping(target = "fractionUuids", source = "fractionUuids")
    @Mapping(target = "workingHours", source = "receivingPoint", qualifiedByName = "workingHours")
    ReceivingPointDto toDto(ReceivingPoint receivingPoint, List<UUID> fractionUuids);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workingHours", source = "request", qualifiedByName = "workingHoursRequest")
    ReceivingPoint toModel(CreateReceivingPointRequest request);

    @Named("workingHours")
    default List<WorkingHour> workingHours(ReceivingPoint receivingPoint) {
        return receivingPoint.getWorkingHours().getWorkingHours();
    }

    @Named("workingHoursRequest")
    default WorkingHours workingHoursRequest(CreateReceivingPointRequest request) {
        return new WorkingHours()
                .setWorkingHours(request.getWorkingHours());
    }
}
