package com.estoque.backend.application.services;

import com.estoque.backend.application.dto.ImgHighlightDTO;
import com.estoque.backend.application.services.interfaces.IImgHighlightService;
import com.estoque.backend.data.utilityExternal.Interface.ICloudinaryUti;
import com.estoque.backend.domain.entities.ImgHighlight;
import com.estoque.backend.domain.repositories.IImgHighlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class ImgHighlightService implements IImgHighlightService {
    private final IImgHighlightRepository imgHighlightRepository;
    private final ICloudinaryUti cloudinaryUti;
    private final ModelMapper modelMapper;

    public ImgHighlightService(IImgHighlightRepository imgHighlightRepository, ICloudinaryUti cloudinaryUti, ModelMapper modelMapper) {
        this.imgHighlightRepository = imgHighlightRepository;
        this.cloudinaryUti = cloudinaryUti;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultService<ImgHighlightDTO> GetById(UUID imgHighlightId) {
        try {
            var imgHighlightDTO = imgHighlightRepository.GetById(imgHighlightId);

            if(imgHighlightDTO == null){
                return ResultService.Fail("does not found imgHighlightDTO");
            }

            return ResultService.Ok(imgHighlightDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<List<ImgHighlightDTO>> GetAllImgHighlights() {
        try {
            var imgHighlightDTOs= imgHighlightRepository.GetAllImgHighlights();

            if(imgHighlightDTOs == null){
                return ResultService.Fail("does not found list imgHighlightDTO");
            }

            return ResultService.Ok(imgHighlightDTOs);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<ImgHighlightDTO> CreateAsync(ImgHighlightDTO imgHighlightDTO) {
        if(imgHighlightDTO == null)
            return ResultService.Fail("error DTO Is Null");

//        if(result.hasErrors()){
//            var errorsDTO = result.getAllErrors();
//            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);
//
//            return ResultService.RequestError("error validate DTO", errors);
//        }

        try {
            UUID entity_id = UUID.randomUUID();

            var userImage = imgHighlightDTO.getBase64Img();

            var resultCreate = cloudinaryUti.CreateMedia(userImage, "imgs-backend-estoque/img-home-page", 1180, 400);

            if (resultCreate.getImgUrl() == null || resultCreate.getPublicId() == null)
            {
                return ResultService.Fail("error when create ImgPerfil");
            }

            var imgHighlight = new ImgHighlight(entity_id, resultCreate.getImgUrl(), imgHighlightDTO.getAlt());

            var entityData = imgHighlightRepository.create(imgHighlight);

            if(entityData == null)
                return ResultService.Fail("error ImgHighlight Creation is null");

            var entityMap = modelMapper.map(entityData, ImgHighlightDTO.class);

            return ResultService.Ok(entityMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<ImgHighlightDTO> UpdateImgHighlight(ImgHighlightDTO imgHighlightDTO, BindingResult result) {
        return null;
    }

    @Override
    public ResultService<ImgHighlightDTO> Delete(UUID imgHighlightId) {
        try {
            var entityDelete = imgHighlightRepository.GetImgHighlightToDelete(imgHighlightId);

            if(entityDelete == null)
                return ResultService.Fail("ImgHighlight not found");

            if(entityDelete.getImg() != null){
                var deleteFound = cloudinaryUti.DeleteFileCloudinaryExtractingPublicIdFromUrlList(entityDelete.getImg());

                if(!deleteFound.getDeleteSuccessfully())
                    return ResultService.Fail(deleteFound.getMessage());
            }

            var entityDeleteSuccessfully = imgHighlightRepository.delete(entityDelete.getId());

            return ResultService.Ok(modelMapper.map(entityDeleteSuccessfully, ImgHighlightDTO.class));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
