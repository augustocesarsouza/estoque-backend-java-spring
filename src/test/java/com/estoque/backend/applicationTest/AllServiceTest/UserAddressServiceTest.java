package com.estoque.backend.applicationTest.AllServiceTest;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.AddressUpdateDTOValidator;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.AddressUpdateOnlyDefaultDTOValidator;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.UserAddressCreateValidatorDTO;
import com.estoque.backend.application.services.ErrorValidation;
import com.estoque.backend.application.services.ResultService;
import com.estoque.backend.application.services.UserAddressService;
import com.estoque.backend.application.services.interfaces.IUserManagementService;
import com.estoque.backend.domain.entities.User;
import com.estoque.backend.domain.entities.UserAddress;
import com.estoque.backend.domain.repositories.IUserAddressRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class UserAddressServiceTest {
    @Mock
    private IUserAddressRepository userAddressRepository;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private IUserManagementService userManagementService;

    private UserAddressService userAddressService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        userAddressService = new UserAddressService(userAddressRepository, validateErrorsDTO, modelMapper, userManagementService);
    }

    @Test
    public void should_GetAddressById_WithoutErrors(){
        String addressId = "56e921a3-f1de-4d9a-b2af-9e3d464cce86";
        UserAddressDTO userAddressDTO = new UserAddressDTO();

        when(userAddressRepository.GetAddressById(any())).thenReturn(userAddressDTO);

        // Act
        var result = userAddressService.GetAddressById(UUID.fromString(addressId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userAddressDTO, result.Data);
    }

    @Test
    public void should_findById_Return_Null_When_GetAddressById(){
        String addressId = "56e921a3-f1de-4d9a-b2af-9e3d464cce86";

        when(userAddressRepository.GetAddressById(any())).thenReturn(null);

        // Act
        var result = userAddressService.GetAddressById(UUID.fromString(addressId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "address does not found");
    }

    @Test
    public void should_GetAddressByIdWithUserProperty_WithoutErrors(){
        String addressId = "3c3d13fc-2c56-4df6-8468-cfbc2708488a";
        UserAddressDTO userAddressDTO = new UserAddressDTO();

        when(userAddressRepository.GetAddressByIdWithUserProperty(any())).thenReturn(userAddressDTO);

        // Act
        var result = userAddressService.GetAddressByIdWithUserProperty(UUID.fromString(addressId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userAddressDTO, result.Data);
    }

    @Test
    public void should_GetAddressByIdWithUserProperty_Return_Null(){
        String addressId = "3c3d13fc-2c56-4df6-8468-cfbc2708488a";

        when(userAddressRepository.GetAddressByIdWithUserProperty(any())).thenReturn(null);

        // Act
        var result = userAddressService.GetAddressByIdWithUserProperty(UUID.fromString(addressId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "address does not found");
    }

    @Test
    public void should_GetAddressByUserId_WithoutErrors(){
        String addressId = "3c3d13fc-2c56-4df6-8468-cfbc2708488a";
        List<UserAddressDTO> userAddressDTOs = new ArrayList<>();

        when(userAddressRepository.GetAddressByUserId(any())).thenReturn(userAddressDTOs);

        // Act
        var result = userAddressService.GetAddressByUserId(UUID.fromString(addressId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userAddressDTOs, result.Data);
    }

    @Test
    public void should_GetAddressByUserId_Return_Null(){
        String addressId = "3c3d13fc-2c56-4df6-8468-cfbc2708488a";

        when(userAddressRepository.GetAddressByUserId(any())).thenReturn(null);

        // Act
        var result = userAddressService.GetAddressByUserId(UUID.fromString(addressId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "list address does not found");
    }

    @Test
    public void should_CreateAsync_UserAddress_Successfully(){
        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        BindingResult bindingResult = new BeanPropertyBindingResult(userAddressCreateValidatorDTO, "UserAddressCreateValidatorDTO");

        var userAddressDTO = new UserAddressDTO();
        var userAddress = new UserAddress();

        ResultService<UserDTO> mockResult = ResultService.Ok(new UserDTO());

        when(userManagementService.VerifyWhetherUserExist(any())).thenReturn(mockResult);
        when(userAddressRepository.VerifyIfUserAlreadyHaveAddress(any())).thenReturn(new UserAddressDTO());
        when(userAddressRepository.create(any())).thenReturn(userAddress);
//        when(modelMapper.map(UserAddress.class, UserAddressDTO.class)).thenReturn(userAddressDTO);
        when(modelMapper.map(userAddress, UserAddressDTO.class)).thenReturn(userAddressDTO);

        // Act
        var result = userAddressService.CreateAsync(userAddressCreateValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, userAddressDTO);
    }

    @Test
    public void error_CreateAsync_DTO_Is_Null(){
        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        BindingResult bindingResult = new BeanPropertyBindingResult(userAddressCreateValidatorDTO, "UserAddressCreateValidatorDTO");

        // Act
        var result = userAddressService.CreateAsync(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }
    @Test
    public void error_validate_DTO_CreateAsync(){
        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        BindingResult bindingResult = new BeanPropertyBindingResult(userAddressCreateValidatorDTO, "UserAddressCreateValidatorDTO");

        bindingResult.rejectValue("FullName", "NotEmpty", "FullName should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("FullName", "FullName should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userAddressService.CreateAsync(userAddressCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Return_Error_VerifyWhetherUserExist_CreateAsync_UserAddress(){
        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        BindingResult bindingResult = new BeanPropertyBindingResult(userAddressCreateValidatorDTO, "UserAddressCreateValidatorDTO");

        var mockResult = ResultService.Fail(new UserDTO());

        when(userManagementService.VerifyWhetherUserExist(any())).thenReturn(mockResult);

        // Act
        var result = userAddressService.CreateAsync(userAddressCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error User not exist");
    }

    @Test
    public void should_ThrowException_When_UserAddress(){
        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        BindingResult bindingResult = new BeanPropertyBindingResult(userAddressCreateValidatorDTO, "UserAddressCreateValidatorDTO");

        String expectedErrorMessage = "Database connection error";

        ResultService<UserDTO> mockResult = ResultService.Ok(new UserDTO());

        when(userManagementService.VerifyWhetherUserExist(any())).thenReturn(mockResult);
        when(userAddressRepository.VerifyIfUserAlreadyHaveAddress(any())).thenReturn(new UserAddressDTO());
        when(userAddressRepository.create(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userAddressService.CreateAsync(userAddressCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_CreateToUser_Successfully(){
        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        var userAddressDTO = new UserAddressDTO();
        var userAddress = new UserAddress();

        ResultService<UserDTO> mockResult = ResultService.Ok(new UserDTO());

        when(userManagementService.VerifyWhetherUserExist(any())).thenReturn(mockResult);
        when(userAddressRepository.VerifyIfUserAlreadyHaveAddress(any())).thenReturn(new UserAddressDTO());
        when(userAddressRepository.create(any())).thenReturn(userAddress);
//        when(modelMapper.map(UserAddress.class, UserAddressDTO.class)).thenReturn(userAddressDTO);
        when(modelMapper.map(userAddress, UserAddressDTO.class)).thenReturn(userAddressDTO);

        // Act
        var result = userAddressService.CreateToUser(userAddressCreateValidatorDTO);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, userAddressDTO);
    }

    @Test
    public void should_Return_Error_VerifyWhetherUserExist_CreateAsync_CreateToUser(){
        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        var mockResult = ResultService.Fail(new UserDTO());

        when(userManagementService.VerifyWhetherUserExist(any())).thenReturn(mockResult);

        // Act
        var result = userAddressService.CreateToUser(userAddressCreateValidatorDTO);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error User does not exist");
    }

    @Test
    public void should_ThrowException_When_CreateToUser(){
        var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", (byte)1);
        userAddressCreateValidatorDTO.setUserId("178e4d4e-ead1-49a9-87a3-9b169b7134f3");

        String expectedErrorMessage = "Database connection error";

        ResultService<UserDTO> mockResult = ResultService.Ok(new UserDTO());

        when(userManagementService.VerifyWhetherUserExist(any())).thenReturn(mockResult);
        when(userAddressRepository.VerifyIfUserAlreadyHaveAddress(any())).thenReturn(new UserAddressDTO());
        when(userAddressRepository.create(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userAddressService.CreateToUser(userAddressCreateValidatorDTO);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_UpdateAddressUser_Successfully(){
        var addressUpdateDTOValidator = new AddressUpdateDTOValidator("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", "complement",
                "530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d", (byte)1);
        addressUpdateDTOValidator.setId("2666cc42-b40b-4558-98e3-e392f7bd68da");
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateDTOValidator, "AddressUpdateDTOValidator");

        var userAddressDTO = new UserAddressDTO();
        userAddressDTO.setUserDTO(new UserDTO());
        var userAddress = new UserAddress();

        when(userAddressRepository.GetAddressByIdToDelete(any())).thenReturn(userAddressDTO);
        when(userAddressRepository.update(any())).thenReturn(userAddress);
//        when(modelMapper.map(UserAddress.class, UserAddressDTO.class)).thenReturn(userAddressDTO);
        when(modelMapper.map(userAddress, UserAddressDTO.class)).thenReturn(userAddressDTO);

        // Act
        var result = userAddressService.UpdateAddressUser(addressUpdateDTOValidator, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, userAddressDTO);
    }

    @Test
    public void error_UpdateAddressUser_DTO_Is_Null(){
        var addressUpdateDTOValidator = new AddressUpdateDTOValidator("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", "complement",
                "530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d", (byte)1);
        addressUpdateDTOValidator.setId("2666cc42-b40b-4558-98e3-e392f7bd68da");
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateDTOValidator, "AddressUpdateDTOValidator");

        // Act
        var result = userAddressService.UpdateAddressUser(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }
    @Test
    public void error_validate_DTO_UpdateAddressUser(){
        var addressUpdateDTOValidator = new AddressUpdateDTOValidator("", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", "complement",
                "530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d", (byte)1);
        addressUpdateDTOValidator.setId("2666cc42-b40b-4558-98e3-e392f7bd68da");
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateDTOValidator, "AddressUpdateDTOValidator");

        bindingResult.rejectValue("FullName", "NotEmpty", "FullName should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("FullName", "FullName should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userAddressService.UpdateAddressUser(addressUpdateDTOValidator, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_GetAddressByIdToDelete_Return_Null_UpdateAddressUser(){
        var addressUpdateDTOValidator = new AddressUpdateDTOValidator("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", "complement",
                "530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d", (byte)1);
        addressUpdateDTOValidator.setId("2666cc42-b40b-4558-98e3-e392f7bd68da");
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateDTOValidator, "AddressUpdateDTOValidator");

        when(userAddressRepository.GetAddressByIdToDelete(any())).thenReturn(null);

        // Act
        var result = userAddressService.UpdateAddressUser(addressUpdateDTOValidator, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error Address does not found");
    }

    @Test
    public void should_ThrowException_When_UpdateAddressUser(){
        var addressUpdateDTOValidator = new AddressUpdateDTOValidator("augusto", "(+43) 23 45345 5644", "32323-23",
                "stateCity1", "neighborhood1", "street1", "3232", "complement",
                "530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d", (byte)1);
        addressUpdateDTOValidator.setId("2666cc42-b40b-4558-98e3-e392f7bd68da");
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateDTOValidator, "AddressUpdateDTOValidator");

        var userAddressDTO = new UserAddressDTO();
        userAddressDTO.setUserDTO(new UserDTO());
        String expectedErrorMessage = "Database connection error";

        when(userAddressRepository.GetAddressByIdToDelete(any())).thenReturn(userAddressDTO);
        when(userAddressRepository.update(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userAddressService.UpdateAddressUser(addressUpdateDTOValidator, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_UpdateAsyncOnlyDefaultAddress_Successfully(){
        var addressUpdateOnlyDefaultDTOValidator = new AddressUpdateOnlyDefaultDTOValidator("530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d",
                1);
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateOnlyDefaultDTOValidator, "AddressUpdateOnlyDefaultDTOValidator");

        var userAddressMap = new UserAddress();
        var userAddressDTO = new UserAddressDTO();
        var userAddressDTOGetAddressDefaultAllInfo = new UserAddressDTO();

        var userAddressUpdate = new UserAddress();

        var userAddressDTOMap = new UserAddressDTO();
        userAddressDTOMap.setUserDTO(new UserDTO());

        when(userAddressRepository.GetAddressById(any())).thenReturn(userAddressDTO);
        when(userAddressRepository.GetAddressDefaultAllInfo()).thenReturn(userAddressDTOGetAddressDefaultAllInfo);
        when(modelMapper.map(userAddressDTO, UserAddress.class)).thenReturn(userAddressMap);
        when(userAddressRepository.update(any())).thenReturn(userAddressUpdate);
//        when(modelMapper.map(UserAddress.class, UserAddressDTO.class)).thenReturn(userAddressDTOMap);
        when(modelMapper.map(userAddressUpdate, UserAddressDTO.class)).thenReturn(userAddressDTOMap);

        // Act
        var result = userAddressService.UpdateAsyncOnlyDefaultAddress(addressUpdateOnlyDefaultDTOValidator, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, userAddressDTOMap);
    }

    @Test
    public void error_UpdateAsyncOnlyDefaultAddress_DTO_Is_Null(){
        var addressUpdateOnlyDefaultDTOValidator = new AddressUpdateOnlyDefaultDTOValidator("530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d",
                1);
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateOnlyDefaultDTOValidator, "AddressUpdateOnlyDefaultDTOValidator");

        // Act
        var result = userAddressService.UpdateAsyncOnlyDefaultAddress(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }
    @Test
    public void error_validate_DTO_UpdateAsyncOnlyDefaultAddress(){
        var addressUpdateOnlyDefaultDTOValidator = new AddressUpdateOnlyDefaultDTOValidator("530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d",
                1);
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateOnlyDefaultDTOValidator, "AddressUpdateOnlyDefaultDTOValidator");

        bindingResult.rejectValue("Id", "NotEmpty", "Id should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("Id", "Id should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userAddressService.UpdateAsyncOnlyDefaultAddress(addressUpdateOnlyDefaultDTOValidator, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Error_GetAddressById_Return_Null_UpdateAsyncOnlyDefaultAddress(){
        var addressUpdateOnlyDefaultDTOValidator = new AddressUpdateOnlyDefaultDTOValidator("530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d",
                1);
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateOnlyDefaultDTOValidator, "AddressUpdateOnlyDefaultDTOValidator");

        when(userAddressRepository.GetAddressById(any())).thenReturn(null);

        // Act
        var result = userAddressService.UpdateAsyncOnlyDefaultAddress(addressUpdateOnlyDefaultDTOValidator, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error Address does not found");
    }

    @Test
    public void should_ThrowException_When_UpdateAsyncOnlyDefaultAddress(){
        var addressUpdateOnlyDefaultDTOValidator = new AddressUpdateOnlyDefaultDTOValidator("530aa082-ea42-4c9d-b6ab-bf49b8f9ed7d",
                1);
        BindingResult bindingResult = new BeanPropertyBindingResult(addressUpdateOnlyDefaultDTOValidator, "AddressUpdateOnlyDefaultDTOValidator");

        var userAddressMap = new UserAddress();
        var userAddressDTO = new UserAddressDTO();
        var userAddressDTOGetAddressDefaultAllInfo = new UserAddressDTO();

        String expectedErrorMessage = "Database connection error";

        when(userAddressRepository.GetAddressById(any())).thenReturn(userAddressDTO);
        when(userAddressRepository.GetAddressDefaultAllInfo()).thenReturn(userAddressDTOGetAddressDefaultAllInfo);
        when(modelMapper.map(userAddressDTO, UserAddress.class)).thenReturn(userAddressMap);
        when(userAddressRepository.update(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userAddressService.UpdateAsyncOnlyDefaultAddress(addressUpdateOnlyDefaultDTOValidator, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_Delete_Successfully(){
        var addressId = "2666cc42-b40b-4558-98e3-e392f7bd68da";

        var userAddressDTO = new UserAddressDTO();
        var userAddress = new UserAddress();
        userAddress.setUser(new User());

        var userAddressDTOMap = new UserAddressDTO();

        when(userAddressRepository.GetAddressByIdToDelete(any())).thenReturn(userAddressDTO);
        when(userAddressRepository.delete(any())).thenReturn(userAddress);
        when(modelMapper.map(userAddress, UserAddressDTO.class)).thenReturn(userAddressDTOMap);

        // Act
        var result = userAddressService.Delete(UUID.fromString(addressId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data, userAddressDTOMap);
    }

    @Test
    public void should_Error_GetAddressByIdToDelete_Return_Null_Delete(){
        var addressId = "2666cc42-b40b-4558-98e3-e392f7bd68da";

        when(userAddressRepository.GetAddressByIdToDelete(any())).thenReturn(null);

        // Act
        var result = userAddressService.Delete(UUID.fromString(addressId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Address does not found");
    }

    @Test
    public void should_ThrowException_When_Delete(){
        var addressId = "2666cc42-b40b-4558-98e3-e392f7bd68da";

        var userAddressDTO = new UserAddressDTO();

        String expectedErrorMessage = "Database connection error";

        when(userAddressRepository.GetAddressByIdToDelete(any())).thenReturn(userAddressDTO);
        when(userAddressRepository.delete(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userAddressService.Delete(UUID.fromString(addressId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }
}