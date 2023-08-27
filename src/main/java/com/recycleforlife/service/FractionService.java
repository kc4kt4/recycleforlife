package com.recycleforlife.service;

import com.recycleforlife.domain.ApiError;
import com.recycleforlife.domain.ErrorCode;
import com.recycleforlife.domain.dto.CategoryFractionsResponse;
import com.recycleforlife.domain.dto.FractionDto;
import com.recycleforlife.domain.exception.ApiException;
import com.recycleforlife.domain.mapper.FractionDtoMapper;
import com.recycleforlife.domain.model.Category;
import com.recycleforlife.domain.model.Fraction;
import com.recycleforlife.domain.repository.CategoryRepository;
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
public class FractionService {
    private final FractionDtoMapper fractionDtoMapper;
    private final CategoryRepository categoryRepository;
    private final FractionRepository fractionRepository;
    private final ReceivingPointRepository receivingPointRepository;

    @NotNull
    public CategoryFractionsResponse findByCategoryUuid(final @NotNull UUID categoryUuid) {
        final List<FractionDto> fractions = fractionRepository.findForCategory(categoryUuid)
                .stream()
                .map(e -> {
                    final List<UUID> receivingPointUuids = receivingPointRepository.findPointsUuidForFractionId(e.getId());
                    return fractionDtoMapper.toDto(e, categoryUuid, receivingPointUuids);
                })
                .toList();

        return new CategoryFractionsResponse()
                .setFractions(fractions);
    }

    @NotNull
    public FractionDto findByUuid(final @NotNull UUID uuid) {
        return fractionRepository.findByUuid(uuid)
                .map(e -> {
                    final List<UUID> receivingPointUuids = receivingPointRepository.findPointsUuidForFractionId(e.getId());
                    final Category category = categoryRepository.findById(e.getCategoryId())
                            .orElseThrow(() -> {
                                throw new ApiException(
                                        new ApiError()
                                                .setCode(ErrorCode.CATEGORY_NOT_FOUND)
                                                .setMessage("category not found by id: " + e.getCategoryId())
                                );
                            });

                    return fractionDtoMapper.toDto(e, category.getUuid(), receivingPointUuids);
                })
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.FRACTION_NOT_FOUND)
                                    .setMessage("fraction not found by uuid: " + uuid)
                    );
                });
    }

    @NotNull
    @Transactional
    public FractionDto create(final @NotNull FractionDto dto) {
        final Category category = categoryRepository.findByUuid(dto.getCategoryUuid())
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.CATEGORY_NOT_FOUND)
                                    .setMessage("category with uuid not found")
                    );
                });
        final Fraction fraction = fractionDtoMapper.toInner(dto, category.getId());
        final Fraction saved = fractionRepository.save(fraction);
        log.info("fraction saved - {}", saved);

        final List<UUID> receivingPointUuids = receivingPointRepository.findPointsUuidForFractionId(saved.getId());

        return fractionDtoMapper.toDto(saved, category.getUuid(), receivingPointUuids);
    }
}
