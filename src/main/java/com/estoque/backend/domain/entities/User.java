package com.estoque.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_users", schema = "public")
public class User implements UserDetails {
    @jakarta.persistence.Id
    @Column(name = "user_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "name")
    @JsonProperty("name")
    private String Name;
    @Column(name = "last_name")
    @JsonProperty("lastName")
    private String LastName;
    @Column(name = "birth_date")
    @JsonProperty("birthDate")
    private LocalDateTime BirthDate;
    @Column(name = "gender")
    @JsonProperty("gender")
    private String Gender;
    @Column(name = "cpf")
    @JsonProperty("cpf")
    private String Cpf;
    @Column(name = "email")
    @JsonProperty("email")
    private String Email;
    @Column(name = "landline")
    @JsonProperty("landline")
    private String Landline;
    @Column(name = "cell_phone")
    @JsonProperty("cellPhone")
    private String CellPhone;
    @Column(name = "password_hash")
    @JsonProperty("passwordHash")
    private String PasswordHash;
    @Column(name = "user_image")
    @JsonProperty("userImage")
    private String UserImage;
    @Column(name = "confirm_email")
    @JsonProperty("confirmEmail")
    private Short ConfirmEmail; // se 0 false, 1 true

    public User(UUID id, String name, String lastName, LocalDateTime birthDate, String gender,
                String cpf, String email, String landline, String cellPhone,
                String passwordHash, String userImage, Short confirmEmail) {
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
    }

    public User() {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return PasswordHash;
    }

    @Override
    public String getUsername() {
        return Email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void SetValueUserAddress(String name, String lastName, LocalDateTime birthDate, String gender,
                                    String cpf, String email, String landline, String cellPhone, String userImage){
        Name = name;
        LastName = lastName;
        BirthDate = birthDate;
        Gender = gender;
        Cpf = cpf;
        Email = email;
        Landline = landline;
        CellPhone = cellPhone;
        UserImage = userImage;
    }
}
