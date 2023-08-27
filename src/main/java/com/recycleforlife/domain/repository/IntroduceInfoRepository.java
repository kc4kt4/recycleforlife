package com.recycleforlife.domain.repository;

import com.recycleforlife.domain.model.IntroduceInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IntroduceInfoRepository extends CrudRepository<IntroduceInfo, Long> {

    @NotNull
    @Override
    List<IntroduceInfo> findAll();
}
