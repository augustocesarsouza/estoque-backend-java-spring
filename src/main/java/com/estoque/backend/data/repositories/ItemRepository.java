package com.estoque.backend.data.repositories;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.data.context.CategoryRepositoryJPA;
import com.estoque.backend.data.context.ItemRepositoryJPA;
import com.estoque.backend.domain.entities.Category;
import com.estoque.backend.domain.entities.Item;
import com.estoque.backend.domain.repositories.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ItemRepository implements IItemRepository {
    private final ItemRepositoryJPA itemRepositoryJPA;
    private final CategoryRepositoryJPA categoryRepositoryJPA;

    @Autowired
    public ItemRepository(ItemRepositoryJPA itemRepositoryJPA, CategoryRepositoryJPA categoryRepositoryJPA) {
        this.itemRepositoryJPA = itemRepositoryJPA;
        this.categoryRepositoryJPA = categoryRepositoryJPA;
    }

    @Override
    public ItemDTO GetItemByItemId(UUID itemId) {
        return itemRepositoryJPA.GetItemByItemId(itemId);
    }

    @Override
    public List<ItemDTO> GetItemByNameCategory(String nameCategory) {
        return itemRepositoryJPA.GetItemByNameCategory(nameCategory);
    }

    @Override
    public ItemDTO GetItemByIdWithCategory(UUID itemId) {
        return itemRepositoryJPA.GetItemByIdWithCategory(itemId);
    }

    @Override
    public Item create(ItemDTO itemDTO) {
        if(itemDTO == null)
            return null;

        UUID entityId = UUID.randomUUID();
        var categoryId = itemDTO.getCategoryId();

        Category category = categoryRepositoryJPA.findById(UUID.fromString(categoryId))
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        var entityCreate = new Item(entityId, itemDTO.getName(), itemDTO.getPriceProduct(),
        itemDTO.getDiscountPercentage(), itemDTO.getSize(), itemDTO.getBrand(), itemDTO.getDescription(), itemDTO.getImgProductAll());

        entityCreate.setCategory(category);

        Item createItem = itemRepositoryJPA.save(entityCreate);
        createItem.setCategory(null);

        return createItem;
    }

    @Override
    public Item update(ItemDTO itemDTO) {
        return null;
    }

    @Override
    public Item delete(UUID itemId) {
        if(itemId == null)
            return null;

        var entity = itemRepositoryJPA.findById(itemId).orElse(null);

        if(entity == null)
            return null;

        itemRepositoryJPA.delete(entity);

        return entity;
    }
}
