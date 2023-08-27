package com.recycleforlife.controller;

import com.recycleforlife.domain.dto.CategoryDto;
import com.recycleforlife.domain.dto.CreateCategoryRequest;
import com.recycleforlife.domain.dto.FindCategoriesResponse;
import com.recycleforlife.domain.dto.SubCategoryRequest;
import com.recycleforlife.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FindCategoriesResponse findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto find(final @PathVariable UUID uuid) {
        return service.find(uuid);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto create(final @RequestBody @Valid CreateCategoryRequest request) {
        return service.create(request);
    }

    @PostMapping(
            path = "/{uuid}/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void addSubCategory(final @PathVariable UUID uuid, final @RequestBody @Valid SubCategoryRequest request) {
        service.addSubCategory(uuid, request);
    }

    @PostMapping(
            path = "/{uuid}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void removeSubCategory(final @PathVariable UUID uuid, final @RequestBody @Valid SubCategoryRequest request) {
        service.removeSubCategory(uuid, request);
    }
}
