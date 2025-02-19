package com.estoque.backend.applicationTest.AllServiceTest;

import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserCreateLoginGoogleValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserCreateValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserUpdatePasswordValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserUpdateValidatorDTO;
import com.estoque.backend.application.services.ErrorValidation;
import com.estoque.backend.application.services.UserManagementService;
import com.estoque.backend.application.util.interfaces.IBCryptPasswordEncoderUtil;
import com.estoque.backend.data.utilityExternal.Interface.ICloudinaryUti;
import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.authentication.ITokenGenerator;
import com.estoque.backend.domain.authentication.TokenOutValue;
import com.estoque.backend.domain.entities.User;
import com.estoque.backend.domain.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class UserManagementServiceTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    @Mock
    private IBCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;
    @Mock
    private ICloudinaryUti cloudinaryUti;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ITokenGenerator tokenGenerator;
    @Mock
    private AuthenticationManager authenticationManager;
    private Authentication authenticate;

    private UserManagementService userManagementService;

//    @BeforeEach
//    public void setup(){
//        MockitoAnnotations.openMocks(this);
//        userManagementService = new UserManagementService(userRepository, validateErrorsDTO, bCryptPasswordEncoderUtil,
//                cloudinaryUti, modelMapper, tokenGenerator, authenticationManager);
//    }

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        userManagementService = new UserManagementService(userRepository, validateErrorsDTO, bCryptPasswordEncoderUtil,
                cloudinaryUti, modelMapper, tokenGenerator, authenticationManager);

        authenticate = mock(Authentication.class);
    }

    @Test
    public void should_FindById_WithoutErrors(){
        String phone = "8273636736";
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.GetUserByPhoneInfoUpdate(any())).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        var result = userManagementService.findById(phone);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userDTO, result.Data);
    }

    @Test
    public void should_findById_Return_Null_When_GetUserByPhoneInfoUpdate(){
        String phone = "8273636736";

        when(userRepository.GetUserByPhoneInfoUpdate(any())).thenReturn(null);

        // Act
        var result = userManagementService.findById(phone);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found");
    }

    @Test
    public void should_findByIdToDatePersonal_WithoutErrors(){
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findByIdToDatePersonal(any())).thenReturn(userDTO);

        // Act
        var result = userManagementService.findByIdToDatePersonal(userId);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userDTO, result.Data);
    }

    @Test
    public void should_Return_Null_When_findByIdToDatePersonal(){
        UUID userId = UUID.randomUUID();

        when(userRepository.findByIdToDatePersonal(any())).thenReturn(null);

        // Act
        var result = userManagementService.findByIdToDatePersonal(userId);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found user");
    }

    @Test
    public void should_VerifyWhetherUserExist_WithoutErrors(){
        UUID userId = UUID.randomUUID();
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.GetUserByIdForDeleteImg(any())).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        var result = userManagementService.VerifyWhetherUserExist(userId);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userDTO, result.Data);
    }

    @Test
    public void should_VerifyWhetherUserExist_Return_Null(){
        UUID userId = UUID.randomUUID();

        when(userRepository.GetUserByIdForDeleteImg(any())).thenReturn(null);

        // Act
        var result = userManagementService.VerifyWhetherUserExist(userId);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "not found");
    }

    @Test
    public void should_Create_Successfully(){
        var userCreateValidatorDTO = new UserCreateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");
        User user = new User();
        UserDTO userDTO = new UserDTO();

        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.create(any())).thenReturn(user);
        when(tokenGenerator.generatorTokenUser(any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userDTO, result.Data);
    }

    @Test
    public void error_create_DTO_Is_Null(){
        var userCreateValidatorDTO = new UserCreateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        var resultError = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        // Act
        var result = userManagementService.create(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }
    @Test
    public void error_validate_DTO_create(){
        UserCreateValidatorDTO userCreateValidatorDTO = new UserCreateValidatorDTO();
        var resultError = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        resultError.rejectValue("BirthDate", "NotEmpty", "birthDate should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("BirthDate", "birthDate should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Throw_Error_When_Create_Repository(){
        var userCreateValidatorDTO = new UserCreateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");

        when(userRepository.create(any())).thenReturn(null);

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error User Creation is null");
    }

    @Test
    public void should_Throw_Error_When_Create_Token_Create(){
        var userCreateValidatorDTO = new UserCreateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");
        User user = new User();

        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.create(any())).thenReturn(user);
        when(tokenGenerator.generatorTokenUser(any()))
                .thenReturn(InfoErrors.Fail(TokenOutValue, "error token"));

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error token");
    }

    @Test
    public void should_ThrowException_When_create(){
        var userCreateValidatorDTO = new UserCreateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateValidatorDTO, "userCreateValidatorDTO");
        String expectedErrorMessage = "Database connection error";

        when(userRepository.create(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userManagementService.create(userCreateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_createToLoginGoogle_Successfully_User_Exist(){
        var userCreateLoginGoogleValidatorDTO = new UserCreateLoginGoogleValidatorDTO("augusto", "augusto@gmail.com", "cesar");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateLoginGoogleValidatorDTO, "UserCreateLoginGoogleValidatorDTO");
        User user = new User();
        UserDTO userDTO = new UserDTO();

        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.GetUserByEmail(any())).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(tokenGenerator.generatorTokenUser(any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));

        // Act
        var result = userManagementService.createToLoginGoogle(userCreateLoginGoogleValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userDTO, result.Data);
    }

    @Test
    public void should_createToLoginGoogle_Successfully_User_Does_Not_Exist(){
        var userCreateLoginGoogleValidatorDTO = new UserCreateLoginGoogleValidatorDTO("augusto", "augusto@gmail.com", "cesar");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateLoginGoogleValidatorDTO, "UserCreateLoginGoogleValidatorDTO");
        User user = new User();
        UserDTO userDTO = new UserDTO();

        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.GetUserByEmail(any())).thenReturn(null);
        when(userRepository.create(any())).thenReturn(user);
        when(tokenGenerator.generatorTokenUser(any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        var result = userManagementService.createToLoginGoogle(userCreateLoginGoogleValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userDTO, result.Data);
    }

    @Test
    public void should_Throw_Error_User_Not_Created_createToLoginGoogle(){
        var userCreateLoginGoogleValidatorDTO = new UserCreateLoginGoogleValidatorDTO("augusto", "augusto@gmail.com", "cesar");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateLoginGoogleValidatorDTO, "UserCreateLoginGoogleValidatorDTO");

        when(userRepository.GetUserByEmail(any())).thenReturn(null);
        when(userRepository.create(any())).thenReturn(null);
        // Act
        var result = userManagementService.createToLoginGoogle(userCreateLoginGoogleValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error User Creation is null");
    }

    @Test
    public void should_ThrowException_When_createToLoginGoogle(){
        var userCreateLoginGoogleValidatorDTO = new UserCreateLoginGoogleValidatorDTO("augusto", "augusto@gmail.com", "cesar");

        BindingResult bindingResult = new BeanPropertyBindingResult(userCreateLoginGoogleValidatorDTO, "UserCreateLoginGoogleValidatorDTO");

        String expectedErrorMessage = "Database connection error";

        when(userRepository.GetUserByEmail(any())).thenReturn(null);
        when(userRepository.create(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userManagementService.createToLoginGoogle(userCreateLoginGoogleValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_UpdateUser_Successfully(){
        var userUpdateValidatorDTO = new UserUpdateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");
        User user = new User();
        User userUpdate = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.GetUserByEmail(any())).thenReturn(user);
        when(userRepository.update(any())).thenReturn(userUpdate);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        // Act
        var result = userManagementService.UpdateUser(userUpdateValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(userDTO, result.Data);
    }

    @Test
    public void error_UpdateUser_DTO_Is_Null(){
        var userUpdateValidatorDTO = new UserUpdateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");

        // Act
        var result = userManagementService.UpdateUser(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }
    @Test
    public void error_validate_DTO_UpdateUser(){
        var userUpdateValidatorDTO = new UserUpdateValidatorDTO("augusto", "cesar", null,
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult resultError = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");
        resultError.rejectValue("BirthDate", "NotEmpty", "birthDate should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("BirthDate", "birthDate should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userManagementService.UpdateUser(userUpdateValidatorDTO, resultError);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_UpdateUser_Verify_By_Email_If_User_Does_Exist(){
        var userUpdateValidatorDTO = new UserUpdateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");

        when(userRepository.GetUserByEmail(any())).thenReturn(null);

        // Act
        var result = userManagementService.UpdateUser(userUpdateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error user does not exist");
    }

    @Test
    public void should_ThrowException_When_UpdateUser(){
        var userUpdateValidatorDTO = new UserUpdateValidatorDTO("augusto", "cesar", "05/10/1999",
                "M", "232.323.232-32", "augusto@gmail.com", "(12) 3123-1231", "(+43) 23 45345 5644",
                "", "Augusto92349923");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");

        String expectedErrorMessage = "Database connection error";

        when(userRepository.GetUserByEmail(any())).thenReturn(new User());
        when(userRepository.update(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userManagementService.UpdateUser(userUpdateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }

    @Test
    public void should_UpdateUserPassword_Successfully(){
        var userUpdateValidatorDTO = new UserUpdatePasswordValidatorDTO("augusto", "augusto123",
                "219f849d-5318-4157-a852-a4c14660126b", "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");

        User user = new User();
        User userUpdate = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.GetUserByEmail(any())).thenReturn(user);

        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(new User());
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);

        when(userRepository.updatePassword(any())).thenReturn(userUpdate);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        // Act
        var result = userManagementService.UpdateUserPassword(userUpdateValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertTrue(result.Data.changePasswordSuccessfully());
    }

    @Test
    public void error_UpdateUserPassword_DTO_Is_Null(){
        var userUpdateValidatorDTO = new UserUpdatePasswordValidatorDTO("augusto", "augusto123",
                "219f849d-5318-4157-a852-a4c14660126b", "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");

        // Act
        var result = userManagementService.UpdateUserPassword(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Is Null");
    }
    @Test
    public void error_validate_DTO_UpdateUserPassword(){
        var userUpdateValidatorDTO = new UserUpdatePasswordValidatorDTO("augusto", "augusto123",
                "", "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");
        bindingResult.rejectValue("UserId", "Size", "userId should have at last 36 characters");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("UserId", "userId should have at last 36 characters");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userManagementService.UpdateUserPassword(userUpdateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Return_Null_UpdateUserPassword(){
        var userUpdateValidatorDTO = new UserUpdatePasswordValidatorDTO("augusto", "augusto123",
                "219f849d-5318-4157-a852-a4c14660126b", "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");

        when(userRepository.GetUserByEmail(any())).thenReturn(null);

        // Act
        var result = userManagementService.UpdateUserPassword(userUpdateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error user does not exist");
    }

    @Test
    public void error_Credentials_User_Is_Wrong_UpdateUserPassword(){
        var userUpdateValidatorDTO = new UserUpdatePasswordValidatorDTO("augusto", "augusto123",
                "219f849d-5318-4157-a852-a4c14660126b", "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");

        User user = new User();

        when(userRepository.GetUserByEmail(any())).thenReturn(user);
        when(authenticate.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("User Does Not Exist or password invalid"));

        // Act
        var result = userManagementService.UpdateUserPassword(userUpdateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertFalse(result.Data.changePasswordSuccessfully());
    }

    @Test
    public void should_ThrowException_When_UpdateUserPassword(){
        var userUpdateValidatorDTO = new UserUpdatePasswordValidatorDTO("augusto", "augusto123",
                "219f849d-5318-4157-a852-a4c14660126b", "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userUpdateValidatorDTO, "UserUpdateValidatorDTO");

        String expectedErrorMessage = "Database connection error";

        when(userRepository.GetUserByEmail(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(new User());
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userRepository.updatePassword(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userManagementService.UpdateUserPassword(userUpdateValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(expectedErrorMessage, result.Message);
    }
}
