package com.estoque.backend.applicationTest.AllServiceTest;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.ItemValidationDTOs.ItemCreateValidatorDTO;
import com.estoque.backend.application.services.ItemService;
import com.estoque.backend.data.utilityExternal.Interface.ICloudinaryUti;
import com.estoque.backend.domain.entities.Category;
import com.estoque.backend.domain.entities.Item;
import com.estoque.backend.domain.repositories.IItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ItemServiceTest {
    @Mock
    private IItemRepository iItemRepository;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ICloudinaryUti cloudinaryUti;

    private ItemService itemService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        itemService = new ItemService(iItemRepository, validateErrorsDTO, modelMapper, cloudinaryUti);
    }

    @Test
    public void should_GetItemByNameCategory_WithoutErrors(){
        String nameCategory = "nameCategory";
        List<ItemDTO> itemDTOS = new ArrayList<>();

        when(iItemRepository.GetItemByNameCategory(any())).thenReturn(itemDTOS);

        // Act
        var result = itemService.GetItemByNameCategory(nameCategory);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(itemDTOS, result.Data);
    }

    @Test
    public void should_GetItemByNameCategory_Return_Null_When_GetAddressById() {
        String nameCategory = "nameCategory";

        when(iItemRepository.GetItemByNameCategory(any())).thenReturn(null);

        // Act
        var result = itemService.GetItemByNameCategory(nameCategory);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "itemDTOs does not found");
    }

    @Test
    public void should_CreateAsync_Item_Successfully(){
        var itemCreateValidatorDTO = new ItemCreateValidatorDTO("augusto",124.1, 20, "12,13",
                "brand123", null, null, null);

        BindingResult bindingResult = new BeanPropertyBindingResult(itemCreateValidatorDTO, "ItemCreateValidatorDTO");

        var item = new Item();
        var itemDTOMap = new ItemDTO();

        when(iItemRepository.create(any())).thenReturn(item);
//        when(modelMapper.map(Item.class, ItemDTO.class)).thenReturn(itemDTOMap);
        when(modelMapper.map(item, ItemDTO.class)).thenReturn(itemDTOMap);

        // Act
        var result = itemService.CreateAsync(itemCreateValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, itemDTOMap);
    }

    @Test
    public void error_CreateAsync_DTO_Is_Null(){
        BindingResult bindingResult = new BeanPropertyBindingResult(new ItemCreateValidatorDTO(), "ItemCreateValidatorDTO");
        // Act
        var result = itemService.CreateAsync(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }

    @Test
    public void should_ThrowException_When_UserAddress(){
        var itemCreateValidatorDTO = new ItemCreateValidatorDTO("augusto",124.1, 20, "12,13",
                "brand123", null, null, null);

        BindingResult bindingResult = new BeanPropertyBindingResult(itemCreateValidatorDTO, "ItemCreateValidatorDTO");

        String expectedErrorMessage = "Database connection error";

        when(iItemRepository.create(any())).thenThrow(new RuntimeException(expectedErrorMessage));
//        when(modelMapper.map(item, ItemDTO.class)).thenReturn(itemDTOMap);

        // Act
        var result = itemService.CreateAsync(itemCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_Delete_Successfully(){
        var itemId = "2666cc42-b40b-4558-98e3-e392f7bd68da";

        var item = new Item();
        var itemDTO = new ItemDTO();
        var itemDTOMap = new ItemDTO();

        when(iItemRepository.GetItemByItemId(any())).thenReturn(itemDTO);
        when(iItemRepository.delete(any())).thenReturn(item);
        when(modelMapper.map(item, ItemDTO.class)).thenReturn(itemDTOMap);

        // Act
        var result = itemService.Delete(UUID.fromString(itemId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, itemDTOMap);
    }

    @Test
    public void should_ThrowException_When_Item(){
        var itemId = "2666cc42-b40b-4558-98e3-e392f7bd68da";

        String expectedErrorMessage = "Database connection error";

        when(iItemRepository.GetItemByItemId(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = itemService.Delete(UUID.fromString(itemId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }
}
