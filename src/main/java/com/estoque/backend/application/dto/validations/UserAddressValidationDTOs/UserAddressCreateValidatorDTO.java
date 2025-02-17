package com.estoque.backend.application.dto.validations.UserAddressValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAddressCreateValidatorDTO {
    @JsonProperty("id")
    private String Id;
    @NotEmpty(message = "FullName should not be empty")
    @Size(min = 2, message = "Must be informed FullName")
    @JsonProperty("fullName")
    private String FullName;
    @NotEmpty(message = "PhoneNumber should not be empty")
    @Pattern(regexp = "\\(\\+\\d{2}\\) \\d{2} \\d{5} \\d{4}",
            message = "PhoneNumber must follow the format (+99) 99 99999 9999")
    @Size(min = 19, max = 19, message = "PhoneNumber must have 19 characters, including spaces and parentheses")
    @JsonProperty("phoneNumber")
    private String PhoneNumber;
    @NotEmpty(message = "CEP should not be empty")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP must follow the format 99999-999")
    @Size(min = 9, max = 9, message = "CEP must have exactly 9 characters")
    @JsonProperty("cep")
    private String Cep;
    @NotEmpty(message = "StateCity should not be empty")
    @Size(min = 2, message = "StateCity must have at least 2 characters")
    @JsonProperty("stateCity")
    private String StateCity;
    @NotEmpty(message = "Neighborhood should not be empty")
    @Size(min = 2, message = "Neighborhood must have at least 2 characters")
    @JsonProperty("neighborhood")
    private String Neighborhood;
    @NotEmpty(message = "Street should not be empty")
    @Size(min = 2, message = "Street must have at least 2 characters")
    @JsonProperty("street")
    private String Street;
    @NotEmpty(message = "NumberHome should not be empty")
    @Size(min = 1, message = "NumberHome must have at least 1 character")
    @JsonProperty("numberHome")
    private String NumberHome;
    @JsonProperty("complement")
    private String Complement;
    @NotEmpty(message = "UserId should not be empty")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "UserId must be a valid UUID")
    @JsonProperty("userId")
    private String UserId;
    @JsonProperty("saveAs")
    private Byte SaveAs;

    public UserAddressCreateValidatorDTO(String fullName, String phoneNumber, String cep, String stateCity, String neighborhood, String street, String numberHome,
                                     Byte saveAs) {
        FullName = fullName;
        PhoneNumber = phoneNumber;
        Cep = cep;
        StateCity = stateCity;
        Neighborhood = neighborhood;
        Street = street;
        NumberHome = numberHome;
        SaveAs = saveAs;
    }

    public UserAddressCreateValidatorDTO() {
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

    public String getUserId() {
        return UserId;
    }

    public String getId() {
        return Id;
    }

    public Byte getSaveAs() {
        return SaveAs;
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

    public void setId(String id) {
        Id = id;
    }

    public void setComplement(String complement) {
        Complement = complement;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setSaveAs(Byte saveAs) {
        SaveAs = saveAs;
    }
}
