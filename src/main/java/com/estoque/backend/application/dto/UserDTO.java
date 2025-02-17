package com.estoque.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("name")
    private String Name;
    @JsonProperty("lastName")
    private String LastName;
    @JsonProperty("birthDate")
    private LocalDateTime BirthDate;
    @JsonProperty("gender")
    private String Gender;
    @JsonProperty("cpf")
    private String Cpf;
    @JsonProperty("email")
    private String Email;
    @JsonProperty("landline")
    private String Landline;
    @JsonProperty("cellPhone")
    private String CellPhone;
    @JsonProperty("passwordHash")
    private String PasswordHash;
    @JsonProperty("userImage")
    private String UserImage;
    @JsonProperty("confirmEmail")
    private Short ConfirmEmail; // se 0 false, 1 true
    @JsonProperty("token")
    private String Token;

    public UserDTO(UUID id, String name, String lastName, LocalDateTime birthDate,
                   String gender, String cpf, String email, String landline,
                   String cellPhone, String passwordHash, String userImage, Short confirmEmail, String token) {
        Id = id;
        Name = name;
        LastName = lastName;
        BirthDate = birthDate;
        Gender = gender;
        Cpf = cpf;
        Email = email;
        Landline = landline;
        CellPhone = cellPhone;
        PasswordHash = passwordHash;
        UserImage = userImage;
        ConfirmEmail = confirmEmail;
        Token = token;
    }

    public UserDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getLastName() {
        return LastName;
    }

    public LocalDateTime getBirthDate() {
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

    public String getPasswordHash() {
        return PasswordHash;
    }

    public String getUserImage() {
        return UserImage;
    }

    public Short getConfirmEmail() {
        return ConfirmEmail;
    }

    public String getToken() {
        return Token;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setBirthDate(LocalDateTime birthDate) {
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

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public void setConfirmEmail(Short confirmEmail) {
        ConfirmEmail = confirmEmail;
    }

    public void setToken(String token) {
        Token = token;
    }
}
