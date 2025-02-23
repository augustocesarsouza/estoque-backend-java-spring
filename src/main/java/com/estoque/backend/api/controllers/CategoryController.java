package com.estoque.backend.api.controllers;

import com.estoque.backend.application.dto.CategoryDTO;
import com.estoque.backend.application.services.ResultService;
import com.estoque.backend.application.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/category/get-item-by-category-id/{categoryId}")
    public ResponseEntity<ResultService<CategoryDTO>> GetItemByCategoryId(@PathVariable String categoryId){
        var result = categoryService.GetItemByCategoryId(UUID.fromString(categoryId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/category/create")
    public ResponseEntity<ResultService<CategoryDTO>> Create(@RequestBody CategoryDTO categoryDTO){
        var result = categoryService.CreateAsync(categoryDTO);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/public/category/delete/{categoryId}")
    public ResponseEntity<ResultService<CategoryDTO>> DeleteAsync(@PathVariable String categoryId){
        var result = categoryService.Delete(UUID.fromString(categoryId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
