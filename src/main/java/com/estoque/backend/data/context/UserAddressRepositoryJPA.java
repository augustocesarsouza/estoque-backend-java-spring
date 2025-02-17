package com.estoque.backend.data.context;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.domain.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserAddressRepositoryJPA extends JpaRepository<UserAddress, UUID> {
    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserAddressDTO(x.Id, x.FullName, x.PhoneNumber, x.Cep, x.StateCity, x.Neighborhood, x.Street, x.NumberHome, x.Complement, x.DefaultAddress," +
            "x.UserId, null, null) " +
            "FROM UserAddress AS x " +
            "WHERE x.Id = :addressId")
    UserAddressDTO GetAddressById(UUID addressId);
    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserAddressDTO(x.Id, x.FullName, x.PhoneNumber, x.Cep, x.StateCity, x.Neighborhood, x.Street, x.NumberHome, x.Complement, x.DefaultAddress," +
            "x.UserId, new com.estoque.backend.application.dto." +
            "UserDTO(x.User.Id, x.User.Name, null, null, null, null, null, null, null, null, null, null, null), null) " +
            "FROM UserAddress AS x " +
            "WHERE x.Id = :addressId")
    UserAddressDTO GetAddressByIdWithUserProperty(UUID addressId);

//    UserDTO(UUID id, String name, String lastName, LocalDateTime birthDate,
//            String gender, String cpf, String email, String landline,
//            String cellPhone, String passwordHash, String userImage, Short confirmEmail, String token)
    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserAddressDTO(x.Id, x.FullName, null, null, null, null, null, null, null, null," +
            "null, null, null) " +
            "FROM UserAddress AS x " +
            "WHERE x.Id = :addressId")
    UserAddressDTO GetAddressByIdToDelete(UUID addressId);

    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserAddressDTO(x.Id, x.FullName, x.PhoneNumber, x.Cep, x.StateCity, x.Neighborhood, x.Street, x.NumberHome, x.Complement, x.DefaultAddress," +
            "x.UserId, null, x.SaveAs) " +
            "FROM UserAddress AS x " +
            "WHERE x.UserId = :userId")
    List<UserAddressDTO> GetAddressByUserId(UUID userId);

    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserAddressDTO(x.Id, x.FullName, null, null, null, null, null, null, null, x.DefaultAddress," +
            "null, null, null) " +
            "FROM UserAddress AS x " +
            "WHERE x.UserId = :userId AND x.DefaultAddress = 1")
    UserAddressDTO VerifyIfUserAlreadyHaveAddress(UUID userId);

    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserAddressDTO(x.Id, x.FullName, null, null, null, null, null, null, null, x.DefaultAddress," +
            "null, null, null) " +
            "FROM UserAddress AS x " +
            "WHERE x.DefaultAddress = 1")
    UserAddressDTO GetAddressDefault();

    @Query("SELECT new com.estoque.backend.application.dto." +
            "UserAddressDTO(x.Id, x.FullName, null, null, null, null, null, null, null, x.DefaultAddress," +
            "null, null, null) " +
            "FROM UserAddress AS x " +
            "WHERE x.DefaultAddress = 1")
    UserAddressDTO GetAddressDefaultAllInfo();
}
//UserAddressDTO(UUID id, String fullName, String phoneNumber, String cep, String stateCity, String neighborhood, String street,
//               String numberHome, String complement, Byte defaultAddress, UUID userId, UserDTO userDTO, Byte saveAs)

//    UserDTO(UUID id, String name, String lastName, LocalDateTime birthDate,
//            String gender, String cpf, String email, String landline,
//            String cellPhone, String passwordHash, String userImage, Short confirmEmail, String token)