package com.estoque.backend.application.services;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.AddressUpdateDTOValidator;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.AddressUpdateOnlyDefaultDTOValidator;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.UserAddressCreateValidatorDTO;
import com.estoque.backend.application.services.interfaces.IUserAddressService;
import com.estoque.backend.application.services.interfaces.IUserManagementService;
import com.estoque.backend.domain.entities.User;
import com.estoque.backend.domain.entities.UserAddress;
import com.estoque.backend.domain.repositories.IUserAddressRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class UserAddressService implements IUserAddressService {
    private final IUserAddressRepository userAddressRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final ModelMapper modelMapper;
    private final IUserManagementService userManagementService;
    @Autowired
    public UserAddressService(IUserAddressRepository userAddressRepository, IValidateErrorsDTO validateErrorsDTO, ModelMapper modelMapper,
                              IUserManagementService userManagementService) {
        this.userAddressRepository = userAddressRepository;
        this.validateErrorsDTO = validateErrorsDTO;
        this.modelMapper = modelMapper;
        this.userManagementService = userManagementService;
    }

    @Override
    @Transactional
    public ResultService<UserAddressDTO> GetAddressById(UUID addressId) {
        try {
            UserAddressDTO addressDTO = userAddressRepository.GetAddressById(addressId);

            if(addressDTO == null){
                return ResultService.Fail("address does not found");
            }

            return ResultService.Ok(addressDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<UserAddressDTO> GetAddressByIdWithUserProperty(UUID addressId) {
        try {
            UserAddressDTO addressDTO = userAddressRepository.GetAddressByIdWithUserProperty(addressId);

            if(addressDTO == null){
                return ResultService.Fail("address does not found");
            }

            return ResultService.Ok(addressDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<List<UserAddressDTO>> GetAddressByUserId(UUID userId) {
        try {
            List<UserAddressDTO> addressDTO = userAddressRepository.GetAddressByUserId(userId);

            if(addressDTO == null){
                return ResultService.Fail("list address does not found");
            }

            return ResultService.Ok(addressDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserAddressDTO> CreateAsync(UserAddressCreateValidatorDTO userAddressCreateValidatorDTO, BindingResult result) {
        if(userAddressCreateValidatorDTO == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            UUID addressId = UUID.randomUUID();
            UUID userId = UUID.fromString(userAddressCreateValidatorDTO.getUserId());

            var userVerify = userManagementService.VerifyWhetherUserExist(userId);

            if(!userVerify.IsSuccess)
                return ResultService.Fail("Error User not exist");

            var verifyIfExistAddressRegistered = userAddressRepository.VerifyIfUserAlreadyHaveAddress(userId);

            UserAddress addressCreate;

            if(verifyIfExistAddressRegistered == null){
                addressCreate = new UserAddress(addressId, userAddressCreateValidatorDTO.getFullName(),
                        userAddressCreateValidatorDTO.getPhoneNumber(), userAddressCreateValidatorDTO.getCep(), userAddressCreateValidatorDTO.getStateCity(),
                        userAddressCreateValidatorDTO.getNeighborhood(), userAddressCreateValidatorDTO.getStreet(), userAddressCreateValidatorDTO.getNumberHome(),
                        userAddressCreateValidatorDTO.getComplement(), (byte) 1, UUID.fromString(userAddressCreateValidatorDTO.getUserId()), null,
                        userAddressCreateValidatorDTO.getSaveAs());
            }else {
                addressCreate = new UserAddress(addressId, userAddressCreateValidatorDTO.getFullName(),
                        userAddressCreateValidatorDTO.getPhoneNumber(), userAddressCreateValidatorDTO.getCep(), userAddressCreateValidatorDTO.getStateCity(),
                        userAddressCreateValidatorDTO.getNeighborhood(), userAddressCreateValidatorDTO.getStreet(), userAddressCreateValidatorDTO.getNumberHome(),
                        userAddressCreateValidatorDTO.getComplement(), (byte) 0, UUID.fromString(userAddressCreateValidatorDTO.getUserId()), null,
                        userAddressCreateValidatorDTO.getSaveAs());
            }

            var createAddress = userAddressRepository.create(addressCreate);

            var userMap = modelMapper.map(createAddress, UserAddressDTO.class);

            return ResultService.Ok(userMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserAddressDTO> CreateToUser(UserAddressCreateValidatorDTO userAddressCreateValidatorDTO) {
        if(userAddressCreateValidatorDTO == null)
            return ResultService.Fail("error DTO Is Null");

        try {
            UUID addressId = UUID.randomUUID();
            UUID userId = UUID.fromString(userAddressCreateValidatorDTO.getUserId());

            var userVerify = userManagementService.VerifyWhetherUserExist(userId);

            if(!userVerify.IsSuccess)
                return ResultService.Fail("Error User does not exist");

            var verifyIfExistAddressRegistered = userAddressRepository.VerifyIfUserAlreadyHaveAddress(userId);

            UserAddress addressCreate;

            if(verifyIfExistAddressRegistered == null){
                addressCreate = new UserAddress(addressId, userAddressCreateValidatorDTO.getFullName(),
                        userAddressCreateValidatorDTO.getPhoneNumber(), userAddressCreateValidatorDTO.getCep(), userAddressCreateValidatorDTO.getStateCity(),
                        userAddressCreateValidatorDTO.getNeighborhood(), userAddressCreateValidatorDTO.getStreet(), userAddressCreateValidatorDTO.getNumberHome(),
                        userAddressCreateValidatorDTO.getComplement(), (byte) 1, UUID.fromString(userAddressCreateValidatorDTO.getUserId()), null,
                        userAddressCreateValidatorDTO.getSaveAs());
            }else {
                addressCreate = new UserAddress(addressId, userAddressCreateValidatorDTO.getFullName(),
                        userAddressCreateValidatorDTO.getPhoneNumber(), userAddressCreateValidatorDTO.getCep(), userAddressCreateValidatorDTO.getStateCity(),
                        userAddressCreateValidatorDTO.getNeighborhood(), userAddressCreateValidatorDTO.getStreet(), userAddressCreateValidatorDTO.getNumberHome(),
                        userAddressCreateValidatorDTO.getComplement(), (byte) 0, UUID.fromString(userAddressCreateValidatorDTO.getUserId()), null,
                        userAddressCreateValidatorDTO.getSaveAs());
            }

            var createAddress = userAddressRepository.create(addressCreate);

            var userMap = modelMapper.map(createAddress, UserAddressDTO.class);

            return ResultService.Ok(userMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserAddressDTO> UpdateAddressUser(AddressUpdateDTOValidator updateDTOValidator, BindingResult result) {
        if(updateDTOValidator == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            var addressDb = userAddressRepository.GetAddressByIdToDelete(UUID.fromString(updateDTOValidator.getId()));

            if(addressDb == null)
                return ResultService.Fail("Error Address does not found");

            var addressToUpdate = new UserAddress(UUID.fromString(updateDTOValidator.getId()), updateDTOValidator.getFullName(),updateDTOValidator.getPhoneNumber(),
                    updateDTOValidator.getCep(), updateDTOValidator.getStateCity(), updateDTOValidator.getNeighborhood(),
                    updateDTOValidator.getStreet(), updateDTOValidator.getNumberHome(), updateDTOValidator.getComplement(),
                    updateDTOValidator.getDefaultAddress(), UUID.fromString(updateDTOValidator.getUserId()), null,
                    updateDTOValidator.getSaveAs());

            var updateAddress = userAddressRepository.update(addressToUpdate);

            var addressMap = modelMapper.map(updateAddress, UserAddressDTO.class);
            addressMap.getUserDTO().setPasswordHash(null);

            return ResultService.Ok(addressMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserAddressDTO> UpdateAsyncOnlyDefaultAddress(AddressUpdateOnlyDefaultDTOValidator updateOnlyDefaultDTOValidator, BindingResult result) {
        if(updateOnlyDefaultDTOValidator == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            var addressDb = userAddressRepository.GetAddressById(UUID.fromString(updateOnlyDefaultDTOValidator.getId()));

            if(addressDb == null)
                return ResultService.Fail("Error Address does not found");

            var addressUpdateDefault = userAddressRepository.GetAddressDefaultAllInfo();
            addressUpdateDefault.setDefaultAddress((byte)0);

//            var updateAddressDefault = addressRepository.updateOnlyDefaultAddress(modelMapper.map(addressUpdateDefault, Address.class));
            int defaultAddress = updateOnlyDefaultDTOValidator.getDefaultAddress();
            addressDb.setDefaultAddress((byte) defaultAddress);

            var addressToUpdate = modelMapper.map(addressDb, UserAddress.class);

            var updateAddress = userAddressRepository.update(addressToUpdate);

            var addressMap = modelMapper.map(updateAddress, UserAddressDTO.class);
            addressMap.getUserDTO().setPasswordHash(null);

            return ResultService.Ok(addressMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserAddressDTO> Delete(UUID addressId) {
        try {
            var addressToDelete = userAddressRepository.GetAddressByIdToDelete(addressId);

            if(addressToDelete == null)
                return ResultService.Fail("Address does not found");

            UserAddress addressDeleteSuccessfully = userAddressRepository.delete(addressId);
            addressDeleteSuccessfully.getUser().setPasswordHash(null);
            var addressMap = modelMapper.map(addressDeleteSuccessfully, UserAddressDTO.class);

            return ResultService.Ok(addressMap);

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
