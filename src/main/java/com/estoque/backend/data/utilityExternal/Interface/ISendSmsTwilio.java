package com.estoque.backend.data.utilityExternal.Interface;

public interface ISendSmsTwilio {
    Boolean SendSms(String toPhoneNumber, String messageContent);
}
