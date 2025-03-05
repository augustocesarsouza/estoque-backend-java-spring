package com.estoque.backend.domain.repositories;

import com.estoque.backend.application.dto.ReviewItemDTO;
import com.estoque.backend.domain.entities.ReviewItem;

import java.util.List;
import java.util.UUID;

public interface IReviewItemRepository {
    ReviewItemDTO CheckIfExistReviewItemById(UUID reviewItemId);
    ReviewItemDTO GetReviewItemById(UUID reviewItemId);
    List<ReviewItemDTO> GetAllReviewItemsByItemId(UUID itemId);
    ReviewItem create(ReviewItem reviewItem);
    ReviewItem update(ReviewItemDTO reviewItemDTO);
    ReviewItem delete(UUID reviewItemId);
}
