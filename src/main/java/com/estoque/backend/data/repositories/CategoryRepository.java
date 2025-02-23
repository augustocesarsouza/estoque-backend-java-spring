package com.estoque.backend.data.repositories;

import com.estoque.backend.application.dto.CategoryDTO;
import com.estoque.backend.data.context.CategoryRepositoryJPA;
import com.estoque.backend.domain.entities.Category;
import com.estoque.backend.domain.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryRepository implements ICategoryRepository {
    private final CategoryRepositoryJPA categoryRepositoryJPA;

    @Autowired
    public CategoryRepository(CategoryRepositoryJPA categoryRepositoryJPA) {
        this.categoryRepositoryJPA = categoryRepositoryJPA;
    }

    @Override
    public CategoryDTO GetItemByCategoryId(UUID categoryId) {
        return categoryRepositoryJPA.GetItemByCategoryId(categoryId);
    }

    @Override
    public Category create(Category category) {
        if(category == null)
            return null;

        return categoryRepositoryJPA.save(category);
    }

    @Override
    public Category update(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public Category delete(UUID categoryId) {
        if(categoryId == null)
            return null;

        var entity = categoryRepositoryJPA.findById(categoryId).orElse(null);

        if(entity == null)
            return null;

        categoryRepositoryJPA.delete(entity);

        return entity;
    }
}
