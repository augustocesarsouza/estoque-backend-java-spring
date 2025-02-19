package com.estoque.backend.domain.repositories;

import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserUpdateValidatorDTO;
import com.estoque.backend.domain.entities.User;

import java.util.UUID;

public interface IUserRepository {
    User GetUserById(UUID userId);
    UserDTO findByIdToDatePersonal(UUID userId);
    User GetUserByEmail(String email);
    User GetUserByIdInfoEmailPasswordHash(UUID guidId);
    User GetUserByPhoneInfoUpdate(String phone);
    UserDTO GetUserInfoToLogin(String email);
    User GetUserByIdForDeleteImg(UUID userId);
    User GetUserByEmailToUserAuthentication(String email);
    User create(User user);
    User update(UserUpdateValidatorDTO userUpdateValidatorDTO);
    User updatePassword(User user);
    User delete(UUID userId);
}
