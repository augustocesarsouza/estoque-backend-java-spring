package com.estoque.backend.application.dto.validations.ItemValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemCreateValidatorDTO {
    @NotEmpty(message = "name should not be empty")
    @jakarta.validation.constraints.Size(min = 2, message = "Must be informed name")
    @JsonProperty("name")
    private String Name;
    @Min(value = 0, message = "priceProduct must be at least 0")
    @JsonProperty("priceProduct")
    private Double PriceProduct;
    @Min(value = 0, message = "discountPercentage must be at least 0")
    @JsonProperty("discountPercentage")
    private Integer DiscountPercentage;
    @NotEmpty(message = "size should not be empty")
    @JsonProperty("size")
    private String Size;
    @NotEmpty(message = "size should not be empty")
    @JsonProperty("brand")
    private String Brand;
    @NotEmpty(message = "description should not be empty")
    @JsonProperty("description")
    private String Description;
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "categoryId must be a valid UUID")
    @JsonProperty("categoryId")
    private String categoryId;
    @NotEmpty(message = "imgProductAll must have at least one image")
    @JsonProperty("imgProductAll")
    private List<String> ImgProductAll;

    public ItemCreateValidatorDTO(String name, Double priceProduct, Integer discountPercentage, String size, String brand, String description,
                                  String categoryId, List<String> imgProductAll) {
        Name = name;
        PriceProduct = priceProduct;
        DiscountPercentage = discountPercentage;
        Size = size;
        Brand = brand;
        Description = description;
        this.categoryId = categoryId;
        ImgProductAll = imgProductAll;
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

    public String getCategoryId() {
        return categoryId;
    }

    public List<String> getImgProductAll() {
        return ImgProductAll;
    }

    public String getDescription() {
        return Description;
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

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setImgProductAll(List<String> imgProductAll) {
        ImgProductAll = imgProductAll;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
