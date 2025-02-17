package com.estoque.backend.data.context;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, UUID> {
    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserDTO(x.Id, x.Name, x.LastName, x.BirthDate, x.Gender, x.Cpf, x.Email, " +
            "x.Landline, x.CellPhone, null, x.UserImage, null, null) " +
            "FROM User AS x " +
            "WHERE x.Id = :userId")
    UserDTO GetUserById(UUID userId);
    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserDTO(x.Id, x.Name, x.LastName, x.BirthDate, x.Gender, x.Cpf, x.Email, " +
            "x.Landline, x.CellPhone, null, x.UserImage, null, null) " +
            "FROM User AS x " +
            "WHERE x.Id = :userId")
    UserDTO findByIdToDatePersonal(UUID userId);

    @Query("SELECT new com.estoque.backend.domain.entities." +
            "User(x.Id, x.Name, null, null, null, null, x.Email, " +
            "null, x.CellPhone, null, null, null) " +
            "FROM User AS x " +
            "WHERE x.Email = :email")
    User GetUserByEmail(String email);

    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserDTO(x.Id, x.Name, null, null, null, null, x.Email, null, " +
            "x.CellPhone, x.PasswordHash, null, null, null) " +
            "FROM User AS x " +
            "WHERE x.Email = :email")
    UserDTO GetUserInfoToLogin(String email);

    @Query("SELECT new com.estoque.backend.domain.entities." +
            "User(x.Id, null, null, null, null, null, x.Email, null, null, x.PasswordHash, null, null) " +
            "FROM User AS x " +
            "WHERE x.Id = :userId")
    User GetUserByIdInfoEmailPasswordHash(UUID userId);

    @Query("SELECT new com.estoque.backend.domain.entities." +
            "User(x.Id, null, null, null, null, null, x.Email, null, null, x.PasswordHash, null, null) " +
            "FROM User AS x " +
            "WHERE x.Email = :email")
    User GetUserByEmailToUserAuthentication(String email);
    @Query("SELECT new com.estoque.backend.domain.entities." +
            "User(x.Id, null, null, null, null, null, null, null, null, null, x.UserImage, null) " +
            "FROM User AS x " +
            "WHERE x.Id = :userId")
    User GetUserByIdForDeleteImg(UUID userId);
}
//UserDTO(UUID id, String name, String lastName, LocalDateTime birthDate,
//        String gender, String cpf, String email, String landline,
//        String cellPhone, String passwordHash, String userImage, Short confirmEmail, String token)

//User(UUID id, String name, String lastName, LocalDateTime birthDate, String gender,
//     String cpf, String email, String landline, String cellPhone,
//     String passwordHash, String userImage, Short confirmEmail)