package com.estoque.backend.data.repositories;

import com.estoque.backend.application.dto.ReviewItemDTO;
import com.estoque.backend.data.context.ReviewItemRepositoryJPA;
import com.estoque.backend.domain.entities.ReviewItem;
import com.estoque.backend.domain.entities.UserAddress;
import com.estoque.backend.domain.repositories.IReviewItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ReviewItemRepository implements IReviewItemRepository {
    private final ReviewItemRepositoryJPA reviewItemRepositoryJPA;

    @Autowired
    public ReviewItemRepository(ReviewItemRepositoryJPA reviewItemRepositoryJPA) {
        this.reviewItemRepositoryJPA = reviewItemRepositoryJPA;
    }

    @Override
    public ReviewItemDTO CheckIfExistReviewItemById(UUID reviewItemId) {
        return reviewItemRepositoryJPA.CheckIfExistReviewItemById(reviewItemId);
    }

    @Override
    public ReviewItemDTO GetReviewItemById(UUID reviewItemId) {
        return reviewItemRepositoryJPA.GetReviewItemById(reviewItemId);
    }

    @Override
    public List<ReviewItemDTO> GetAllReviewItemsByItemId(UUID itemId) {
        return reviewItemRepositoryJPA.GetAllReviewItemsByItemId(itemId);
    }

    @Override
    public ReviewItem create(ReviewItem reviewItem) {
        if(reviewItem == null)
            return null;

        return reviewItemRepositoryJPA.save(reviewItem);
    }

    @Override
    public ReviewItem update(ReviewItemDTO reviewItemDTO) {
        return null;
    }

    @Override
    public ReviewItem delete(UUID reviewItemId) {
        if(reviewItemId == null)
            return null;

        ReviewItem entity = reviewItemRepositoryJPA.findById(reviewItemId).orElse(null);

        if(entity == null)
            return null;

        reviewItemRepositoryJPA.delete(entity);
//        address.setUser(null);

        return entity;
    }
}
