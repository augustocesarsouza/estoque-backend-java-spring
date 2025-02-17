package com.estoque.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAddressDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("fullName")
    private String FullName;
    @JsonProperty("phoneNumber")
    private String PhoneNumber;
    @JsonProperty("cep")
    private String Cep;
    @JsonProperty("stateCity")
    private String StateCity;
    @JsonProperty("neighborhood")
    private String Neighborhood;
    @JsonProperty("street")
    private String Street;
    @JsonProperty("numberHome")
    private String NumberHome;
    @JsonProperty("complement")
    private String Complement;
    @JsonProperty("defaultAddress")
    private Byte DefaultAddress;

    @JsonProperty("userId")
    private UUID UserId;
    @JsonProperty("userDTO")
    private UserDTO UserDTO;
    //    @JsonProperty("addressDTO")
//    private List<AddressDTO> AddressDTO; // TEM QUE VER SE VAI PRECISAR DISSO AQUI
    @JsonProperty("saveAs")
    private Byte SaveAs;

    public UserAddressDTO() {
    }

    public UserAddressDTO(UUID id, String fullName, String phoneNumber, String cep, String stateCity, String neighborhood, String street,
                      String numberHome, String complement, Byte defaultAddress, UUID userId, UserDTO userDTO, Byte saveAs) {
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
        UserDTO = userDTO;
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

    public UserDTO getUserDTO() {
        return UserDTO;
    }

    public Byte getSaveAs() {
        return SaveAs;
    }
    //    public List<AddressDTO> getAddressDTO() {
//        return AddressDTO;
//    }

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

    public void setUserDTO(UserDTO userDTO) {
        UserDTO = userDTO;
    }

    public void setSaveAs(Byte saveAs) {
        SaveAs = saveAs;
    }

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
