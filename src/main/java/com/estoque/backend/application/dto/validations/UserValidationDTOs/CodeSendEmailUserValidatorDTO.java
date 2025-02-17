package com.estoque.backend.application.dto.validations.UserValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeSendEmailUserValidatorDTO {
    @NotEmpty(message = "Name should not be empty")
    @JsonProperty("name")
    private String Name;
    @NotEmpty(message = "Email should not be empty")
    @JsonProperty("email")
    private String Email;
    @NotEmpty(message = "Code should not be empty")
    @JsonProperty("code")
    private String Code;
    @JsonProperty("codeSendToEmailSuccessfully")
    private Boolean CodeSendToEmailSuccessfully;
    @JsonProperty("userAlreadyExist")
    private Boolean UserAlreadyExist;
    @JsonProperty("emailAlreadyRegistered")
    private Boolean EmailAlreadyRegistered;

    public CodeSendEmailUserValidatorDTO(String name, String email, String code, Boolean codeSendToEmailSuccessfully,
                                         Boolean userAlreadyExist, Boolean emailAlreadyRegistered) {
        Name = name;
        Email = email;
        Code = code;
        CodeSendToEmailSuccessfully = codeSendToEmailSuccessfully;
        UserAlreadyExist = userAlreadyExist;
        EmailAlreadyRegistered = emailAlreadyRegistered;
    }

    public CodeSendEmailUserValidatorDTO() {
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getCode() {
        return Code;
    }

    public Boolean getCodeSendToEmailSuccessfully() {
        return CodeSendToEmailSuccessfully;
    }

    public Boolean getUserAlreadyExist() {
        return UserAlreadyExist;
    }

    public Boolean getEmailAlreadyRegistered() {
        return EmailAlreadyRegistered;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setCodeSendToEmailSuccessfully(Boolean codeSendToEmailSuccessfully) {
        CodeSendToEmailSuccessfully = codeSendToEmailSuccessfully;
    }

    public void setUserAlreadyExist(Boolean userAlreadyExist) {
        UserAlreadyExist = userAlreadyExist;
    }

    public void setEmailAlreadyRegistered(Boolean emailAlreadyRegistered) {
        EmailAlreadyRegistered = emailAlreadyRegistered;
    }
}
