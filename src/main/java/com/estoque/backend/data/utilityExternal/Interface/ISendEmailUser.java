package com.estoque.backend.data.utilityExternal.Interface;


import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.entities.User;

public interface ISendEmailUser {
    InfoErrors<String> sendCodeRandom(User user, int code);
//    InfoErrors<String> sendEmailConfirmRegisterUser(User user);
}
