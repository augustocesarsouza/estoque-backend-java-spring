package com.estoque.backend.data.utilityExternal;

import com.estoque.backend.data.utilityExternal.Interface.ISendEmailBrevo;
import com.estoque.backend.data.utilityExternal.Interface.ISendEmailUser;
import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendEmailUser implements ISendEmailUser {
    @Value("${JWT-SECRET-KEY}")
    private String secretKey;
    private final ISendEmailBrevo sendEmailBrevo;
//    private final ICacheRedisUti cacheRedisUti;

    @Autowired
    public SendEmailUser(ISendEmailBrevo sendEmailBrevo) {
      this.sendEmailBrevo = sendEmailBrevo;
    }
    @Override
    public InfoErrors<String> sendCodeRandom(User user, int code) {
        return  sendEmailBrevo.sendCode(user, code);
    }

//    @Override
//    public InfoErrors<String> sendEmailConfirmRegisterUser(User user) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secretKey);
//            LocalDateTime currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC);
//            LocalDateTime expires = currentUtcDateTime.plusMinutes(10);
//            Date expiresDate = Date.from(expires.toInstant(ZoneOffset.UTC));
//
//            String token = JWT.create()
//                    .withClaim("id", user.getId().toString())
//                    .withExpiresAt(expiresDate)
//                    .sign(algorithm);
//
//            String key = "TokenString" + user.getId().toString();
//            String cache = cacheRedisUti.getString(key);
//
//            if(cache == null){
//                cacheRedisUti.setString(key, token, 10, TimeUnit.MINUTES);
//            }
//
//            String url = "http://localhost:4200/my-account/confirm-email/"+token;
//            var resultSend = sendEmailBrevo.sendEmail(user, url);
//
//            if(!resultSend.IsSuccess)
//                return InfoErrors.Fail(resultSend.Message);
//
//            return InfoErrors.Ok("tudo certo com o envio do email");
//        }catch (Exception ex){
//            return InfoErrors.Fail("Erro no envio do email, ERROR: " + ex.getMessage());
//        }
//    }
}
