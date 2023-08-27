package com.recycleforlife.service;

import com.recycleforlife.domain.ApiError;
import com.recycleforlife.domain.ErrorCode;
import com.recycleforlife.domain.dto.CreateReceivingPointRequest;
import com.recycleforlife.domain.dto.GetReceivingPointResponse;
import com.recycleforlife.domain.dto.ReceivingPointDto;
import com.recycleforlife.domain.exception.ApiException;
import com.recycleforlife.domain.mapper.FractionDtoMapper;
import com.recycleforlife.domain.mapper.ReceivingPointMapper;
import com.recycleforlife.domain.model.Fraction;
import com.recycleforlife.domain.model.FractionReceivingPoint;
import com.recycleforlife.domain.model.ReceivingPoint;
import com.recycleforlife.domain.repository.FractionReceivingPointRepository;
import com.recycleforlife.domain.repository.FractionRepository;
import com.recycleforlife.domain.repository.ReceivingPointRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ReceivingPointService {
    private final ReceivingPointMapper receivingPointMapper;
    private final ReceivingPointRepository receivingPointRepository;
    private final FractionRepository fractionRepository;
    private final FractionReceivingPointRepository fractionReceivingPointRepository;

    @NotNull
    public GetReceivingPointResponse findAll() {
        final List<ReceivingPointDto> receivingPoints = receivingPointRepository.findAll()
                .stream()
                .map(e -> {
                    final List<UUID> fractionUuids = fractionRepository.findAllFractionsByReceivingPointId(e.getId());
                    return receivingPointMapper.toDto(e, fractionUuids);
                })
                .toList();

        return new GetReceivingPointResponse()
                .setReceivingPoints(receivingPoints);
    }

    @NotNull
    public ReceivingPointDto create(final @NotNull CreateReceivingPointRequest request) {
        final ReceivingPoint receivingPoint = receivingPointRepository.findByUuid(request.getUuid())
                .orElseGet(() -> {
                    final ReceivingPoint model = receivingPointMapper.toModel(request);
                    final ReceivingPoint saved = receivingPointRepository.save(model);
                    log.info("receivingPoint saved: {}", saved);
                    return saved;
                });
        final List<UUID> fractionUuids = fractionRepository.findAllFractionsByReceivingPointId(receivingPoint.getId());

        return receivingPointMapper.toDto(receivingPoint, fractionUuids);
    }

    @Transactional
    @NotNull
    public ReceivingPointDto bindFraction(final @NotNull UUID pointUuid, final @NotNull UUID fractionUuid) {
        final ReceivingPoint receivingPoint = receivingPointRepository.findByUuid(pointUuid)
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.RECEIVING_POINT_NOT_FOUND)
                                    .setMessage("receiving point not found by uuid: " + pointUuid)
                    );
                });

        final Fraction fraction = fractionRepository.findByUuid(fractionUuid)
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.FRACTION_NOT_FOUND)
                                    .setMessage("fraction not found by uuid: " + fractionUuid)
                    );
                });

        final FractionReceivingPoint fractionReceivingPoint = new FractionReceivingPoint()
                .setReceivingPointId(receivingPoint.getId())
                .setFractionId(fraction.getId());
        final FractionReceivingPoint saved = fractionReceivingPointRepository.save(fractionReceivingPoint);
        log.info("fractionReceivingPoint saved: {}", saved);

        final List<UUID> fractionUuids = fractionRepository.findAllFractionsByReceivingPointId(receivingPoint.getId());

        return receivingPointMapper.toDto(receivingPoint, fractionUuids);
    }
}
