package com.estoque.backend.data.repositories;

import com.estoque.backend.application.dto.ImgHighlightDTO;
import com.estoque.backend.data.context.ImgHighlightRepositoryJPA;
import com.estoque.backend.domain.entities.ImgHighlight;
import com.estoque.backend.domain.repositories.IImgHighlightRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ImgHighlightRepository implements IImgHighlightRepository {
    private final ImgHighlightRepositoryJPA imgHighlightRepositoryJPA;

    public ImgHighlightRepository(ImgHighlightRepositoryJPA imgHighlightRepositoryJPA) {
        this.imgHighlightRepositoryJPA = imgHighlightRepositoryJPA;
    }

    @Override
    public ImgHighlightDTO GetById(UUID imgHighlightId) {
        return imgHighlightRepositoryJPA.GetById(imgHighlightId);
    }

    @Override
    public List<ImgHighlightDTO> GetAllImgHighlights() {
        return imgHighlightRepositoryJPA.GetAllImgHighlights();
    }

    @Override
    public ImgHighlightDTO GetImgHighlightToDelete(UUID imgHighlightId) {
        return imgHighlightRepositoryJPA.GetImgHighlightToDelete(imgHighlightId);
    }

    @Override
    public ImgHighlight create(ImgHighlight imgHighlight) {
        if(imgHighlight == null)
            return null;

        return imgHighlightRepositoryJPA.save(imgHighlight);
    }

    @Override
    public ImgHighlight update(ImgHighlight imgHighlight) {
        if(imgHighlight == null)
            return null;

        var imgHighlightUpdate = imgHighlightRepositoryJPA.findById(imgHighlight.getId()).orElse(null);

        if(imgHighlightUpdate == null)
            return null;

        imgHighlightUpdate.SetValueEntity(imgHighlight.getImg(), imgHighlight.getAlt());

        return imgHighlightRepositoryJPA.save(imgHighlightUpdate);
    }

    @Override
    public ImgHighlight delete(UUID imgHighlightId) {
        if(imgHighlightId == null)
            return null;

        var imgHighlight = imgHighlightRepositoryJPA.findById(imgHighlightId).orElse(null);

        if(imgHighlight == null)
            return null;

        imgHighlightRepositoryJPA.delete(imgHighlight);

        return imgHighlight;
    }
}
