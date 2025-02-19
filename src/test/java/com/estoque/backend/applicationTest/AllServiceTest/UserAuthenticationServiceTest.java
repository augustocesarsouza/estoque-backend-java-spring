package com.estoque.backend.applicationTest.AllServiceTest;

import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.CodeSendEmailUserValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserConfirmCodeEmailValidatorDTO;
import com.estoque.backend.application.services.ErrorValidation;
import com.estoque.backend.application.services.UserAuthenticationService;
import com.estoque.backend.application.util.interfaces.IDictionaryCode;
import com.estoque.backend.data.utilityExternal.Interface.ISendEmailUser;
import com.estoque.backend.data.utilityExternal.Interface.ISendSmsTwilio;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserAuthenticationServiceTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ISendEmailUser sendEmailUser;
    @Mock
    private ISendSmsTwilio sendSmsTwilio;
    @Mock
    private IDictionaryCode dictionaryCode;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private ITokenGenerator tokenGenerator;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;
    private Authentication authenticate;

    private UserAuthenticationService userAuthenticationService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        userAuthenticationService = new UserAuthenticationService(userRepository, modelMapper, sendEmailUser,
                sendSmsTwilio, dictionaryCode, authenticationManager, tokenGenerator, validateErrorsDTO);

        authenticate = mock(Authentication.class);
    }

    @Test
    public void should_Login_Successfully() {
        String email = "augusto@gmail.com";
        String password = "password1";

        var userGetUserByName = new User();
        userGetUserByName.setId(UUID.fromString("cf0a72ef-9864-4a9b-93aa-2d1a3eaa0654"));

        var TokenOutValue = new TokenOutValue("access_token_random", null);

        var userDTO = new UserDTO();
        userDTO.setId(UUID.fromString("219d0f58-fad1-4339-b477-20b977021046"));

        when(userRepository.GetUserInfoToLogin(anyString())).thenReturn(new UserDTO());

        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(userGetUserByName);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);

        when(tokenGenerator.generatorTokenUser(any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));

        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        // Act
        var result = userAuthenticationService.Login(email, password);

        // Assert
        assertTrue(result.IsSuccess);
        assertTrue(result.Data.getPasswordIsCorrect());
    }

    @Test
    public void error_GetUserInfoToLogin_Return_Null_Login() {
        String email = "augusto@gmail.com";
        String password = "password1";

        when(userRepository.GetUserInfoToLogin(anyString())).thenReturn(null);

        // Act
        var result = userAuthenticationService.Login(email, password);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error user info login is null");
    }

    @Test
    public void error_Credentials_User_Is_Wrong_Login() {
        String email = "augusto@gmail.com";
        String password = "password1";

        var userGetUserByName = new UserDTO();
        userGetUserByName.setId(UUID.fromString("cf0a72ef-9864-4a9b-93aa-2d1a3eaa0654"));

        when(userRepository.GetUserInfoToLogin(anyString())).thenReturn(userGetUserByName);

        when(authenticate.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("User does Not Exist or password invalid"));

        // Act
        var result = userAuthenticationService.Login(email, password);

        // Assert
        assertFalse(result.IsSuccess);
        assertFalse(result.Data.getPasswordIsCorrect());
        assertEquals(result.Data.getMessage(), "User does Not Exist or password invalid");
    }

    @Test
    public void error_Token_Generate_Login() {
        String email = "augusto@gmail.com";
        String password = "password1";

        var userGetUserByName = new UserDTO();
        userGetUserByName.setId(UUID.fromString("cf0a72ef-9864-4a9b-93aa-2d1a3eaa0654"));

        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.GetUserInfoToLogin(anyString())).thenReturn(userGetUserByName);

        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(userGetUserByName);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);

        when(tokenGenerator.generatorTokenUser(any()))
                .thenReturn(InfoErrors.Fail(TokenOutValue, "error token"));

        // Act
        var result = userAuthenticationService.Login(email, password);

        // Assert
        assertFalse(result.IsSuccess);
        assertFalse(result.Data.getPasswordIsCorrect());
    }

    @Test
    public void error_Map_Obj_To_DTO_Login() {
        String email = "augusto@gmail.com";
        String password = "password1";

        var userGetUserByName = new User();
        userGetUserByName.setId(UUID.fromString("cf0a72ef-9864-4a9b-93aa-2d1a3eaa0654"));

        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.GetUserInfoToLogin(anyString())).thenReturn(new UserDTO());

        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(userGetUserByName);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);

        when(tokenGenerator.generatorTokenUser(any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));

        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(null);

        // Act
        var result = userAuthenticationService.Login(email, password);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error in null class mapping");
    }

    @Test
    public void error_Throw_Exception_GetUserInfoToLogin_Login() {
        String email = "augusto@gmail.com";
        String password = "password1";

        String expectedErrorMessage = "Database connection error";

        when(userRepository.GetUserInfoToLogin(anyString())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userAuthenticationService.Login(email, password);

        // Assert
        assertFalse(result.IsSuccess);
        assertFalse(result.Data.getPasswordIsCorrect());
        assertEquals(result.Data.getMessage(), expectedErrorMessage);
    }

    @Test
    public void should_SendCodeEmail_Successfully() {
        var codeSendEmailUserValidatorDTO = new CodeSendEmailUserValidatorDTO("augusto", "augusto@gmail.com", "242450",
                false, false, false);

        BindingResult bindingResult = new BeanPropertyBindingResult(codeSendEmailUserValidatorDTO, "CodeSendEmailUserValidatorDTO");
        var user = new User();
        user.setId(UUID.fromString("54a7716d-186a-44ca-8115-f717e72e87ff"));

        when(userRepository.GetUserByEmail(anyString())).thenReturn(user);
        when(sendEmailUser.sendCodeRandom(any(), anyInt()))
                .thenReturn(new InfoErrors<String>(true, "all very well", null));

        // Act
        var result = userAuthenticationService.SendCodeEmail(codeSendEmailUserValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertTrue(result.Data.getCodeSendToEmailSuccessfully());
    }

    @Test
    public void error_SendCodeEmail_DTO_Is_Null(){
        var codeSendEmailUserValidatorDTO = new CodeSendEmailUserValidatorDTO("augusto", "augusto@gmail.com", "242450",
                false, false, false);

        BindingResult bindingResult = new BeanPropertyBindingResult(codeSendEmailUserValidatorDTO, "codeSendEmailUserValidatorDTO");

        // Act
        var result = userAuthenticationService.SendCodeEmail(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error DTO Informed is null");
    }
    @Test
    public void error_validate_DTO_create(){
        var codeSendEmailUserValidatorDTO = new CodeSendEmailUserValidatorDTO("augusto", "", "242450",
                false, false, false);

        BindingResult bindingResult = new BeanPropertyBindingResult(codeSendEmailUserValidatorDTO, "CodeSendEmailUserValidatorDTO");

        bindingResult.rejectValue("Email", "NotEmpty", "Email should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("Email", "Email should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userAuthenticationService.SendCodeEmail(codeSendEmailUserValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Throw_Error_GetUserByEmail_Return_Null_SendCodeEmail() {
        var codeSendEmailUserValidatorDTO = new CodeSendEmailUserValidatorDTO("augusto", "augusto@gmail.com", "242450",
                false, false, false);

        BindingResult bindingResult = new BeanPropertyBindingResult(codeSendEmailUserValidatorDTO, "CodeSendEmailUserValidatorDTO");

        when(userRepository.GetUserByEmail(anyString())).thenReturn(null);

        // Act
        var result = userAuthenticationService.SendCodeEmail(codeSendEmailUserValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error userDTO Not found");
    }

    @Test
    public void should_Throw_Error_Code_Does_Not_Send_Email_SendCodeEmail() {
        var codeSendEmailUserValidatorDTO = new CodeSendEmailUserValidatorDTO("augusto", "augusto@gmail.com", "242450",
                false, false, false);

        BindingResult bindingResult = new BeanPropertyBindingResult(codeSendEmailUserValidatorDTO, "CodeSendEmailUserValidatorDTO");
        var user = new User();
        user.setId(UUID.fromString("54a7716d-186a-44ca-8115-f717e72e87ff"));

        when(userRepository.GetUserByEmail(anyString())).thenReturn(user);
        when(sendEmailUser.sendCodeRandom(any(), anyInt()))
                .thenReturn(new InfoErrors<String>(false, "error Send Code Email", null));

        // Act
        var result = userAuthenticationService.SendCodeEmail(codeSendEmailUserValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertFalse(result.Data.getCodeSendToEmailSuccessfully());
    }

    @Test
    public void error_Throw_Exception_GetUserByEmail_SendCodeEmail() {
        var codeSendEmailUserValidatorDTO = new CodeSendEmailUserValidatorDTO("augusto", "augusto@gmail.com", "242450",
                false, false, false);

        BindingResult bindingResult = new BeanPropertyBindingResult(codeSendEmailUserValidatorDTO, "CodeSendEmailUserValidatorDTO");

        String expectedErrorMessage = "Database connection error";

        when(userRepository.GetUserByEmail(anyString())).thenThrow(new RuntimeException(expectedErrorMessage));

        // Act
        var result = userAuthenticationService.SendCodeEmail(codeSendEmailUserValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, expectedErrorMessage);
    }

    @Test
    public void should_VerifyCodeToLogin_Successfully() {
        var userConfirmCodeEmailValidatorDTO = new UserConfirmCodeEmailValidatorDTO(343456, "e7e147af-85ba-46df-b001-bc94ed457dda",
                "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userConfirmCodeEmailValidatorDTO, "UserConfirmCodeEmailValidatorDTO");

        when(dictionaryCode.getKeyDictionary(anyString())).thenReturn(232323);

        // Act
        var result = userAuthenticationService.VerifyCodeToLogin(userConfirmCodeEmailValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertFalse(result.Data.getCodeFoundSuccessfully());
    }

    @Test
    public void error_VerifyCodeToLogin_DTO_Is_Null(){
        var userConfirmCodeEmailValidatorDTO = new UserConfirmCodeEmailValidatorDTO(343456, "e7e147af-85ba-46df-b001-bc94ed457dda",
                "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userConfirmCodeEmailValidatorDTO, "UserConfirmCodeEmailValidatorDTO");

        // Act
        var result = userAuthenticationService.VerifyCodeToLogin(null, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Error DTO Informed is null");
    }
    @Test
    public void error_validate_DTO_VerifyCodeToLogin(){
        var userConfirmCodeEmailValidatorDTO = new UserConfirmCodeEmailValidatorDTO(343456, "e7e147af-85ba-46df-b001-bc94ed457dda",
                "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userConfirmCodeEmailValidatorDTO, "UserConfirmCodeEmailValidatorDTO");

        bindingResult.rejectValue("userId", "NotEmpty", "userId should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("userId", "userId should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(anyList())).thenReturn(errors);

        // Act
        var result = userAuthenticationService.VerifyCodeToLogin(userConfirmCodeEmailValidatorDTO, bindingResult);

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_When_Get_Code_Dictionary_Return_Null_VerifyCodeToLogin() {
        var userConfirmCodeEmailValidatorDTO = new UserConfirmCodeEmailValidatorDTO(343456, "e7e147af-85ba-46df-b001-bc94ed457dda",
                "augusto@gmail.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(userConfirmCodeEmailValidatorDTO, "UserConfirmCodeEmailValidatorDTO");

        when(dictionaryCode.getKeyDictionary(anyString())).thenReturn(null);

        // Act
        var result = userAuthenticationService.VerifyCodeToLogin(userConfirmCodeEmailValidatorDTO, bindingResult);

        // Assert
        assertTrue(result.IsSuccess);
        assertFalse(result.Data.getCodeFoundSuccessfully());
    }
}