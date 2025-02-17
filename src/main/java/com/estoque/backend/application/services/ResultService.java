package com.estoque.backend.application.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultService<T> {
    @JsonIgnore
    public boolean IsSuccess;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("message")
    public String Message;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    public List<ErrorValidation> Errors;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data")
    public T Data;
    // ver mapper depos
    public ResultService(boolean isSuccess, String message, List<ErrorValidation> errors, T data){
        this.IsSuccess = isSuccess;
        this.Message = message;
        this.Errors = errors;
        this.Data = data;
    }
    public static <T> ResultService<T> RequestError(String message, List<ErrorValidation> errors){
        return new ResultService<>(false, message, errors, null);
    }
    public static <T> ResultService<T> RequestError(String message, List<ErrorValidation> errors, T data){
        return new ResultService<>(false,message, errors, data);
    }
    public static <T> ResultService<T> Fail(String message) {
        return new ResultService<>(false, message, null, null);
    }
    public static <T> ResultService<T> Fail(T data) {
        return new ResultService<>(false, null, null, data);
    }

    public static <T> ResultService<T> Ok(String message) {
        return new ResultService<>(true, message, null, null);
    }
    public static <T> ResultService<T> Ok(T data) {
        return new ResultService<>(true, null, null, data);
    }
    public static <T> ResultService<T> Ok(String message ,T data) {
        return new ResultService<>(true, message, null, data);
    }
}
