package com.recycleforlife.service;

import com.recycleforlife.domain.dto.CategoryDto;
import com.recycleforlife.domain.dto.CreateCategoryRequest;
import com.recycleforlife.domain.dto.FindCategoriesResponse;
import com.recycleforlife.domain.dto.SubCategoryRequest;
import com.recycleforlife.domain.mapper.CategoryDtoMapper;
import com.recycleforlife.domain.model.Category;
import com.recycleforlife.domain.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryDtoMapper categoryDtoMapper;
    private final CategoryRepository categoryRepository;

    @NotNull
    // todo переписать хуету эту
    public FindCategoriesResponse findAll() {
        final List<CategoryDto> categories = categoryRepository.findRoots()
                .stream()
                .map(e -> {
                    final List<UUID> subUuids = findSubUuids(e);
                    final UUID parentUuid = Optional.ofNullable(e.getParentId())
                            .flatMap(categoryRepository::findById)
                            .map(Category::getUuid)
                            .orElse(null);
                    return categoryDtoMapper.toDto(e, subUuids, parentUuid);
                })
                .toList();

        return new FindCategoriesResponse()
                .setCategories(categories);
    }

    @Nullable
    public CategoryDto find(final @NotNull UUID uuid) {
        return categoryRepository.findByUuid(uuid)
                .map(e -> {
                    final List<UUID> subUuids = findSubUuids(e);
                    final UUID parentUuid = Optional.ofNullable(e.getParentId())
                            .flatMap(categoryRepository::findById)
                            .map(Category::getUuid)
                            .orElse(null);
                    return categoryDtoMapper.toDto(e, subUuids, parentUuid);
                })
                .orElse(null);
    }

    @NotNull
    public CategoryDto create(final @NotNull CreateCategoryRequest request) {
        final Category category = new Category()
                .setUuid(request.getUuid())
                .setName(request.getName())
                .setDescription(request.getDescription());
        final Category saved = categoryRepository.save(category);
        log.info("category saved - {}", saved);

        return categoryDtoMapper.toDto(saved, List.of(), null);
    }

    public void addSubCategory(final @NotNull UUID uuid, final @NotNull SubCategoryRequest request) {
        categoryRepository.findByUuid(uuid)
                .ifPresent(e -> {
                    request.getSubCategoryUuids()
                            .stream()
                            .map(categoryRepository::findByUuid)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(s -> s.setParentId(e.getId()))
                            .forEach(categoryRepository::save);
                });
    }

    public void removeSubCategory(final @NotNull UUID uuid, final @NotNull SubCategoryRequest request) {
        categoryRepository.findByUuid(uuid)
                .ifPresent(e -> {
                    categoryRepository.findByParentId(e.getId())
                            .stream()
                            .filter(s -> request.getSubCategoryUuids().contains(s.getUuid()))
                            .map(s -> s.setParentId(null))
                            .forEach(categoryRepository::save);
                });
    }

    @NotNull
    private List<UUID> findSubUuids(final Category category) {
        return categoryRepository.findByParentId(category.getId())
                .stream()
                .map(Category::getUuid)
                .toList();
    }
}
