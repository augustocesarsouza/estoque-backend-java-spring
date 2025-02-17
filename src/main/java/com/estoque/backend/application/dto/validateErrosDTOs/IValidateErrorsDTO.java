package com.estoque.backend.application.dto.validateErrosDTOs;

import com.estoque.backend.application.services.ErrorValidation;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface IValidateErrorsDTO {
    List<ErrorValidation> ValidateDTO(List<ObjectError> errorsDTO);
}
