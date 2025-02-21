package com.estoque.backend.application.services.interfaces;

import com.estoque.backend.application.dto.ImgHighlightDTO;
import com.estoque.backend.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface IImgHighlightService {
    ResultService<ImgHighlightDTO> GetById(UUID imgHighlightId);
    ResultService<List<ImgHighlightDTO>> GetAllImgHighlights();
    ResultService<ImgHighlightDTO> CreateAsync(ImgHighlightDTO imgHighlightDTO);
    ResultService<ImgHighlightDTO> UpdateImgHighlight(ImgHighlightDTO imgHighlightDTO, BindingResult result);
    ResultService<ImgHighlightDTO> Delete(UUID imgHighlightId);
}
