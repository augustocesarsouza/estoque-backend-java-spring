package com.estoque.backend.data.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.authentication.ITokenGenerator;
import com.estoque.backend.domain.authentication.TokenOutValue;
import com.estoque.backend.domain.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class TokenGenerator implements ITokenGenerator {
    @Value("${JWT-SECRET-KEY}")
    private String secretKey;
    @Override
    public InfoErrors<TokenOutValue> generatorTokenUser(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = currentUtcDateTime.plusHours(5);
//        LocalDateTime expires = currentUtcDateTime.plusMinutes(1);
//        LocalDateTime expires = currentUtcDateTime.plusSeconds(1);
        Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));

        if(user == null)
            return InfoErrors.Fail(new TokenOutValue(), "error: user is null");

        String token = JWT.create()
                //.withIssuer("Produtos")
                .withClaim("email", user.getEmail())
                //.withClaim("Password", password) - tirei daqui
                .withClaim("userID", user.getId().toString())
                .withExpiresAt(expiresDate)
                .sign(algorithm);

        var tokenValue = new TokenOutValue();
        var successfullyCreatedToken = tokenValue.ValidateToken(token, expiresDate);

        if (successfullyCreatedToken)
        {
            return InfoErrors.Ok(tokenValue);
        }
        else
        {
            return InfoErrors.Fail(new TokenOutValue(), "error when creating token");
        }
    }
    public Claim getClaimUserId(String token) throws TokenExpiredException {
        return JWT.require(Algorithm.HMAC256(secretKey))
                //.withIssuer("Produtos")
                .build().verify(token)//verific se o token está valido ou nao
                .getClaim("userID");
    }
}
