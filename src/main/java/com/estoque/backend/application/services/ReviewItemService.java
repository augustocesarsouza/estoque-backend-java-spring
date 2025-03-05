package com.estoque.backend.application.services;

import com.estoque.backend.application.dto.CategoryDTO;
import com.estoque.backend.application.dto.ReviewItemDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.ReviewItemValidationDTOs.ReviewItemCreateValidatorDTO;
import com.estoque.backend.application.services.interfaces.IReviewItemService;
import com.estoque.backend.domain.entities.ReviewItem;
import com.estoque.backend.domain.repositories.IItemRepository;
import com.estoque.backend.domain.repositories.IReviewItemRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewItemService implements IReviewItemService {
    private final IReviewItemRepository reviewItemRepository;
    private final IItemRepository itemRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final ModelMapper modelMapper;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public ReviewItemService(PlatformTransactionManager transactionManager, IReviewItemRepository reviewItemRepository, IItemRepository itemRepository, IValidateErrorsDTO validateErrorsDTO, ModelMapper modelMapper) {
        this.transactionManager = transactionManager;
        this.reviewItemRepository = reviewItemRepository;
        this.itemRepository = itemRepository;
        this.validateErrorsDTO = validateErrorsDTO;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultService<ReviewItemDTO> GetReviewItemById(UUID reviewItemId) {
        try {
            var entityDTO = reviewItemRepository.GetReviewItemById(reviewItemId);

            if(entityDTO == null){
                return ResultService.Fail("ReviewItem does not found");
            }

            return ResultService.Ok(entityDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<List<ReviewItemDTO>> GetAllReviewItemsByItemId(UUID itemId) {
        try {
            var entityDTOs = reviewItemRepository.GetAllReviewItemsByItemId(itemId);

            if(entityDTOs == null){
                return ResultService.Fail("ReviewItem list does not found");
            }

            return ResultService.Ok(entityDTOs);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<ReviewItemDTO> CreateAsync(ReviewItemCreateValidatorDTO reviewItemCreateValidatorDTO, BindingResult result) {
        if(reviewItemCreateValidatorDTO == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            UUID entityId = UUID.randomUUID();
            UUID itemsId = UUID.fromString(reviewItemCreateValidatorDTO.getItemsId());

            var verifyWhetherExistItem = itemRepository.GetItemByItemId(itemsId);

            if(verifyWhetherExistItem == null)
                return ResultService.Fail("Error Item does not found");

            var entityCreate = new ReviewItem(entityId, reviewItemCreateValidatorDTO.getNameUser(), reviewItemCreateValidatorDTO.getEmail(),
                    reviewItemCreateValidatorDTO.getReviewTitle(), reviewItemCreateValidatorDTO.getDescription(),
                    reviewItemCreateValidatorDTO.getFitRating(), reviewItemCreateValidatorDTO.getQualityRating(),
                    reviewItemCreateValidatorDTO.getPriceRating(), itemsId);

            var createEntity = reviewItemRepository.create(entityCreate);
            var entityMap = modelMapper.map(createEntity, ReviewItemDTO.class);

            transactionManager.commit(status);
            return ResultService.Ok(entityMap);
        }catch (Exception ex){
            transactionManager.rollback(status);
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<ReviewItemDTO> Delete(UUID reviewItemId) {
        try {
            var entityDeleteSuccessfully = reviewItemRepository.delete(reviewItemId);

            if(entityDeleteSuccessfully == null)
                return ResultService.Fail("ReviewItem does not found");

            var entityMap = modelMapper.map(entityDeleteSuccessfully, ReviewItemDTO.class);

            return ResultService.Ok(entityMap);

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
