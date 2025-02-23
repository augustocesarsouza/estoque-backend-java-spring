package com.estoque.backend.domain.repositories;

import com.estoque.backend.application.dto.CategoryDTO;
import com.estoque.backend.domain.entities.Category;

import java.util.UUID;

public interface ICategoryRepository {
    CategoryDTO GetItemByCategoryId(UUID categoryId);
    Category create(Category category);
    Category update(CategoryDTO categoryDTO);
    Category delete(UUID categoryId);
}
