package com.estoque.backend.application.dto;

import com.estoque.backend.domain.entities.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("name")
    private String Name;
    @JsonProperty("priceProduct")
    private Double PriceProduct;
    @JsonProperty("discountPercentage")
    private Integer DiscountPercentage;
    @JsonProperty("size")
    private String Size;
    @JsonProperty("brand")
    private String Brand;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("categoryId")
    private String categoryId;
    @JsonProperty("imgProductAll")
    private List<String> ImgProductAll;

    public ItemDTO(UUID id, String name, Double priceProduct, Integer discountPercentage, String size, String brand,
                   Category category, String categoryId, List<String> imgProductAll) {
        Id = id;
        Name = name;
        PriceProduct = priceProduct;
        DiscountPercentage = discountPercentage;
        Size = size;
        Brand = brand;
        this.category = category;
        this.categoryId = categoryId;
        ImgProductAll = imgProductAll;
    }

    public ItemDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public Double getPriceProduct() {
        return PriceProduct;
    }

    public Integer getDiscountPercentage() {
        return DiscountPercentage;
    }

    public String getSize() {
        return Size;
    }

    public String getBrand() {
        return Brand;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public List<String> getImgProductAll() {
        return ImgProductAll;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPriceProduct(Double priceProduct) {
        PriceProduct = priceProduct;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public void setSize(String size) {
        Size = size;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setImgProductAll(List<String> imgProductAll) {
        ImgProductAll = imgProductAll;
    }
}
