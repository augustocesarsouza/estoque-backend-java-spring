package com.estoque.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImgHighlightDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("base64Img")
    private String Base64Img;
    @JsonProperty("img")
    private String Img;
    @JsonProperty("alt")
    private String Alt;

    public ImgHighlightDTO(UUID id, String img, String alt) {
        Id = id;
        Img = img;
        Alt = alt;
    }

    public ImgHighlightDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public String getBase64Img() {
        return Base64Img;
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

    public void setBase64Img(String base64Img) {
        Base64Img = base64Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public void setAlt(String alt) {
        Alt = alt;
    }
}
