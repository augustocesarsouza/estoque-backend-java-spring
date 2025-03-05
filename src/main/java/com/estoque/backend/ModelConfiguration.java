package com.estoque.backend;

import com.estoque.backend.application.dto.*;
import com.estoque.backend.domain.entities.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.UUID;

@Configuration
public class ModelConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(context -> context.getSource() != null);

        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setLastName(source.getLastName());
                map().setBirthDate(source.getBirthDate());
                map().setGender(source.getGender());
                map().setCpf(source.getCpf());
                map().setEmail(source.getEmail());
                map().setLandline(source.getLandline());
                map().setCellPhone(source.getCellPhone());
                map().setPasswordHash(source.getPasswordHash());
                map().setUserImage(source.getUserImage());
                map().setConfirmEmail(source.getConfirmEmail());
            }
        });

        modelMapper.addMappings(new PropertyMap<UserAddress, UserAddressDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setFullName(source.getFullName());
                map().setPhoneNumber(source.getPhoneNumber());
                map().setCep(source.getCep());
                map().setStateCity(source.getStateCity());
                map().setNeighborhood(source.getNeighborhood());
                map().setStreet(source.getStreet());
                map().setNumberHome(source.getNumberHome());
                map().setComplement(source.getComplement());
                map().setDefaultAddress(source.getDefaultAddress());
                map().setUserId(source.getUserId());

                when(Objects::nonNull)
                        .map(source.getUser(), destination.getUserDTO());
            }
        });

        modelMapper.addMappings(new PropertyMap<ImgHighlight, ImgHighlightDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setImg(source.getImg());
                map().setAlt(source.getAlt());
            }
        });

        modelMapper.addMappings(new PropertyMap<Item, ItemDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setPriceProduct(source.getPriceProduct());
                map().setSize(source.getSize());
                map().setBrand(source.getBrand());
                map().setCategory(null);
            }
        });

        modelMapper.addMappings(new PropertyMap<Category, CategoryDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setNameCategory(source.getNameCategory());
//                map().setItemsDTOs(source.getItems());
            }
        });

        modelMapper.addMappings(new PropertyMap<ReviewItem, ReviewItemDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setNameUser(source.getNameUser());
                map().setEmail(source.getEmail());
                map().setReviewTitle(source.getReviewTitle());
                map().setDescription(source.getDescription());
                map().setFitRating(source.getFitRating());
                map().setQualityRating(source.getQualityRating());
                map().setPriceRating(source.getPriceRating());
                map().setItemsId(source.getItemsId());
            }
        });

        return modelMapper;
    }
}
