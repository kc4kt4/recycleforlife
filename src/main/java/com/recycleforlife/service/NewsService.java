package com.recycleforlife.service;

import com.recycleforlife.domain.dto.FindNewsResponse;
import com.recycleforlife.domain.dto.NewsDto;
import com.recycleforlife.domain.dto.NewsType;
import com.recycleforlife.domain.dto.SimpleNewsDto;
import com.recycleforlife.domain.mapper.NewsDtoMapper;
import com.recycleforlife.domain.model.News;
import com.recycleforlife.domain.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NewsService {
    private final NewsDtoMapper newsDtoMapper;
    private final NewsRepository newsRepository;

    @NotNull
    public FindNewsResponse findAll(
            final @NotNull LocalDate from,
            final @NotNull LocalDate to,
            final NewsType type,
            final String city
    ) {
        final List<SimpleNewsDto> news = newsRepository.findByPeriod(from, to)
                .stream()
                .filter(e -> type == null || type == e.getType())
                .filter(e -> city == null || city.equals(e.getCity()))
                .map(newsDtoMapper::toSimpleDto)
                .toList();

        return new FindNewsResponse()
                .setNews(news);
    }

    @Nullable
    public NewsDto find(final @NotNull UUID uuid) {
        return newsRepository.findByUuid(uuid)
                .map(newsDtoMapper::toDto)
                .orElse(null);
    }

    @NotNull
    public NewsDto create(final @NotNull NewsDto dto) {
        final News news = newsDtoMapper.toInner(dto);
        final News saved = newsRepository.save(news);

        return newsDtoMapper.toDto(saved);
    }
}
