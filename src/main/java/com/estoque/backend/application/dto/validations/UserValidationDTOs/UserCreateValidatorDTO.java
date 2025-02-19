package com.estoque.backend.application.dto.validations.UserValidationDTOs;

import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.UserAddressCreateValidatorDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateValidatorDTO {
    @NotEmpty(message = "name should not be empty")
    @JsonProperty("name")
    private String Name;
    @NotEmpty(message = "lastName should not be empty")
    @JsonProperty("lastName")
    private String LastName;
    @NotEmpty(message = "birthDate should not be empty")
    @Pattern(
            regexp = "^\\d{2}/\\d{2}/\\d{4}$",
            message = "birthDate must follow the format dd/MM/yyyy"
    )
    @JsonProperty("birthDate")
    private String BirthDate;
    @NotEmpty(message = "gender should not be empty")
    @JsonProperty("gender")
    private String Gender;
    @NotEmpty(message = "cpf should not be empty")
    @Size(min = 11, message = "cpf must be greater than 11")
    @JsonProperty("cpf")
    private String Cpf;
    @NotEmpty(message = "email should not be empty")
    @JsonProperty("email")
    private String Email;
    @JsonProperty("landline")
    private String Landline;
    @NotEmpty(message = "cellPhone should not be empty")
    @JsonProperty("cellPhone")
    private String CellPhone;
    @JsonProperty("userImage")
    private String UserImage;
    @NotEmpty(message = "password should not be empty")
    @Size(min = 8, message = "password should have at last 8 characters")
    @JsonProperty("password")
    private String Password;

    public UserCreateValidatorDTO(String name, String lastName, String birthDate, String gender,
                                  String cpf, String email, String landline, String cellPhone, String userImage,
                                  String password) {
        Name = name;
        LastName = lastName;
        BirthDate = birthDate;
        Gender = gender;
        Cpf = cpf;
        Email = email;
        Landline = landline;
        CellPhone = cellPhone;
        UserImage = userImage;
        Password = password;
    }

    public UserCreateValidatorDTO() {
    }

    public String getName() {
        return Name;
    }

    public String getLastName() {
        return LastName;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public String getGender() {
        return Gender;
    }

    public String getCpf() {
        return Cpf;
    }

    public String getEmail() {
        return Email;
    }

    public String getLandline() {
        return Landline;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public String getUserImage() {
        return UserImage;
    }

    public String getPassword() {
        return Password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setCpf(String cpf) {
        Cpf = cpf;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setLandline(String landline) {
        Landline = landline;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
