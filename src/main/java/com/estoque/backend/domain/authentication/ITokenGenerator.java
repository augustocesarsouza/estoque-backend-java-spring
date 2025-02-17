package com.estoque.backend.domain.authentication;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.entities.User;

public interface ITokenGenerator {
    InfoErrors<TokenOutValue> generatorTokenUser(User user);
    Claim getClaimUserId(String token) throws TokenExpiredException;
}
