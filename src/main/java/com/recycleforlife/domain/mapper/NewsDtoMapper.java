package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.NewsDto;
import com.recycleforlife.domain.dto.SimpleNewsDto;
import com.recycleforlife.domain.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NewsDtoMapper {

    @Mapping(target = "id", ignore = true)
    News toInner(NewsDto dto);

    NewsDto toDto(News news);

    SimpleNewsDto toSimpleDto(News news);
}
