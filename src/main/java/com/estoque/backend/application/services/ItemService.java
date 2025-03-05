package com.estoque.backend.application.services;

import com.estoque.backend.application.dto.ItemDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.ItemValidationDTOs.ItemCreateValidatorDTO;
import com.estoque.backend.application.services.interfaces.IItemService;
import com.estoque.backend.data.utilityExternal.Interface.ICloudinaryUti;
import com.estoque.backend.domain.entities.Category;
import com.estoque.backend.domain.entities.Item;
import com.estoque.backend.domain.repositories.IItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService implements IItemService {
    private final IItemRepository itemRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final ModelMapper modelMapper;
    private final ICloudinaryUti cloudinaryUti;

    @Autowired
    public ItemService(IItemRepository itemRepository, IValidateErrorsDTO validateErrorsDTO, ModelMapper modelMapper,
                       ICloudinaryUti cloudinaryUti) {
        this.itemRepository = itemRepository;
        this.validateErrorsDTO = validateErrorsDTO;
        this.modelMapper = modelMapper;
        this.cloudinaryUti = cloudinaryUti;
    }

    @Override
    public ResultService<List<ItemDTO>> GetItemByNameCategory(String nameCategory) {
        try {
            var entityDTOs = itemRepository.GetItemByNameCategory(nameCategory);

            if(entityDTOs == null){
                return ResultService.Fail("itemDTOs does not found");
            }

            return ResultService.Ok(entityDTOs);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<ItemDTO> GetItemByIdWithCategory(UUID itemId) {
        try {
            var entityDTO = itemRepository.GetItemByIdWithCategory(itemId);

            if(entityDTO == null){
                return ResultService.Fail("itemDTO does not found");
            }

            return ResultService.Ok(entityDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<ItemDTO> CreateAsync(ItemCreateValidatorDTO itemCreateValidatorDTO, BindingResult result) {
        if(itemCreateValidatorDTO == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            List<String> listImgAll = itemCreateValidatorDTO.getImgProductAll();
//            List<String> listImages = new ArrayList<>();
            List<String> listImgVideo = new ArrayList<>();

            if(listImgAll != null && !listImgAll.isEmpty()){
                for (String el : listImgAll) {
                    boolean isImage = el.startsWith("data:image");
                    boolean isVideo = el.startsWith("data:video");

                    if(isImage) {
//                        String base64String = el.substring(el.indexOf(",") + 1);
                        String base64String = el.split(",")[1];

                        int width = 10;
                        int height = 10;

                        try {
                            byte[] imageBytes = Base64.getDecoder().decode(base64String);

                            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                            BufferedImage image = ImageIO.read(inputStream);

                            if (image != null) {
                                width = image.getWidth();
                                height = image.getHeight();
                            } else {
                                return ResultService.Fail("Unable to read image data.");
                            }
                        } catch (IOException e) {
                            return ResultService.Fail("Error processing image. " + e.getMessage());
                        }

                        var resultCreate = cloudinaryUti.CreateMedia(el, "imgs-backend-estoque/images-item", width, height);

                        if (resultCreate.getImgUrl() == null || resultCreate.getPublicId() == null)
                            return ResultService.Fail("error when create Img item");

                        listImgVideo.add(resultCreate.getImgUrl());
                    }else if (isVideo){
                        var resultCreate = cloudinaryUti.CreateMedia(el, "imgs-backend-estoque/images-item", 517, 919);

                        if (resultCreate.getImgUrl() == null || resultCreate.getPublicId() == null) {
                            return ResultService.Fail("Error when creating video.");
                        }

                        listImgVideo.add(resultCreate.getImgUrl());
                    }
                };
            }

            var itemDTO = new ItemDTO(null, itemCreateValidatorDTO.getName(), itemCreateValidatorDTO.getPriceProduct(),
                    itemCreateValidatorDTO.getDiscountPercentage(), itemCreateValidatorDTO.getSize(), itemCreateValidatorDTO.getBrand(),
                    itemCreateValidatorDTO.getDescription(),
                    null, itemCreateValidatorDTO.getCategoryId(), listImgVideo);

            var createEntity = itemRepository.create(itemDTO);

            var entityMap = modelMapper.map(createEntity, ItemDTO.class);

            return ResultService.Ok(entityMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<ItemDTO> Delete(UUID itemId) {
        try {
            var entityToDelete = itemRepository.GetItemByItemId(itemId);

            if(entityToDelete == null)
                return ResultService.Fail("Item does not found");

            var listImg = entityToDelete.getImgProductAll();

            if(listImg != null && !listImg.isEmpty()){
                for (String el : listImg) {
                    var deleteFound = cloudinaryUti.DeleteFileCloudinaryExtractingPublicIdFromUrlList(el);
                    // Arrumar esse delete para deletar porque nao está acho que é "DeleteFileCloudinaryExtractingPublicIdFromUrlList"

                    if(!deleteFound.getDeleteSuccessfully())
                        return ResultService.Fail(deleteFound.getMessage());
                }
            }

            var entityDeleteSuccessfully = itemRepository.delete(itemId);

            var entityMap = modelMapper.map(entityDeleteSuccessfully, ItemDTO.class);

            return ResultService.Ok(entityMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
