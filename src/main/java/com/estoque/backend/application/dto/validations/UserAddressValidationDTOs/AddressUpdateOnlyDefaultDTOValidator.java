package com.estoque.backend.application.dto.validations.UserAddressValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressUpdateOnlyDefaultDTOValidator {
    @NotEmpty(message = "Id should not be empty")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "Id must be a valid UUID")
    @JsonProperty("id")
    private String Id;

    @Min(value = 0, message = "DefaultAddress must be at least 0")
    @Max(value = 1, message = "DefaultAddress must be at most 1")
    @JsonProperty("defaultAddress")
    private int DefaultAddress;

    public AddressUpdateOnlyDefaultDTOValidator() {
    }

    public AddressUpdateOnlyDefaultDTOValidator(String id, int defaultAddress) {
        Id = id;
        DefaultAddress = defaultAddress;
    }

    public String getId() {
        return Id;
    }

    public int getDefaultAddress() {
        return DefaultAddress;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setDefaultAddress(int defaultAddress) {
        DefaultAddress = defaultAddress;
    }
}
