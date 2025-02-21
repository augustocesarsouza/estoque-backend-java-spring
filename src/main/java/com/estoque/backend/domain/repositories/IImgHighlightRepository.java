package com.estoque.backend.domain.repositories;

import com.estoque.backend.application.dto.ImgHighlightDTO;
import com.estoque.backend.domain.entities.ImgHighlight;

import java.util.List;
import java.util.UUID;

public interface IImgHighlightRepository {
    ImgHighlightDTO GetById(UUID imgHighlightId);
    List<ImgHighlightDTO> GetAllImgHighlights();
    ImgHighlightDTO GetImgHighlightToDelete(UUID imgHighlightId);
    ImgHighlight create(ImgHighlight imgHighlight);
    ImgHighlight update(ImgHighlight imgHighlight);
    ImgHighlight delete(UUID imgHighlightId);
}
