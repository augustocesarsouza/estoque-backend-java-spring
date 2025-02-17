package com.estoque.backend.application.services.interfaces;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.AddressUpdateDTOValidator;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.AddressUpdateOnlyDefaultDTOValidator;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.UserAddressCreateValidatorDTO;
import com.estoque.backend.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface IUserAddressService {
    ResultService<UserAddressDTO> GetAddressById(UUID addressId);
    ResultService<UserAddressDTO> GetAddressByIdWithUserProperty(UUID addressId);
    ResultService<List<UserAddressDTO>> GetAddressByUserId(UUID userId);
    ResultService<UserAddressDTO> CreateAsync(UserAddressCreateValidatorDTO userAddressCreateValidatorDTO, BindingResult result);
    ResultService<UserAddressDTO> CreateToUser(UserAddressCreateValidatorDTO userAddressCreateValidatorDTO);
    ResultService<UserAddressDTO> UpdateAddressUser(AddressUpdateDTOValidator updateDTOValidator, BindingResult result);
    ResultService<UserAddressDTO> UpdateAsyncOnlyDefaultAddress(AddressUpdateOnlyDefaultDTOValidator updateOnlyDefaultDTOValidator, BindingResult result);
    ResultService<UserAddressDTO> Delete(UUID addressId);
}
