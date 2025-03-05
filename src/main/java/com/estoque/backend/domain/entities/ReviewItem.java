package com.estoque.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_review_items", schema = "public")
public class ReviewItem {
    @jakarta.persistence.Id
    @Column(name = "review_items_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "name_user")
    @JsonProperty("nameUser")
    private String NameUser;
    @Column(name = "email")
    @JsonProperty("email")
    private String Email;
    @Column(name = "review_title")
    @JsonProperty("reviewTitle")
    private String ReviewTitle;
    @Column(name = "description")
    @JsonProperty("description")
    private String Description;
    @Column(name = "fit_rating")
    @JsonProperty("fitRating")
    private Short FitRating;
    @Column(name = "quality_rating")
    @JsonProperty("qualityRating")
    private Short QualityRating;
    @Column(name = "price_rating")
    @JsonProperty("priceRating")
    private Short PriceRating;
    @Column(name = "items_id")
    @JsonProperty("itemsId")
    private UUID ItemsId;

    public ReviewItem(UUID id, String nameUser, String email, String reviewTitle, String description, Short fitRating, Short qualityRating, Short priceRating,
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

    public ReviewItem() {
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
