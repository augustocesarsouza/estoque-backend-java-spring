package com.estoque.backend.data.context;

import com.estoque.backend.application.dto.ImgHighlightDTO;
import com.estoque.backend.domain.entities.ImgHighlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImgHighlightRepositoryJPA extends JpaRepository<ImgHighlight, UUID> {
    @Query("SELECT new com.estoque.backend.application.dto." +
            "ImgHighlightDTO(x.Id, x.Img, x.Alt) " +
            "FROM ImgHighlight AS x " +
            "WHERE x.Id = :imgHighlightId")
    ImgHighlightDTO GetById(UUID imgHighlightId);

    @Query("SELECT new com.estoque.backend.application.dto." +
            "ImgHighlightDTO(x.Id, x.Img, x.Alt) " +
            "FROM ImgHighlight AS x")
    List<ImgHighlightDTO> GetAllImgHighlights();

    @Query("SELECT new com.estoque.backend.application.dto." +
            "ImgHighlightDTO(x.Id, x.Img, null) " +
            "FROM ImgHighlight AS x " +
            "WHERE x.Id = :imgHighlightId")
    ImgHighlightDTO GetImgHighlightToDelete(UUID imgHighlightId);
}
//ImgHighlightDTO(UUID id, String img, String alt)