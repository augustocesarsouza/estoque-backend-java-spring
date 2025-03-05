package com.estoque.backend.data.context;

import com.estoque.backend.application.dto.ReviewItemDTO;
import com.estoque.backend.domain.entities.ReviewItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewItemRepositoryJPA extends JpaRepository<ReviewItem, UUID> {
    @Query("SELECT new com.estoque.backend.application.dto." +
            "ReviewItemDTO(x.Id, null, null, null, null, null, null, null, null) " +
            "FROM ReviewItem AS x " +
            "WHERE x.Id = :reviewItemId")
    ReviewItemDTO CheckIfExistReviewItemById(UUID reviewItemId);

    @Query("SELECT new com.estoque.backend.application.dto." +
            "ReviewItemDTO(x.Id, x.NameUser, x.Email, x.ReviewTitle, x.Description, x.FitRating, x.QualityRating, x.PriceRating, x.ItemsId) " +
            "FROM ReviewItem AS x " +
            "WHERE x.Id = :reviewItemId")
    ReviewItemDTO GetReviewItemById(UUID reviewItemId);

    @Query("SELECT new com.estoque.backend.application.dto." +
            "ReviewItemDTO(x.Id, x.NameUser, x.Email, x.ReviewTitle, x.Description, x.FitRating, x.QualityRating, x.PriceRating, x.ItemsId) " +
            "FROM ReviewItem AS x " +
            "WHERE x.ItemsId = :itemId")
    List<ReviewItemDTO> GetAllReviewItemsByItemId(UUID itemId);
}
//ReviewItemDTO(UUID id, String nameUser, String email, String reviewTitle, String description, Short fitRating, Short qualityRating, Short priceRating, UUID itemsId)