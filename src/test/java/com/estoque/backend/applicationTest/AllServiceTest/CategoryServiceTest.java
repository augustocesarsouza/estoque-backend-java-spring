package com.estoque.backend.applicationTest.AllServiceTest;

import com.estoque.backend.application.dto.CategoryDTO;
import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.services.CategoryService;
import com.estoque.backend.domain.entities.Category;
import com.estoque.backend.domain.entities.Item;
import com.estoque.backend.domain.repositories.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private ModelMapper modelMapper;

    private CategoryService categoryService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryRepository, validateErrorsDTO, modelMapper);
    }

    @Test
    public void should_GetItemByCategoryId_WithoutErrors(){
        String categoryId = "719463b3-81e3-44e2-9680-9192e25be780";
        CategoryDTO categoryDTO = new CategoryDTO();

        when(categoryRepository.GetItemByCategoryId(any())).thenReturn(categoryDTO);

        // Act
        var result = categoryService.GetItemByCategoryId(UUID.fromString(categoryId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(categoryDTO, result.Data);
    }

    @Test
    public void should_GetItemByCategoryId_Return_Null_When_GetItemByCategoryId() {
        String categoryId = "719463b3-81e3-44e2-9680-9192e25be780";

        when(categoryRepository.GetItemByCategoryId(any())).thenReturn(null);

        // Act
        var result = categoryService.GetItemByCategoryId(UUID.fromString(categoryId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "category does not found");
    }

    @Test
    public void should_CreateAsync_Category_Successfully(){
        var categoryDTO = new CategoryDTO(UUID.fromString("97bf82aa-b623-481c-8d46-c2e470c85c5c"), "nameCategory",
                null);

        var category = new Category();
        var categoryDTOMap = new CategoryDTO();

        when(categoryRepository.create(any())).thenReturn(category);
//        when(modelMapper.map(Item.class, ItemDTO.class)).thenReturn(itemDTOMap);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTOMap);

        // Act
        var result = categoryService.CreateAsync(categoryDTO);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, categoryDTOMap);
    }

    @Test
    public void error_CreateAsync_DTO_Is_Null(){
        // Act
        var result = categoryService.CreateAsync(null);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }

    @Test
    public void should_ThrowException_When_Category(){
        var categoryDTO = new CategoryDTO(UUID.fromString("97bf82aa-b623-481c-8d46-c2e470c85c5c"), "nameCategory",
                null);

        String expectedErrorMessage = "Database connection error";

        when(categoryRepository.create(any())).thenThrow(new RuntimeException(expectedErrorMessage));
//        when(modelMapper.map(item, ItemDTO.class)).thenReturn(itemDTOMap);

        // Act
        var result = categoryService.CreateAsync(categoryDTO);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_Delete_Successfully(){
        var categoryId = "97bf82aa-b623-481c-8d46-c2e470c85c5c";


        var category = new Category();
        var categoryDTO = new CategoryDTO();
        var categoryDTOMap = new CategoryDTO();

        when(categoryRepository.GetItemByCategoryId(any())).thenReturn(categoryDTO);
        when(categoryRepository.delete(any())).thenReturn(category);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTOMap);

        // Act
        var result = categoryService.Delete(UUID.fromString(categoryId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, categoryDTOMap);
    }
}
