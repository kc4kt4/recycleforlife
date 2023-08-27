package com.recycleforlife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recycleforlife.domain.dto.CategoryDto;
import com.recycleforlife.domain.dto.CreateCategoryRequest;
import com.recycleforlife.domain.dto.FindCategoriesResponse;
import com.recycleforlife.domain.dto.SubCategoryRequest;
import com.recycleforlife.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = CategoryController.class)
@ActiveProfiles("test")
class CategoryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CategoryService service;

    @Test
    void findAll() throws Exception {
        final FindCategoriesResponse response = new FindCategoriesResponse()
                .setCategories(List.of(
                        new CategoryDto()
                                .setUuid(UUID.randomUUID())
                                .setName("n")
                ));
        Mockito.when(service.findAll()).thenReturn(response);

        //test
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/categories")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void find() throws Exception {
        final UUID uuid = UUID.randomUUID();
        final CategoryDto dto = new CategoryDto()
                .setDescription("d")
                .setUuid(UUID.randomUUID())
                .setName("n");
        Mockito.when(service.find(uuid)).thenReturn(dto);

        //test
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/categories/{uuid}", uuid)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void create() throws Exception {
        final CreateCategoryRequest request = new CreateCategoryRequest()
                .setDescription("d")
                .setName("n")
                .setUuid(UUID.randomUUID());
        final CategoryDto dto = new CategoryDto()
                .setDescription("d")
                .setUuid(UUID.randomUUID())
                .setName("n");
        Mockito.when(service.create(request)).thenReturn(dto);

        //test
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/categories")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void addSubCategory() throws Exception {
        final UUID uuid = UUID.randomUUID();
        final List<UUID> uuids = List.of(UUID.randomUUID());
        final SubCategoryRequest request = new SubCategoryRequest()
                .setSubCategoryUuids(uuids);

        //test
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/categories/{uuid}/add", uuid)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service).addSubCategory(uuid, request);
    }

    @Test
    void removeSubCategory() throws Exception {
        final UUID uuid = UUID.randomUUID();
        final List<UUID> uuids = List.of(UUID.randomUUID());
        final SubCategoryRequest request = new SubCategoryRequest()
                .setSubCategoryUuids(uuids);

        //test
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/categories/{uuid}/remove", uuid)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service).removeSubCategory(uuid, request);
    }
}
