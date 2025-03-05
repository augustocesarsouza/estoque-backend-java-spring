package com.estoque.backend.api.controllers;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.application.dto.ReviewItemDTO;
import com.estoque.backend.application.dto.validations.ItemValidationDTOs.ItemCreateValidatorDTO;
import com.estoque.backend.application.dto.validations.ReviewItemValidationDTOs.ReviewItemCreateValidatorDTO;
import com.estoque.backend.application.services.ResultService;
import com.estoque.backend.application.services.interfaces.IReviewItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class ReviewItemController {
    private final IReviewItemService reviewItemService;

    @Autowired
    public ReviewItemController(IReviewItemService reviewItemService) {
        this.reviewItemService = reviewItemService;
    }

    @GetMapping("/public/review-item/get-review-item-by-id")
    public ResponseEntity<ResultService<ReviewItemDTO>> GetReviewItemById(@RequestParam String reviewItemId){
        var result = reviewItemService.GetReviewItemById(UUID.fromString(reviewItemId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/public/review-item/get-all-review-items-by-item-id")
    public ResponseEntity<ResultService<List<ReviewItemDTO>>> GetAllReviewItemsByItemId(@RequestParam String itemId){
        var result = reviewItemService.GetAllReviewItemsByItemId(UUID.fromString(itemId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/review-item/create")
    public ResponseEntity<ResultService<ReviewItemDTO>> Create(@Valid @RequestBody ReviewItemCreateValidatorDTO reviewItemCreateValidatorDTO, BindingResult resultError){
        var result = reviewItemService.CreateAsync(reviewItemCreateValidatorDTO, resultError);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/public/review-item/delete/{reviewItemId}")
    public ResponseEntity<ResultService<ReviewItemDTO>> DeleteAsync(@PathVariable String reviewItemId){
        var result = reviewItemService.Delete(UUID.fromString(reviewItemId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
