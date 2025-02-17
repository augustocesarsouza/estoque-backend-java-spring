package com.estoque.backend.application.dto.validations.DictionaryCodeDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeVerify {
    @JsonProperty("codeFoundSuccessfully")
    private Boolean CodeFoundSuccessfully;

    public CodeVerify(Boolean codeFoundSuccessfully) {
        CodeFoundSuccessfully = codeFoundSuccessfully;
    }

    public Boolean getCodeFoundSuccessfully() {
        return CodeFoundSuccessfully;
    }

    public void setCodeFoundSuccessfully(Boolean codeFoundSuccessfully) {
        this.CodeFoundSuccessfully = codeFoundSuccessfully;
    }
}
