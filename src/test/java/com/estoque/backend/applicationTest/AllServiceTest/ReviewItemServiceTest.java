package com.estoque.backend.applicationTest.AllServiceTest;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.application.dto.ReviewItemDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.ReviewItemValidationDTOs.ReviewItemCreateValidatorDTO;
import com.estoque.backend.application.services.ErrorValidation;
import com.estoque.backend.application.services.ReviewItemService;
import com.estoque.backend.domain.entities.ReviewItem;
import com.estoque.backend.domain.repositories.IItemRepository;
import com.estoque.backend.domain.repositories.IReviewItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class ReviewItemServiceTest {
    @Mock
    private PlatformTransactionManager transactionManager;
    @Mock
    private IReviewItemRepository reviewItemRepository;
    @Mock
    private IItemRepository itemRepository;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private ModelMapper modelMapper;

    private ReviewItemService reviewItemService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        reviewItemService = new ReviewItemService(transactionManager, reviewItemRepository, itemRepository, validateErrorsDTO, modelMapper);
    }

    @Test
    public void should_GetReviewItemById_WithoutErrors(){
        UUID reviewItemId = UUID.fromString("2e078522-320a-4502-8f5c-cdb9c983c1ef");
        ReviewItemDTO dto = new ReviewItemDTO();

        when(reviewItemRepository.GetReviewItemById(any())).thenReturn(dto);

        // Act
        var result = reviewItemService.GetReviewItemById(reviewItemId);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(dto, result.Data);
    }

    @Test
    public void should_GetReviewItemById_Return_Null_When_GetAddressById() {
        UUID reviewItemId = UUID.fromString("2e078522-320a-4502-8f5c-cdb9c983c1ef");

        when(reviewItemRepository.GetReviewItemById(any())).thenReturn(null);

        // Act
        var result = reviewItemService.GetReviewItemById(reviewItemId);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "ReviewItem does not found");
    }

    @Test
    public void should_GetAllReviewItemsByItemId_WithoutErrors(){
        UUID reviewItemId = UUID.fromString("2e078522-320a-4502-8f5c-cdb9c983c1ef");
        List<ReviewItemDTO> dtos = new ArrayList<>();

        when(reviewItemRepository.GetAllReviewItemsByItemId(any())).thenReturn(dtos);

        // Act
        var result = reviewItemService.GetAllReviewItemsByItemId(reviewItemId);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(dtos, result.Data);
    }

    @Test
    public void should_GetAllReviewItemsByItemId_Return_Null_When_GetAddressById() {
        UUID reviewItemId = UUID.fromString("2e078522-320a-4502-8f5c-cdb9c983c1ef");

        when(reviewItemRepository.GetAllReviewItemsByItemId(any())).thenReturn(null);

        // Act
        var result = reviewItemService.GetAllReviewItemsByItemId(reviewItemId);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "ReviewItem list does not found");
    }

    @Test
    public void should_CreateAsync_UserAddress_Successfully(){
        var reviewItemCreateValidatorDTO = new ReviewItemCreateValidatorDTO(null, "nameUser",
                "augusto@gmail.com", "reviewTitle1",
                "description1", (short)1, (short)2, (short)3, "0e836a91-b410-4b4d-8643-b2ba91f0bfe8");

//        ReviewItemCreateValidatorDTO(UUID id, String nameUser, String email, String reviewTitle, String description,
//                Short fitRating, Short qualityRating, Short priceRating, String itemsId)

        BindingResult bindingResult = new BeanPropertyBindingResult(reviewItemCreateValidatorDTO, "ReviewItemCreateValidatorDTO");

        var reviewItemDTO = new ReviewItemDTO();
        var createEntity = new ReviewItem();

        var verifyWhetherExistItem = new ItemDTO();

        when(itemRepository.GetItemByItemId(any())).thenReturn(verifyWhetherExistItem);
        when(reviewItemRepository.create(any())).thenReturn(createEntity);
        when(modelMapper.map(createEntity, ReviewItemDTO.class)).thenReturn(reviewItemDTO);

        // Act
        var result = reviewItemService.CreateAsync(reviewItemCreateValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, reviewItemDTO);
    }

    @Test
    public void error_CreateAsync_DTO_Is_Null(){
//        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("augusto", "(+43) 23 45345 5644", "32323-23",
//                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
//        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        BindingResult bindingResult = new BeanPropertyBindingResult(new ReviewItemCreateValidatorDTO(), "ReviewItemCreateValidatorDTO");

        // Act
        var result = reviewItemService.CreateAsync(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }

    @Test
    public void error_validate_DTO_CreateAsync(){
        var reviewItemCreateValidatorDTO = new ReviewItemCreateValidatorDTO(null, "nameUser",
                "augusto@gmail.com", "reviewTitle1",
                "description1", (short)1, (short)2, (short)3, "0e836a91-b410-4b4d-8643-b2ba91f0bfe8");

        BindingResult bindingResult = new BeanPropertyBindingResult(new ReviewItemCreateValidatorDTO(), "ReviewItemCreateValidatorDTO");

        bindingResult.rejectValue("nameUser", "NotEmpty", "nameUser should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("nameUser", "nameUser should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = reviewItemService.CreateAsync(reviewItemCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Return_Null_When_Get_Item_If_Exist_CreateAsync(){
        var reviewItemCreateValidatorDTO = new ReviewItemCreateValidatorDTO(null, "nameUser",
                "augusto@gmail.com", "reviewTitle1",
                "description1", (short)1, (short)2, (short)3, "0e836a91-b410-4b4d-8643-b2ba91f0bfe8");

//        ReviewItemCreateValidatorDTO(UUID id, String nameUser, String email, String reviewTitle, String description,
//                Short fitRating, Short qualityRating, Short priceRating, String itemsId)

        BindingResult bindingResult = new BeanPropertyBindingResult(reviewItemCreateValidatorDTO, "ReviewItemCreateValidatorDTO");

        when(itemRepository.GetItemByItemId(any())).thenReturn(null);

        // Act
        var result = reviewItemService.CreateAsync(reviewItemCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error Item does not found");
    }

    @Test
    public void should_ThrowException_WhenGetItemByItemId_CreateAsync(){
        var reviewItemCreateValidatorDTO = new ReviewItemCreateValidatorDTO(null, "nameUser",
                "augusto@gmail.com", "reviewTitle1",
                "description1", (short)1, (short)2, (short)3, "0e836a91-b410-4b4d-8643-b2ba91f0bfe8");

        BindingResult bindingResult = new BeanPropertyBindingResult(reviewItemCreateValidatorDTO, "ReviewItemCreateValidatorDTO");

        var verifyWhetherExistItem = new ItemDTO();

        String expectedErrorMessage = "Database connection error";

        when(itemRepository.GetItemByItemId(any())).thenReturn(verifyWhetherExistItem);
        when(reviewItemRepository.create(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = reviewItemService.CreateAsync(reviewItemCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_Delete_Successfully(){
        var reviewItemId = "8a051abf-e06e-4a41-a86c-d8ed43d124a8";

        var entityDeleteSuccessfully = new ReviewItem();
        var userAddressDTOMap = new ReviewItemDTO();

        when(reviewItemRepository.delete(any())).thenReturn(entityDeleteSuccessfully);
        when(modelMapper.map(entityDeleteSuccessfully, ReviewItemDTO.class)).thenReturn(userAddressDTOMap);

        // Act
        var result = reviewItemService.Delete(UUID.fromString(reviewItemId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, userAddressDTOMap);
    }

    @Test
    public void should_Return_Null_Id_Provided_Is_Null_Delete(){
        // Act
        var result = reviewItemService.Delete(null);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "review item id is null");
    }

    @Test
    public void should_Delete_Return_Null(){
        var reviewItemId = "8a051abf-e06e-4a41-a86c-d8ed43d124a8";

        when(reviewItemRepository.delete(any())).thenReturn(null);

        // Act
        var result = reviewItemService.Delete(UUID.fromString(reviewItemId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "ReviewItem does not found");
    }

    @Test
    public void should_ThrowException_When_Delete(){
        var reviewItemId = "8a051abf-e06e-4a41-a86c-d8ed43d124a8";

        String expectedErrorMessage = "Database connection error";

        when(reviewItemRepository.delete(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = reviewItemService.Delete(UUID.fromString(reviewItemId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }
}
