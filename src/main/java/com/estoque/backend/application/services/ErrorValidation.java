package com.estoque.backend.application.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorValidation {
    @JsonProperty("field")
    private String Field;

    @JsonProperty("message")
    private String Message;

    public ErrorValidation(String field, String message){
        Field = field;
        Message = message;
    }

    public String getField() {
        return Field;
    }

    public String getMessage() {
        return Message;
    }

    public void setField(String field) {
        Field = field;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
