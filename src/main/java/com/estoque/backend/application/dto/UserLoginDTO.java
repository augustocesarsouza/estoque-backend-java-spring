package com.estoque.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginDTO {
    @JsonProperty("passwordIsCorrect")
    private Boolean PasswordIsCorrect;
    @JsonProperty("userDTO")
    private UserDTO UserDTO;
    @JsonProperty("message")
    private String Message;

    public UserLoginDTO(Boolean passwordIsCorrect, UserDTO userDTO, String message) {
        PasswordIsCorrect = passwordIsCorrect;
        UserDTO = userDTO;
        Message = message;
    }

    public UserLoginDTO() {
    }

    public Boolean getPasswordIsCorrect() {
        return PasswordIsCorrect;
    }

    public UserDTO getUserDTO() {
        return UserDTO;
    }

    public String getMessage() {
        return Message;
    }
    public void setPasswordIsCorrect(Boolean passwordIsCorrect) {
        PasswordIsCorrect = passwordIsCorrect;
    }
    public void setMessage(String message) {
        Message = message;
    }
    public void setUserDTO(UserDTO userDTO) {
        UserDTO = userDTO;
    }
}
