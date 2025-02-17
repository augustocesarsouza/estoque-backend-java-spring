package com.estoque.backend.data.repositories;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.data.context.UserAddressRepositoryJPA;
import com.estoque.backend.domain.entities.UserAddress;
import com.estoque.backend.domain.repositories.IUserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserAddressRepository implements IUserAddressRepository {
    private final UserAddressRepositoryJPA userAddressRepositoryJPA;

    @Autowired
    public UserAddressRepository(UserAddressRepositoryJPA userAddressRepositoryJPA) {
        this.userAddressRepositoryJPA = userAddressRepositoryJPA;
    }

    @Override
    public UserAddressDTO GetAddressById(UUID addressId) {
        return userAddressRepositoryJPA.GetAddressById(addressId);
    }

    @Override
    public UserAddressDTO GetAddressByIdWithUserProperty(UUID addressId) {
        return userAddressRepositoryJPA.GetAddressByIdWithUserProperty(addressId);
    }

    @Override
    public UserAddressDTO GetAddressByIdToDelete(UUID addressId) {
        return userAddressRepositoryJPA.GetAddressByIdToDelete(addressId);
    }

    @Override
    public List<UserAddressDTO> GetAddressByUserId(UUID userId) {
        List<UserAddressDTO> addresses = userAddressRepositoryJPA.GetAddressByUserId(userId);

        List<UserAddressDTO> sortedAddresses = addresses.stream()
                .sorted((a1, a2) -> Integer.compare(a2.getDefaultAddress(), a1.getDefaultAddress()))
                .toList();
        // tem que testar isso por causa do get " .OrderByDescending(x => x.DefaultAddress == 1)" é para ser similar ao c#

        return sortedAddresses;
    }

    @Override
    public UserAddressDTO VerifyIfUserAlreadyHaveAddress(UUID userId) {
        return userAddressRepositoryJPA.VerifyIfUserAlreadyHaveAddress(userId);
    }

    @Override
    public UserAddressDTO GetAddressDefault() {
        return null;
    }

    @Override
    public UserAddressDTO GetAddressDefaultAllInfo() {
        return userAddressRepositoryJPA.GetAddressDefaultAllInfo();
    }

    @Override
    public UserAddress create(UserAddress address) {
        if(address == null)
            return null;

        return userAddressRepositoryJPA.save(address);
    }

    @Override
    public UserAddress update(UserAddress address) {
        if(address == null)
            return null;

        UserAddress addressUpdate = userAddressRepositoryJPA.findById(address.getId()).orElse(null);

        if(addressUpdate == null)
            return null;

        addressUpdate.SetValueAddress(address.getFullName(), address.getPhoneNumber(), address.getCep(), address.getStateCity(),
                address.getNeighborhood(), address.getStreet(), address.getNumberHome(), address.getComplement(), address.getDefaultAddress(),
                address.getUserId(), address.getSaveAs());

        return userAddressRepositoryJPA.save(addressUpdate);
    }

    @Override
    public UserAddress updateOnlyDefaultAddress(UserAddress address) {
        if(address == null)
            return null;

        UserAddress addressUpdate = userAddressRepositoryJPA.findById(address.getId()).orElse(null);

        if(addressUpdate == null)
            return null;

        addressUpdate.setDefaultAddress(address.getDefaultAddress());

        return userAddressRepositoryJPA.save(addressUpdate);
    }

    @Override
    public UserAddress delete(UUID addressId) {
        if(addressId == null)
            return null;

        UserAddress address = userAddressRepositoryJPA.findById(addressId).orElse(null);

        if(address == null)
            return null;

        userAddressRepositoryJPA.delete(address);
//        address.setUser(null);

        return address;
    }
}
