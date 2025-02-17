package com.estoque.backend.domain.InfoErrors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoErrors<T> {
    @JsonIgnore
    public boolean IsSuccess;
    @JsonProperty("message")
    public String Message;
    @JsonProperty("data")
    public T Data;

    public InfoErrors(boolean isSuccess, String message, T data) {
        IsSuccess = isSuccess;
        Message = message;
        Data = data;
    }

    public static <T> InfoErrors<T> Fail(String message) {
        return new InfoErrors<>(false, message, null);
    }
    public static <T> InfoErrors<T> Fail(T data, String message) {
        return new InfoErrors<>(false, message, data);
    }

    public static <T> InfoErrors<T> Ok(String message) {
        return new InfoErrors<>(true, message, null);
    }
    public static <T> InfoErrors<T> Ok(T data) {
        return new InfoErrors<>(true, null, data);
    }
}
