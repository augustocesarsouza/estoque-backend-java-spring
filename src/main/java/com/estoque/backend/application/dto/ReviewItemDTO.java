package com.estoque.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewItemDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("nameUser")
    private String NameUser;
    @JsonProperty("email")
    private String Email;
    @JsonProperty("reviewTitle")
    private String ReviewTitle;
    @JsonProperty("description")
    private String Description;
    @JsonProperty("fitRating")
    private Short FitRating;
    @JsonProperty("qualityRating")
    private Short QualityRating;
    @JsonProperty("priceRating")
    private Short PriceRating;
    @JsonProperty("itemsId")
    private UUID ItemsId;

    public ReviewItemDTO(UUID id, String nameUser, String email, String reviewTitle, String description, Short fitRating, Short qualityRating, Short priceRating,
                         UUID itemsId) {
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

    public ReviewItemDTO() {
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

    public UUID getItemsId() {
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

    public void setItemsId(UUID itemsId) {
        ItemsId = itemsId;
    }
}
