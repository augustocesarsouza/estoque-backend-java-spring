package com.estoque.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_categories", schema = "public")
public class Category {
    @jakarta.persistence.Id
    @Column(name = "categories_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "name_category")
    @JsonProperty("nameCategory")
    private String NameCategory;
//    @Column(name = "item_id")
//    @JsonProperty("itemId")
//    private UUID ItemId;

//    @ManyToOne
//    @JoinColumn(name = "items_id", insertable = false, updatable = false)
//    private Item Item;

//    @OneToMany(mappedBy = "category")
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    public Category(UUID id, String nameCategory, List<Item> items) {
        Id = id;
        NameCategory = nameCategory;
//        ItemId = itemId;
        this.items = items;
    }

    public Category() {
    }

    public UUID getId() {
        return Id;
    }

    public String getNameCategory() {
        return NameCategory;
    }

//    public UUID getItemId() {
//        return ItemId;
//    }

    public List<Item> getItems() {
        return items;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setNameCategory(String nameCategory) {
        NameCategory = nameCategory;
    }

//    public void setItemId(UUID itemId) {
//        ItemId = itemId;
//    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
