package com.estoque.backend.domain.authentication;

import java.util.Date;

public class TokenOutValue {
    private String Access_Token;
    private Date Expirations;

    public TokenOutValue(String access_Token, Date expirations) {
        Access_Token = access_Token;
        Expirations = expirations;
    }

    public TokenOutValue() {
    }

    public String getAccess_Token() {
        return Access_Token;
    }

    public Date getExpirations() {
        return Expirations;
    }

    public boolean ValidateToken(String access_Token, Date expirations){
        if(access_Token == null || access_Token.isEmpty())
            return false;

        Access_Token = access_Token;
        Expirations = expirations;
        return true;
    }
}
