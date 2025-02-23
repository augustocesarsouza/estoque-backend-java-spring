package com.estoque.backend.application.services.interfaces;

import com.estoque.backend.application.dto.CategoryDTO;
import com.estoque.backend.application.services.ResultService;

import java.util.UUID;

public interface ICategoryService {
    ResultService<CategoryDTO> GetItemByCategoryId(UUID categoryId);
    ResultService<CategoryDTO> CreateAsync(CategoryDTO categoryDTO);
    ResultService<CategoryDTO> Delete(UUID categoryId);
}
