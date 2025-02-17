package com.estoque.backend.application.dto.validations.UserValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserConfirmCodeEmailValidatorDTO {
    @Min(value = 1, message = "Code must be greater than 0")
    @JsonProperty("code")
    private Integer Code;
    @NotEmpty(message = "idGuid should not be empty")
    @Size(min = 36, message = "idGuid should have at last 36 characters")
    @JsonProperty("userId")
    private String UserId;
    @JsonProperty("email")
    private String Email;

    public UserConfirmCodeEmailValidatorDTO(Integer code, String userId, String email) {
        Code = code;
        UserId = userId;
        Email = email;
    }

    public UserConfirmCodeEmailValidatorDTO() {
    }

    public Integer getCode() {
        return Code;
    }

    public String getUserId() {
        return UserId;
    }

    public String getEmail() {
        return Email;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setEmail(String email) {
        Email = email;
    }
}