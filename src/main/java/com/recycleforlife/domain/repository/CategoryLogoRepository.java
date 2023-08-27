package com.recycleforlife.domain.repository;

import com.recycleforlife.domain.model.Category;
import com.recycleforlife.domain.model.CategoryLogo;
import org.springframework.data.repository.CrudRepository;

public interface CategoryLogoRepository extends CrudRepository<CategoryLogo, Long> {
}
