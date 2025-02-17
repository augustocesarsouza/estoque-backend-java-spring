package com.estoque.backend.application.dto.validations.UserValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateLoginGoogleValidatorDTO {
    @NotEmpty(message = "name should not be empty")
    @JsonProperty("name")
    private String Name;
    @NotEmpty(message = "email should not be empty")
    @JsonProperty("email")
    private String Email;
    @JsonProperty("last_name")
    private String LastName;

    public UserCreateLoginGoogleValidatorDTO(String name, String email, String lastName) {
        Name = name;
        Email = email;
        LastName = lastName;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
