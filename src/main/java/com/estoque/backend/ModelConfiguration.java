package com.estoque.backend;

import com.estoque.backend.application.dto.UserAddressDTO;
import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.domain.entities.User;
import com.estoque.backend.domain.entities.UserAddress;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

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

        return modelMapper;
    }
}
