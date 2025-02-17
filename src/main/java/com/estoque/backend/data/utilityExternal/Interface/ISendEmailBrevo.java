package com.estoque.backend.data.utilityExternal.Interface;

import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.entities.User;

public interface ISendEmailBrevo {
    InfoErrors<String> sendEmail(User user, String url);
    InfoErrors<String> sendCode(User user, int codeRandom);
}
