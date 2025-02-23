package com.estoque.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_items", schema = "public")
public class Item {
    @jakarta.persistence.Id
    @Column(name = "items_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "name")
    @JsonProperty("name")
    private String Name;
    @Column(name = "price_product")
    @JsonProperty("priceProduct")
    private Double PriceProduct;
    @Column(name = "discount_percentage")
    @JsonProperty("discountPercentage")
    private Integer DiscountPercentage;
    @Column(name = "size_item")
    @JsonProperty("size")
    private String Size;
    @Column(name = "brand")
    @JsonProperty("brand")
    private String Brand;
    @ManyToOne
//    @JoinColumn(name = "categories_id", insertable = false, updatable = false)
    @JoinColumn(name = "categories_id", nullable = false)
    private Category category;
    @Column(name = "img_product_all")
    @JsonProperty("imgProductAll")
    private List<String> ImgProductAll;

//    @Query("SELECT i FROM Item i WHERE i.Category.nameCategory = :nameCategory")
//    List<Item> findItemsByCategoryName(@Param("nameCategory") String nameCategory);

    public Item(UUID id, String name, Double priceProduct, Integer discountPercentage, String size, String brand, List<String> imgProductAll) {
        Id = id;
        Name = name;
        PriceProduct = priceProduct;
        DiscountPercentage = discountPercentage;
        Size = size;
        Brand = brand;
        ImgProductAll = imgProductAll;
    }

    public Item() {
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

    public void setImgProductAll(List<String> imgProductAll) {
        ImgProductAll = imgProductAll;
    }
}