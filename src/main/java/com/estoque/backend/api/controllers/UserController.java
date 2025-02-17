package com.estoque.backend.api.controllers;

import com.estoque.backend.application.dto.ChangePasswordResult;
import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.UserLoginDTO;
import com.estoque.backend.application.dto.validations.DictionaryCodeDTOs.CodeVerify;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.*;
import com.estoque.backend.application.services.ResultService;
import com.estoque.backend.application.services.interfaces.IUserAuthenticationService;
import com.estoque.backend.application.services.interfaces.IUserManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class UserController {
    private final IUserManagementService userManagementService;
    private final IUserAuthenticationService userAuthenticationService;

    @Autowired
    public UserController(IUserManagementService userManagementService, IUserAuthenticationService userAuthenticationService) {
        this.userManagementService = userManagementService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping("/public/user/find-by-id/{userId}")
    public ResponseEntity<ResultService<UserDTO>> findById(@PathVariable String userId){
        var result = userManagementService.findById(userId);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/user/find-by-id-to-date-personal/{userId}")
    public ResponseEntity<ResultService<UserDTO>> findByIdToDatePersonal(@PathVariable String userId){
        var result = userManagementService.findByIdToDatePersonal(UUID.fromString(userId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/public/user/login")
    public ResponseEntity<ResultService<UserLoginDTO>> login(@RequestParam String email, @RequestParam String password){
        var result = userAuthenticationService.Login(email, password);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/user/verify-code-to-login")
    public ResponseEntity<ResultService<CodeVerify>> Create(@Valid @RequestBody UserConfirmCodeEmailValidatorDTO userConfirmCodeEmailValidatorDTO,
                                                            BindingResult resultValid){
        var result = userAuthenticationService.VerifyCodeToLogin(userConfirmCodeEmailValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/user/verify-if-user-exist-to-create-login-with-google")
    public ResponseEntity<ResultService<UserDTO>> CreateWithGoogle(@Valid @RequestBody UserCreateLoginGoogleValidatorDTO userCreateLoginGoogleValidatorDTO,
                                                         BindingResult resultValid){

        var result = userManagementService.createToLoginGoogle(userCreateLoginGoogleValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/user/create")
    public ResponseEntity<ResultService<UserDTO>> Create(@Valid @RequestBody UserCreateValidatorDTO userCreateValidatorDTO, BindingResult resultValid){
        var result = userManagementService.create(userCreateValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/user/update-info")
    public ResponseEntity<ResultService<UserDTO>> UpdateInfo(@Valid @RequestBody UserUpdateValidatorDTO userUpdateValidatorDTO,
                                                            BindingResult resultValid){
        var result = userManagementService.UpdateUser(userUpdateValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/user/change-password")
    public ResponseEntity<ResultService<ChangePasswordResult>> UpdateInfo(@Valid @RequestBody UserUpdatePasswordValidatorDTO userUpdatePasswordValidatorDTO,
                                                                          BindingResult resultValid){
        var result = userManagementService.UpdateUserPassword(userUpdatePasswordValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
