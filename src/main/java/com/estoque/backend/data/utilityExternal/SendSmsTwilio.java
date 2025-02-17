package com.estoque.backend.data.utilityExternal;

import com.estoque.backend.data.utilityExternal.Interface.ISendSmsTwilio;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendSmsTwilio implements ISendSmsTwilio {
    @Value("${TWILIO_ACCOUNT_SID}")
    private String accountSid;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;
    @Value("${TWILIO_PHONE_NUMBER}")
    private String twilioPhoneNumber;

    public Boolean SendSms(String toPhoneNumber, String messageContent) {
        try {
            Twilio.init(accountSid, authToken);

            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    messageContent
            ).create();

//            System.out.println("Message SID: " + message.getSid());
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }
}
