package com.estoque.backend.domain.repositories;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.domain.entities.Item;

import java.util.List;
import java.util.UUID;

public interface IItemRepository {
    ItemDTO GetItemByItemId(UUID itemId);
    List<ItemDTO> GetItemByNameCategory(String nameCategory);
    Item create(ItemDTO itemDTO);
    Item update(ItemDTO itemDTO);
    Item delete(UUID itemId);
}
