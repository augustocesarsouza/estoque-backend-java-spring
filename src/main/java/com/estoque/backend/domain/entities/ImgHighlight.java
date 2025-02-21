package com.estoque.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_img_highlights", schema = "public")
public class ImgHighlight {
    @jakarta.persistence.Id
    @Column(name = "img_highlights_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "img")
    @JsonProperty("img")
    private String Img;
    @Column(name = "alt")
    @JsonProperty("alt")
    private String Alt;

    public ImgHighlight(UUID id, String img, String alt) {
        Id = id;
        Img = img;
        Alt = alt;
    }

    public ImgHighlight() {
    }

    public UUID getId() {
        return Id;
    }

    public String getImg() {
        return Img;
    }

    public String getAlt() {
        return Alt;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setImg(String img) {
        Img = img;
    }

    public void setAlt(String alt) {
        Alt = alt;
    }

    public void SetValueEntity(String img, String alt) {
        Img = img;
        Alt = alt;
    }
}
