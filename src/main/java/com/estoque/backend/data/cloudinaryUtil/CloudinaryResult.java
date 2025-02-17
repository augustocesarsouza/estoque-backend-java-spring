package com.estoque.backend.data.cloudinaryUtil;

public class CloudinaryResult {
    private Boolean DeleteSuccessfully;
    private Boolean CreateSuccessfully;
    private String Message;

    public CloudinaryResult(Boolean deleteSuccessfully, Boolean createSuccessfully, String message) {
        DeleteSuccessfully = deleteSuccessfully;
        CreateSuccessfully = createSuccessfully;
        Message = message;
    }

    public CloudinaryResult() {
    }

    public void setDeleteSuccessfully(Boolean deleteSuccessfully) {
        DeleteSuccessfully = deleteSuccessfully;
    }

    public void setCreateSuccessfully(Boolean createSuccessfully) {
        CreateSuccessfully = createSuccessfully;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Boolean getDeleteSuccessfully() {
        return DeleteSuccessfully;
    }

    public Boolean getCreateSuccessfully() {
        return CreateSuccessfully;
    }

    public String getMessage() {
        return Message;
    }
}
