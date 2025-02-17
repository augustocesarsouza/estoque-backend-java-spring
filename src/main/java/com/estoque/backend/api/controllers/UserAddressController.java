package com.estoque.backend.api.controllers;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.AddressUpdateDTOValidator;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.AddressUpdateOnlyDefaultDTOValidator;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.UserAddressCreateValidatorDTO;
import com.estoque.backend.application.services.ResultService;
import com.estoque.backend.application.services.interfaces.IUserAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class UserAddressController {
    private final IUserAddressService userAddressService;

    @Autowired
    public UserAddressController(IUserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @GetMapping("/user-address/get-user-by-id/{userAddressId}")
    public ResponseEntity<ResultService<UserAddressDTO>> GetUserById(@PathVariable String userAddressId){
        var result = userAddressService.GetAddressById(UUID.fromString(userAddressId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/user-address/get-user-by-user-id")
    public ResponseEntity<ResultService<List<UserAddressDTO>>> GetUserByUserId(@RequestParam String userId){
        var result = userAddressService.GetAddressByUserId(UUID.fromString(userId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/user-address/create")
    public ResponseEntity<ResultService<UserAddressDTO>> Create(@Valid @RequestBody UserAddressCreateValidatorDTO userAddressCreateValidatorDTO,
                                                                BindingResult resultValid){
        var result = userAddressService.CreateAsync(userAddressCreateValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/user-address/update")
    public ResponseEntity<ResultService<UserAddressDTO>> UpdateAsync(@Valid @RequestBody AddressUpdateDTOValidator updateDTOValidator, BindingResult resultValid){
        var result = userAddressService.UpdateAddressUser(updateDTOValidator, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/user-address/update-only-default-address")
    public ResponseEntity<ResultService<UserAddressDTO>> UpdateAsyncOnlyDefaultAddress(@Valid @RequestBody AddressUpdateOnlyDefaultDTOValidator updateOnlyDefaultDTOValidator,
                                                                                   BindingResult resultValid){
        var result = userAddressService.UpdateAsyncOnlyDefaultAddress(updateOnlyDefaultDTOValidator, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/user-address/delete")
    public ResponseEntity<ResultService<UserAddressDTO>> DeleteAsync(@RequestParam String userAddressId){
        var result = userAddressService.Delete(UUID.fromString(userAddressId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
