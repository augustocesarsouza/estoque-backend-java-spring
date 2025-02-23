package com.estoque.backend.application.services.interfaces;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.application.dto.validations.ItemValidationDTOs.ItemCreateValidatorDTO;
import com.estoque.backend.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface IItemService {
    ResultService<List<ItemDTO>> GetItemByNameCategory(String nameCategory);
    ResultService<ItemDTO> CreateAsync(ItemCreateValidatorDTO itemCreateValidatorDTO, BindingResult result);
    ResultService<ItemDTO> Delete(UUID itemId);
}
