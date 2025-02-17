package com.estoque.backend.application.dto.validations.UserValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdatePasswordValidatorDTO {
    @NotEmpty(message = "actualPassword should not be empty")
    @JsonProperty("actualPassword")
    private String ActualPassword;
    @NotEmpty(message = "newPassword should not be empty")
    @JsonProperty("newPassword")
    private String NewPassword;
    @Size(min = 36, message = "userId should have at last 36 characters")
    @JsonProperty("userId")
    private String UserId;
    @NotEmpty(message = "email should not be empty")
    @JsonProperty("email")
    private String Email;

    public UserUpdatePasswordValidatorDTO(String actualPassword, String newPassword, String userId, String email) {
        ActualPassword = actualPassword;
        NewPassword = newPassword;
        UserId = userId;
        Email = email;
    }

    public String getActualPassword() {
        return ActualPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public String getUserId() {
        return UserId;
    }

    public String getEmail() {
        return Email;
    }

    public void setActualPassword(String actualPassword) {
        ActualPassword = actualPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
