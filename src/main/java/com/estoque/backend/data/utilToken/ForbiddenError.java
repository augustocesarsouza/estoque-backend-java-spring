package com.estoque.backend.data.utilToken;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForbiddenError {
    @JsonProperty("code")
    private String Code;
    @JsonProperty("message")
    private String Message;

    public ForbiddenError(String code, String message) {
        Code = code;
        Message = message;
    }

    public ForbiddenError() {
    }

    public String getCode() {
        return Code;
    }

    public String getMessage() {
        return Message;
    }
}
