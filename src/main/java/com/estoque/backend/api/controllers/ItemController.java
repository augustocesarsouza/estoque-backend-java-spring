package com.estoque.backend.api.controllers;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.application.dto.validations.ItemValidationDTOs.ItemCreateValidatorDTO;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.UserAddressCreateValidatorDTO;
import com.estoque.backend.application.services.ResultService;
import com.estoque.backend.application.services.interfaces.IItemService;
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
public class ItemController {
    private final IItemService itemService;

    @Autowired
    public ItemController(IItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item/get-all-item-by-name-category")
    public ResponseEntity<ResultService<List<ItemDTO>>> GetItemByNameCategory(@RequestParam String nameCategory){
        var result = itemService.GetItemByNameCategory(nameCategory);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/item/create")
    public ResponseEntity<ResultService<ItemDTO>> Create(@Valid @RequestBody ItemCreateValidatorDTO itemCreateValidatorDTO, BindingResult resultError){
        var result = itemService.CreateAsync(itemCreateValidatorDTO, resultError);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/public/item/delete/{itemId}")
    public ResponseEntity<ResultService<ItemDTO>> DeleteAsync(@PathVariable String itemId){
        var result = itemService.Delete(UUID.fromString(itemId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
