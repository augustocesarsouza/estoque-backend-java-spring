package com.estoque.backend.application.services;

import com.estoque.backend.application.dto.UserDTO;
import com.estoque.backend.application.dto.UserLoginDTO;
import com.estoque.backend.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.estoque.backend.application.dto.validations.DictionaryCodeDTOs.CodeVerify;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.CodeSendEmailUserValidatorDTO;
import com.estoque.backend.application.dto.validations.UserValidationDTOs.UserConfirmCodeEmailValidatorDTO;
import com.estoque.backend.application.services.interfaces.IUserAuthenticationService;
import com.estoque.backend.application.util.interfaces.IDictionaryCode;
import com.estoque.backend.data.utilityExternal.Interface.ISendEmailUser;
import com.estoque.backend.data.utilityExternal.Interface.ISendSmsTwilio;
import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.authentication.ITokenGenerator;
import com.estoque.backend.domain.authentication.TokenOutValue;
import com.estoque.backend.domain.entities.User;
import com.estoque.backend.domain.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ISendEmailUser sendEmailUser;
    private final ISendSmsTwilio sendSmsTwilio;
    private final IDictionaryCode dictionaryCode;
    private final AuthenticationManager authenticationManager;
    private final ITokenGenerator tokenGenerator;
    private final IValidateErrorsDTO validateErrorsDTO;

    public UserAuthenticationService(IUserRepository userRepository, ModelMapper modelMapper, ISendEmailUser sendEmailUser,
                                     ISendSmsTwilio sendSmsTwilio, IDictionaryCode dictionaryCode, AuthenticationManager authenticationManager,
                                     ITokenGenerator tokenGenerator, IValidateErrorsDTO validateErrorsDTO) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.sendEmailUser = sendEmailUser;
        this.sendSmsTwilio = sendSmsTwilio;
        this.dictionaryCode = dictionaryCode;
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.validateErrorsDTO = validateErrorsDTO;
    }

    @Override
    public ResultService<UserDTO> GetByIdInfoUser(UUID userId) {
        return null;
    }

    @Override
    @Transactional
    public ResultService<UserLoginDTO> Login(String email, String password) {
        var userLoginDTO = new UserLoginDTO();

        try {
            UserDTO user = userRepository.GetUserInfoToLogin(email);

            if (user == null) return ResultService.Fail("Error user info login is null");

            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            User userAuth = (User) authenticate.getPrincipal();

            InfoErrors<TokenOutValue> tokenOut = tokenGenerator.generatorTokenUser(userAuth);

            if(!tokenOut.IsSuccess)
                return ResultService.Fail(tokenOut.Message);

            int randomCode = generateRandomNumber();
            dictionaryCode.putKeyValueDictionary(userAuth.getId().toString(), randomCode);

//            InfoErrors<String> resultSendCodeEmail = sendEmailUser.sendCodeRandom(userAuth, randomCode); //Isso aqui para mandar email quando logar

            userAuth.setName(user.getName());
            userAuth.setPasswordHash(null);
            var phone = user.getCellPhone();
            userAuth.setCellPhone(phone);
            var userDTO = modelMapper.map(userAuth, UserDTO.class);

            if(userDTO == null)
                return ResultService.Fail("error in null class mapping");

            userDTO.setToken(tokenOut.Data.getAccess_Token());

            userLoginDTO.setPasswordIsCorrect(true);
            userLoginDTO.setUserDTO(userDTO);

            return ResultService.Ok(userLoginDTO);
        } catch (Exception ex) {
            userLoginDTO.setPasswordIsCorrect(false);
            userLoginDTO.setMessage(ex.getMessage());
            return ResultService.Fail(userLoginDTO);
        }
    }

    @Override
    public ResultService<CodeSendEmailUserValidatorDTO> SendCodeEmail(CodeSendEmailUserValidatorDTO codeSendEmailUserValidatorDTO, BindingResult result) {
        if(codeSendEmailUserValidatorDTO == null)
            return ResultService.Fail("Error DTO Informed is null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            User user = userRepository.GetUserByEmail(codeSendEmailUserValidatorDTO.getName());

            if (user == null)
                return ResultService.Fail("Error userDTO Not found");

            int randomCode = generateRandomNumber();
            dictionaryCode.putKeyValueDictionary(user.getId().toString(), randomCode);

            var resultSend = sendEmailUser.sendCodeRandom(user, randomCode);

            if(!resultSend.IsSuccess){
                return ResultService.Fail(new CodeSendEmailUserValidatorDTO(null, null, resultSend.Data,
                        false, false, false));
            }

            return ResultService.Ok(new CodeSendEmailUserValidatorDTO(null, null, String.valueOf(randomCode),
                    true, false, false));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<CodeVerify> VerifyCodeToLogin(UserConfirmCodeEmailValidatorDTO userConfirmCodeEmailValidatorDTO, BindingResult result) {
        if(userConfirmCodeEmailValidatorDTO == null)
            return ResultService.Fail("Error DTO Informed is null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            var userId = userConfirmCodeEmailValidatorDTO.getUserId();
            var userIdString = String.valueOf(userId);
            var value = dictionaryCode.getKeyDictionary(userIdString);

            if(value == null)
                return ResultService.Ok(new CodeVerify(false));

            Integer codeUser = userConfirmCodeEmailValidatorDTO.getCode();

            if(value.equals(codeUser)){
                dictionaryCode.removeKeyDictionary(userIdString);
                return ResultService.Ok(new CodeVerify(true));
            }

            return ResultService.Ok(new CodeVerify(false));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<UserLoginDTO> VerifyPasswordUser(String phone, String password) {
        return null;
    }

    private static int generateRandomNumber(){
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}
