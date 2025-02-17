package com.estoque.backend.data.repositories;

import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserUpdateValidatorDTO;
import com.estoque.backend.data.context.UserRepositoryJPA;
import com.estoque.backend.domain.entities.User;
import com.estoque.backend.domain.entities.UserAddress;
import com.estoque.backend.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserRepository implements IUserRepository {
    private final UserRepositoryJPA userRepositoryJPA;

    @Autowired
    public UserRepository(UserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public User GetUserById(UUID userId) {
        return null;
    }

    @Override
    public UserDTO findByIdToDatePersonal(UUID userId) {
        return userRepositoryJPA.findByIdToDatePersonal(userId);
    }

    @Override
    public User GetUserByEmail(String email) {
        return userRepositoryJPA.GetUserByEmail(email);
    }

    @Override
    public User GetUserByIdInfoEmailPasswordHash(UUID guidId) {
        return userRepositoryJPA.GetUserByIdInfoEmailPasswordHash(guidId);
    }

    @Override
    public UserDTO GetUserInfoToLogin(String email) {
        return userRepositoryJPA.GetUserInfoToLogin(email);
    }
    @Override
    public User GetUserByIdForDeleteImg(UUID userId) {
        return userRepositoryJPA.GetUserByIdForDeleteImg(userId);
    }
    @Override
    public User create(User user) {
        if(user == null)
            return null;

        return userRepositoryJPA.save(user);
    }

    @Override
    public User GetUserByEmailToUserAuthentication(String email) {
        return userRepositoryJPA.GetUserByEmailToUserAuthentication(email);
    }

    @Override
    public User update(UserUpdateValidatorDTO userUpdateValidatorDTO) {
        if(userUpdateValidatorDTO == null)
            return null;

        var id = UUID.fromString(userUpdateValidatorDTO.getUserId());

        User userUpdate = userRepositoryJPA.findById(id).orElse(null);

        if(userUpdate == null)
            return null;

        userUpdate.SetValueUserAddress(userUpdateValidatorDTO.getName(), userUpdateValidatorDTO.getLastName(), userUpdateValidatorDTO.getBirthDateLocalDateTime(),
                userUpdateValidatorDTO.getGender(), userUpdateValidatorDTO.getCpf(), userUpdateValidatorDTO.getEmail(),
                userUpdateValidatorDTO.getLandline(), userUpdateValidatorDTO.getCellPhone(),userUpdateValidatorDTO.getUserImage());

        return userRepositoryJPA.save(userUpdate);
    }

    @Override
    public User updatePassword(User user) {
        if(user == null)
            return null;

        var id = user.getId();

        User userUpdate = userRepositoryJPA.findById(id).orElse(null);

        if(userUpdate == null)
            return null;

        userUpdate.setPasswordHash(user.getPasswordHash());
//        $2a$10$xLyzS22epz.oGbgnk8rKF.NKH7.vVv07IGEusmtYuZvKhtclpt1hy
//        return new User();
        return userRepositoryJPA.save(userUpdate);
    }
//    SetValueUserAddress(String name, String lastName, LocalDateTime birthDate, String gender,
//                        String cpf, String email, String landline, String cellPhone, String userImage)
    @Override
    public User delete(UUID userId) {
        return null;
    }
}
