package com.estoque.backend.api.controllers;

import com.estoque.backend.application.dto.ImgHighlightDTO;
import com.estoque.backend.application.services.ResultService;
import com.estoque.backend.application.services.interfaces.IImgHighlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class ImgHighlightController {
    private final IImgHighlightService imgHighlightService;

    @Autowired
    public ImgHighlightController(IImgHighlightService imgHighlightService) {
        this.imgHighlightService = imgHighlightService;
    }

    @GetMapping("/img-highlight/get-img-highlight-by-id/{imgHighlightId}")
    public ResponseEntity<ResultService<ImgHighlightDTO>> GetUserById(@PathVariable String imgHighlightId){
        var result = imgHighlightService.GetById(UUID.fromString(imgHighlightId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/img-highlight/get-all-img-highlights")
    public ResponseEntity<ResultService<List<ImgHighlightDTO>>> GetAllImgHighlights(){
        var result = imgHighlightService.GetAllImgHighlights();

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/img-highlight/create")
    public ResponseEntity<ResultService<ImgHighlightDTO>> Create(@RequestBody ImgHighlightDTO imgHighlightDTO){
        var result = imgHighlightService.CreateAsync(imgHighlightDTO);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/img-highlight/delete")
    public ResponseEntity<ResultService<ImgHighlightDTO>> DeleteAsync(@RequestParam String imgHighlightId){
        var result = imgHighlightService.Delete(UUID.fromString(imgHighlightId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
