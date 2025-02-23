package com.estoque.backend.data.context;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.domain.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepositoryJPA extends JpaRepository<Item, UUID> {// CRIAR ITEMS
    @Query("SELECT new com.estoque.backend.application.dto." +
            "ItemDTO(x.Id, x.Name, null, null, null, null, null, null, x.ImgProductAll) " +
            "FROM Item AS x " +
            "WHERE x.Id = :itemId")
    ItemDTO GetItemByItemId(UUID itemId);

    @Query("SELECT new com.estoque.backend.application.dto." +
            "ItemDTO(x.Id, x.Name, x.PriceProduct, x.DiscountPercentage, x.Size, x.Brand, null, null, x.ImgProductAll) " +
            "FROM Item AS x " +
            "WHERE x.category.NameCategory = :nameCategory")
    List<ItemDTO> GetItemByNameCategory(String nameCategory); // TESTAR ESSE GET
}

//ItemDTO(UUID id, String name, Double priceProduct, Integer discountPercentage, String size, String brand, Category category, String categoryId, List<String> imgProductAll)