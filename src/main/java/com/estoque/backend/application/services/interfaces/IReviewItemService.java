package com.estoque.backend.application.services.interfaces;

import com.estoque.backend.application.dto.ReviewItemDTO;
import com.estoque.backend.application.dto.validations.ReviewItemValidationDTOs.ReviewItemCreateValidatorDTO;
import com.estoque.backend.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface IReviewItemService {
    ResultService<ReviewItemDTO> GetReviewItemById(UUID reviewItemId);
    ResultService<List<ReviewItemDTO>> GetAllReviewItemsByItemId(UUID itemId);
    ResultService<ReviewItemDTO> CreateAsync(ReviewItemCreateValidatorDTO reviewItemCreateValidatorDTO, BindingResult result);
    ResultService<ReviewItemDTO> Delete(UUID reviewItemId);
}
