package com.estoque.backend.domain.repositories;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.domain.entities.UserAddress;

import java.util.List;
import java.util.UUID;

public interface IUserAddressRepository {
    UserAddressDTO GetAddressById(UUID addressId);
    UserAddressDTO GetAddressByIdWithUserProperty(UUID addressId);
    UserAddressDTO GetAddressByIdToDelete(UUID addressId);
    List<UserAddressDTO> GetAddressByUserId(UUID userId);
    UserAddressDTO VerifyIfUserAlreadyHaveAddress(UUID userId);
    UserAddressDTO GetAddressDefault();
    UserAddressDTO GetAddressDefaultAllInfo();
    UserAddress create(UserAddress address);
    UserAddress update(UserAddress address);
    UserAddress updateOnlyDefaultAddress(UserAddress address);
    UserAddress delete(UUID addressId);
}
