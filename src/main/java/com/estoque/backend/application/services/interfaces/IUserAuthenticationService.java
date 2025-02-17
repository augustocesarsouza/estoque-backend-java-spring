package com.estoque.backend.application.services.interfaces;

import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.UserLoginDTO;
import com.estoque.backend.application.dto.validations.DictionaryCodeDTOs.CodeVerify;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.CodeSendEmailUserValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserConfirmCodeEmailValidatorDTO;
import com.estoque.backend.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.UUID;

public interface IUserAuthenticationService {
    ResultService<UserDTO> GetByIdInfoUser(UUID userId);
//    ResultService<CodeSendPhoneDTOValidator> SendCodePhone(CodeSendPhoneDTOValidator codeSendPhoneDTOValidator, BindingResult result);
//    ResultService<UserDTO> VerifyEmailAlreadySetUp(UserConfirmCodeEmailValidatorDTO userConfirmCodeEmailValidatorDTO, BindingResult result);
    ResultService<UserLoginDTO> Login(String email, String password);
    ResultService<CodeSendEmailUserValidatorDTO> SendCodeEmail(CodeSendEmailUserValidatorDTO codeSendEmailUserValidatorDTO, BindingResult result);
    ResultService<CodeVerify> VerifyCodeToLogin(UserConfirmCodeEmailValidatorDTO userConfirmCodeEmailValidatorDTO, BindingResult result);
    ResultService<UserLoginDTO> VerifyPasswordUser(String phone,String password);
}
