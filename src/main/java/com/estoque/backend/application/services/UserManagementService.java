package com.estoque.backend.application.services;

import com.estoque.backend.application.dto.ChangePasswordResult;
import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.UserAddressValidationDTOs.UserAddressCreateValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserCreateLoginGoogleValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserCreateValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserUpdatePasswordValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserUpdateValidatorDTO;
import com.estoque.backend.application.services.interfaces.IUserAddressService;
import com.estoque.backend.application.services.interfaces.IUserManagementService;
import com.estoque.backend.application.util.interfaces.IBCryptPasswordEncoderUtil;
import com.estoque.backend.data.utilityExternal.Interface.ICloudinaryUti;
import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.authentication.ITokenGenerator;
import com.estoque.backend.domain.authentication.TokenOutValue;
import com.estoque.backend.domain.entities.User;
import com.estoque.backend.domain.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class UserManagementService implements IUserManagementService {
    private final IUserRepository userRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final IBCryptPasswordEncoderUtil bCryptPasswordEncoder;
    private final ICloudinaryUti cloudinaryUti;
    private final ModelMapper modelMapper;
    private final ITokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserManagementService(IUserRepository userRepository, IValidateErrorsDTO validateErrorsDTO,
                                 IBCryptPasswordEncoderUtil bCryptPasswordEncoder, ICloudinaryUti cloudinaryUti,
                                 ModelMapper modelMapper, ITokenGenerator tokenGenerator,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.validateErrorsDTO = validateErrorsDTO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.cloudinaryUti = cloudinaryUti;
        this.modelMapper = modelMapper;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResultService<UserDTO> findById(String phone) {
        var userDto = new UserDTO();
        userDto.setCpf("asdok´pjcvdsioupjfdjiosp");
        return ResultService.Ok(userDto);
    }

    @Override
    public ResultService<UserDTO> findByIdToDatePersonal(UUID userId) {
        try {
            var entityDTO = userRepository.findByIdToDatePersonal(userId);

            if(entityDTO == null)
                return ResultService.Fail("not found user");

            return ResultService.Ok(entityDTO);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<UserDTO> VerifyWhetherUserExist(UUID userId) {
        try {
            User user = userRepository.GetUserByIdForDeleteImg(userId);

            if(user == null){
                return ResultService.Fail("not found");
            }

            user.setPasswordHash(null);

            var userMap = modelMapper.map(user, UserDTO.class);
            return ResultService.Ok(userMap); // testar isso para ver se o "Mapper" vai funcionar
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserDTO> create(UserCreateValidatorDTO userCreateValidatorDTO, BindingResult result) {
        if(userCreateValidatorDTO == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        User userCreate = new User();

        try {
            String passwordEncoder = bCryptPasswordEncoder.encodePassword(userCreateValidatorDTO.getPassword());
            UUID uuid_user_id = UUID.randomUUID();

            var birthDateString = userCreateValidatorDTO.getBirthDate(); // Editar isso amanha porque tem que converter de String para LocalDateTime
            var birthDateStringSplit = birthDateString.split("/");

            var day = birthDateStringSplit[0];
            var month = birthDateStringSplit[1];
            var year = birthDateStringSplit[2];

            int intDay = Integer.parseInt(day);
            int intMonth = Integer.parseInt(month);
            int intYear = Integer.parseInt(year);

            LocalDateTime birthDate = LocalDateTime.of(intYear, intMonth, intDay, 0, 0);
            ZonedDateTime birthDateUtc = birthDate.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));

            var userImage = userCreateValidatorDTO.getUserImage();
            if(userImage != null && !userImage.isEmpty()){

                var resultCreate = cloudinaryUti.CreateMedia(userCreateValidatorDTO.getUserImage(), "img-user-estoque", 320, 320);

                if (resultCreate.getImgUrl() == null || resultCreate.getPublicId() == null)
                {
                    return ResultService.Fail("error when create ImgPerfil");
                }

                userCreate = new User(uuid_user_id, userCreateValidatorDTO.getName(), userCreateValidatorDTO.getLastName(),
                        birthDate, userCreateValidatorDTO.getGender(), userCreateValidatorDTO.getCpf(),
                        userCreateValidatorDTO.getEmail(), userCreateValidatorDTO.getLandline(), userCreateValidatorDTO.getCellPhone(),
                        passwordEncoder, resultCreate.getImgUrl(), (short)0);
            }else {
                userCreate = new User(uuid_user_id, userCreateValidatorDTO.getName(), userCreateValidatorDTO.getLastName(),
                        birthDate, userCreateValidatorDTO.getGender(), userCreateValidatorDTO.getCpf(),
                        userCreateValidatorDTO.getEmail(), userCreateValidatorDTO.getLandline(), userCreateValidatorDTO.getCellPhone(),
                        passwordEncoder, null, (short)0);
            }

            var userData = userRepository.create(userCreate);

            if(userData == null)
                return ResultService.Fail("error User Creation is null");

//            var userAddressCreateValidatorDTO = userCreateValidatorDTO.getUserAddressCreateValidatorDTO();
//            userAddressCreateValidatorDTO.setUserId(uuid_user_id.toString());
//
//            var resultCreate = userAddressService.CreateToUser(userAddressCreateValidatorDTO);
//
//            if(!resultCreate.IsSuccess)
//                return ResultService.Fail(resultCreate.Message);

            InfoErrors<TokenOutValue> tokenOut = tokenGenerator.generatorTokenUser(userData);

            if(!tokenOut.IsSuccess)
                return ResultService.Fail(tokenOut.Message);

            userData.setPasswordHash(null);

            var userMap = modelMapper.map(userData, UserDTO.class);
            userMap.setToken(tokenOut.Data.getAccess_Token());

            return ResultService.Ok(userMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserDTO> createToLoginGoogle(UserCreateLoginGoogleValidatorDTO userCreateLoginGoogleValidatorDTO,
                                                      BindingResult result) {
        if(userCreateLoginGoogleValidatorDTO == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        User userCreate = new User();

        try {
            UUID uuid_user_id = UUID.randomUUID();
            var name = userCreateLoginGoogleValidatorDTO.getName();
            var email = userCreateLoginGoogleValidatorDTO.getEmail();
            var lastName = userCreateLoginGoogleValidatorDTO.getLastName();

            var user = userRepository.GetUserByEmail(email);

            if(user != null){
                var userMap = modelMapper.map(user, UserDTO.class);

                InfoErrors<TokenOutValue> tokenOut = tokenGenerator.generatorTokenUser(user);

                if(!tokenOut.IsSuccess)
                    return ResultService.Fail(tokenOut.Message);

                userMap.setToken(tokenOut.Data.getAccess_Token());

                return ResultService.Ok(userMap);
            }

            LocalDateTime randomDate = LocalDateTime.of(1, 1, 1, 0, 0);// Talvez posso colocar null no banco Date

            userCreate = new User(uuid_user_id, name, lastName,
                    randomDate, "", "00000000000",
                    email, null, "",
                    "", null, (short)0);

            var userData = userRepository.create(userCreate);

            if(userData == null)
                return ResultService.Fail("error User Creation is null");

//            var userAddressCreateValidatorDTO = new UserAddressCreateValidatorDTO(null, null, null,
//                    null, null,null, null, null, null,0, -1, uuid_user_id.toString());

//            UserAddressCreateValidatorDTO(String cep, String recipientName, String address,
//                    Integer numberHome, String complement, String neighborhood, String city,
//                    String state, String referencePoint, Integer defaultAddress, Integer saveAs, String userId,
//                    String cellPhone)

//            var resultCreate = userAddressService.CreateToUser(userAddressCreateValidatorDTO);
//
//            if(!resultCreate.IsSuccess)
//                return ResultService.Fail(resultCreate.Message);

            InfoErrors<TokenOutValue> tokenOut = tokenGenerator.generatorTokenUser(userData);

            if(!tokenOut.IsSuccess)
                return ResultService.Fail(tokenOut.Message);

            userData.setPasswordHash(null);

            var userMap = modelMapper.map(userData, UserDTO.class);
            userMap.setToken(tokenOut.Data.getAccess_Token());

            return ResultService.Ok(userMap);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserDTO> UpdateUser(UserUpdateValidatorDTO userUpdateValidatorDTO, BindingResult result) {
        if(userUpdateValidatorDTO == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            var checkIfUserExist = userRepository.GetUserByEmail(userUpdateValidatorDTO.getEmail());

            if(checkIfUserExist == null)
                return ResultService.Fail("Error user not exist");

            String birthDateString = userUpdateValidatorDTO.getBirthDate();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthDate = LocalDate.parse(birthDateString, formatter);

            // Converter para LocalDateTime adicionando o horário padrão 00:00:00
            LocalDateTime birthDateTime = birthDate.atStartOfDay();
            userUpdateValidatorDTO.setBirthDateLocalDateTime(birthDateTime);

            var updateEntity = userRepository.update(userUpdateValidatorDTO);

            var EntityDTOUpdateMap = modelMapper.map(updateEntity, UserDTO.class);

            return ResultService.Ok(EntityDTOUpdateMap);

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<ChangePasswordResult> UpdateUserPassword(UserUpdatePasswordValidatorDTO userUpdatePasswordValidatorDTO, BindingResult result) {
        if(userUpdatePasswordValidatorDTO == null)
            return ResultService.Fail("error DTO Is Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            var email = userUpdatePasswordValidatorDTO.getEmail();
            var password = userUpdatePasswordValidatorDTO.getActualPassword();
            var checkIfUserExist = userRepository.GetUserByEmail(email);

            if(checkIfUserExist == null)
                return ResultService.Fail("Error user not exist");

            String passwordEncoder = bCryptPasswordEncoder.encodePassword(userUpdatePasswordValidatorDTO.getNewPassword());

            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            // TESTAR SE A SENHA FOR ERRADA TEM QUE PEGAR "Exception" E DEPOIS RETORNAR AQUI "ChangePasswordResult" UM OBJETO

            User userAuth = (User) authenticate.getPrincipal();

            var userUpdate = new User();
            userUpdate.setPasswordHash(passwordEncoder);
            userUpdate.setId(UUID.fromString(userUpdatePasswordValidatorDTO.getUserId()));

            var updateEntity = userRepository.updatePassword(userUpdate);

            var EntityDTOUpdateMap = modelMapper.map(updateEntity, UserDTO.class);

            return ResultService.Ok(new ChangePasswordResult(true, true));

        }catch (BadCredentialsException ex){
            return ResultService.Fail(new ChangePasswordResult(false, false));
        } catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserDTO> Delete(UUID userID) {
        return null; // FAZER O DELETE E SE TIVER UMA IMAGEM NO CLOUDINARY DELETAR IMG TBM
    }
}
