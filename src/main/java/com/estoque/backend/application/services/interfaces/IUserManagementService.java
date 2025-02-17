package com.estoque.backend.application.services.interfaces;

import com.estoque.backend.application.dto.ChangePasswordResult;
import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserCreateLoginGoogleValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserCreateValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserUpdatePasswordValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserUpdateValidatorDTO;
import com.estoque.backend.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.UUID;

public interface IUserManagementService {
    ResultService<UserDTO> findById(String phone);
    ResultService<UserDTO> findByIdToDatePersonal(UUID userId);
    ResultService<UserDTO> VerifyWhetherUserExist(UUID userId);
    ResultService<UserDTO> create(UserCreateValidatorDTO userCreateValidatorDTO, BindingResult result);
    ResultService<UserDTO> createToLoginGoogle(UserCreateLoginGoogleValidatorDTO userCreateLoginGoogleValidatorDTO,
                                               BindingResult result);
    ResultService<UserDTO> UpdateUser(UserUpdateValidatorDTO userUpdateValidatorDTO, BindingResult result);
    ResultService<ChangePasswordResult> UpdateUserPassword(UserUpdatePasswordValidatorDTO userUpdatePasswordValidatorDTO, BindingResult result);
    ResultService<UserDTO> Delete(UUID userID);
}
