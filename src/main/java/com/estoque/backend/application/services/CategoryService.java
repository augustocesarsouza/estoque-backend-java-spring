package com.estoque.backend.application.services;

import com.estoque.backend.application.dto.CategoryDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.services.interfaces.ICategoryService;
import com.estoque.backend.domain.entities.Category;
import com.estoque.backend.domain.repositories.ICategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository, IValidateErrorsDTO validateErrorsDTO, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validateErrorsDTO = validateErrorsDTO;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultService<CategoryDTO> GetItemByCategoryId(UUID categoryId) {
        try {
            var entityDTO = categoryRepository.GetItemByCategoryId(categoryId);

            if(entityDTO == null){
                return ResultService.Fail("category does not found");
            }

            return ResultService.Ok(entityDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<CategoryDTO> CreateAsync(CategoryDTO categoryDTO) {
        if(categoryDTO == null)
            return ResultService.Fail("error DTO Is Null");

//        if(result.hasErrors()){
//            var errorsDTO = result.getAllErrors();
//            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);
//
//            return ResultService.RequestError("error validate DTO", errors);
//        }

        try {
            UUID entityId = UUID.randomUUID();

            var entityCreate = new Category(entityId, categoryDTO.getNameCategory(), null);
//
            var createEntity = categoryRepository.create(entityCreate);

            var entityMap = modelMapper.map(createEntity, CategoryDTO.class);

            return ResultService.Ok(entityMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<CategoryDTO> Delete(UUID categoryId) {
        try {
            var entityToDelete = categoryRepository.GetItemByCategoryId(categoryId);

            if(entityToDelete == null)
                return ResultService.Fail("Item does not found");

            var entityDeleteSuccessfully = categoryRepository.delete(categoryId);

            var entityMap = modelMapper.map(entityDeleteSuccessfully, CategoryDTO.class);

            return ResultService.Ok(entityMap);

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
