package com.estoque.backend.data.context;

import com.estoque.backend.application.dto.CategoryDTO;
import com.estoque.backend.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepositoryJPA extends JpaRepository<Category, UUID> {
    @Query("SELECT new com.estoque.backend.application.dto." +
            "CategoryDTO(x.Id, x.NameCategory, null) " +
            "FROM Category AS x " +
            "WHERE x.Id = :categoryId")
    CategoryDTO GetItemByCategoryId(UUID categoryId);
}
//CategoryDTO(UUID id, String nameCategory, List<ItemDTO> itemsDTOs)