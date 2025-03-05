package com.estoque.backend.application.dto.validations.ReviewItemValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewItemCreateValidatorDTO {
    @JsonProperty("id")
    private UUID Id;
    @NotEmpty(message = "nameUser should not be empty")
    @Size(min = 2, message = "Must be informed nameUser")
    @JsonProperty("nameUser")
    private String NameUser;
    @NotEmpty(message = "email should not be empty")
    @JsonProperty("email")
    private String Email;
    @NotEmpty(message = "reviewTitle should not be empty")
    @Size(min = 2, message = "Must be informed reviewTitle")
    @JsonProperty("reviewTitle")
    private String ReviewTitle;
    @NotEmpty(message = "description should not be empty")
    @Size(min = 2, message = "Must be informed description")
    @JsonProperty("description")
    private String Description;
    @Min(value = 0, message = "fitRating must be at least 0")
    @JsonProperty("fitRating")
    private Short FitRating;
    @Min(value = 0, message = "qualityRating must be at least 0")
    @JsonProperty("qualityRating")
    private Short QualityRating;
    @Min(value = 0, message = "priceRating must be at least 0")
    @JsonProperty("priceRating")
    private Short PriceRating;
    @NotEmpty(message = "itemsId should not be empty")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "itemsId must be a valid UUID")
    @JsonProperty("itemsId")
    private String ItemsId;

    public ReviewItemCreateValidatorDTO(UUID id, String nameUser, String email, String reviewTitle, String description,
                                        Short fitRating, Short qualityRating, Short priceRating, String itemsId) {
        Id = id;
        NameUser = nameUser;
        Email = email;
        ReviewTitle = reviewTitle;
        Description = description;
        FitRating = fitRating;
        QualityRating = qualityRating;
        PriceRating = priceRating;
        ItemsId = itemsId;
    }

    public ReviewItemCreateValidatorDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public String getNameUser() {
        return NameUser;
    }

    public String getEmail() {
        return Email;
    }

    public String getReviewTitle() {
        return ReviewTitle;
    }

    public String getDescription() {
        return Description;
    }

    public Short getFitRating() {
        return FitRating;
    }

    public Short getQualityRating() {
        return QualityRating;
    }

    public Short getPriceRating() {
        return PriceRating;
    }

    public String getItemsId() {
        return ItemsId;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setReviewTitle(String reviewTitle) {
        ReviewTitle = reviewTitle;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setFitRating(Short fitRating) {
        FitRating = fitRating;
    }

    public void setQualityRating(Short qualityRating) {
        QualityRating = qualityRating;
    }

    public void setPriceRating(Short priceRating) {
        PriceRating = priceRating;
    }

    public void setItemsId(String itemsId) {
        ItemsId = itemsId;
    }
}
