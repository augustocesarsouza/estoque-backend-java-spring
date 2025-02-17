package com.estoque.backend.data.cloudinaryUtil;

public class CloudinaryCreate {
    private String PublicId;
    private String ImgUrl;
    private Boolean CreatedSuccessfully;

    public CloudinaryCreate(String publicId, String imgUrl, Boolean createdSuccessfully) {
        PublicId = publicId;
        ImgUrl = imgUrl;
        CreatedSuccessfully = createdSuccessfully;
    }

    public CloudinaryCreate() {
    }

    public String getPublicId() {
        return PublicId;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public Boolean getCreatedSuccessfully() {
        return CreatedSuccessfully;
    }
    public void setPublicId(String publicId) {
        PublicId = publicId;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public void setCreatedSuccessfully(Boolean createdSuccessfully) {
        CreatedSuccessfully = createdSuccessfully;
    }
}
