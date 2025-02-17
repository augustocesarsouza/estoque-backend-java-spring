package com.estoque.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_user_address", schema = "public")
public class UserAddress {
    @jakarta.persistence.Id
    @Column(name = "user_address_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "full_name")
    @JsonProperty("fullName")
    private String FullName;
    @Column(name = "phone_number")
    @JsonProperty("phoneNumber")
    private String PhoneNumber;
    @Column(name = "cep")
    @JsonProperty("cep")
    private String Cep;
    @Column(name = "state_city")
    @JsonProperty("stateCity")
    private String StateCity;
    @Column(name = "neighborhood")
    @JsonProperty("neighborhood")
    private String Neighborhood;
    @Column(name = "street")
    @JsonProperty("street")
    private String Street;
    @Column(name = "number_home")
    @JsonProperty("numberHome")
    private String NumberHome;
    @Column(name = "complement")
    @JsonProperty("complement")
    private String Complement;
    @Column(name = "default_address")
    @JsonProperty("defaultAddress")
    private Byte DefaultAddress;
    @Column(name = "user_id")
    @JsonProperty("userId")
    private UUID UserId;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User User;
    @Column(name = "save_as")
    @JsonProperty("saveAs")
    private Byte SaveAs;

    public UserAddress() {
    }

    public UserAddress(UUID id, String fullName, String phoneNumber, String cep, String stateCity, String neighborhood,
                   String street, String numberHome, String complement, Byte defaultAddress, UUID userId, User user, Byte saveAs) {
        Id = id;
        FullName = fullName;
        PhoneNumber = phoneNumber;
        Cep = cep;
        StateCity = stateCity;
        Neighborhood = neighborhood;
        Street = street;
        NumberHome = numberHome;
        Complement = complement;
        DefaultAddress = defaultAddress;
        UserId = userId;
        User = user;
        SaveAs = saveAs;
    }

    public UUID getId() {
        return Id;
    }

    public String getFullName() {
        return FullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getCep() {
        return Cep;
    }

    public String getStateCity() {
        return StateCity;
    }

    public String getNeighborhood() {
        return Neighborhood;
    }

    public String getStreet() {
        return Street;
    }

    public String getNumberHome() {
        return NumberHome;
    }

    public String getComplement() {
        return Complement;
    }

    public Byte getDefaultAddress() {
        return DefaultAddress;
    }

    public UUID getUserId() {
        return UserId;
    }

    public User getUser() {
        return User;
    }

    public Byte getSaveAs() {
        return SaveAs;
    }
    public void setId(UUID id) {
        Id = id;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public void setStateCity(String stateCity) {
        StateCity = stateCity;
    }

    public void setNeighborhood(String neighborhood) {
        Neighborhood = neighborhood;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public void setNumberHome(String numberHome) {
        NumberHome = numberHome;
    }

    public void setComplement(String complement) {
        Complement = complement;
    }

    public void setDefaultAddress(Byte defaultAddress) {
        DefaultAddress = defaultAddress;
    }

    public void setUserId(UUID userId) {
        UserId = userId;
    }

    public void setUser(User user) {
        User = user;
    }

    public void setSaveAs(Byte saveAs) {
        SaveAs = saveAs;
    }
    //    public void setAddresses(List<Address> addresses) {
//        Addresses = addresses;
//    }

    public void SetValueAddress(String fullName, String phoneNumber, String cep, String stateCity, String neighborhood,
                                String street, String numberHome, String complement, Byte defaultAddress, UUID userId, Byte saveAs) {
        FullName = fullName;
        PhoneNumber = phoneNumber;
        Cep = cep;
        StateCity = stateCity;
        Neighborhood = neighborhood;
        Street = street;
        NumberHome = numberHome;
        Complement = complement;
        DefaultAddress = defaultAddress;
        UserId = userId;
        SaveAs = saveAs;
    }
}
