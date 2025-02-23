package com.estoque.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("nameCategory")
    private String NameCategory;
    @JsonProperty("itemsDTOs")
    private List<ItemDTO> itemsDTOs;

    public CategoryDTO(UUID id, String nameCategory, List<ItemDTO> itemsDTOs) {
        Id = id;
        NameCategory = nameCategory;
        this.itemsDTOs = itemsDTOs;
    }

    public CategoryDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public String getNameCategory() {
        return NameCategory;
    }

    public List<ItemDTO> getItemsDTOs() {
        return itemsDTOs;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setNameCategory(String nameCategory) {
        NameCategory = nameCategory;
    }

    public void setItemsDTOs(List<ItemDTO> itemsDTOs) {
        this.itemsDTOs = itemsDTOs;
    }
}
