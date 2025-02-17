package com.estoque.backend.data.utilityExternal;

import com.estoque.backend.data.utilityExternal.Interface.ISendEmailBrevo;
import com.estoque.backend.domain.InfoErrors.InfoErrors;
import com.estoque.backend.domain.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class SendEmailBrevo implements ISendEmailBrevo {
    @Value("${BREVO-SECRET-KEY}")
    private String brevoSecretKey;
    public InfoErrors<String> sendEmail(User user, String url) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(brevoSecretKey);

        try {
            TransactionalEmailsApi api = new TransactionalEmailsApi();
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail("augustocesarsantana53@gmail.com");
            sender.setName("augusto");
            List<SendSmtpEmailTo> toList = new ArrayList<>();
            SendSmtpEmailTo to = new SendSmtpEmailTo();
            Properties headers = new Properties();
            headers.setProperty("Some-Custom-Name", "unique-id-1234");
            Properties params = new Properties();
            params.setProperty("parameter", "My param value");
            params.setProperty("subject", "New Subject");
            to.setEmail(user.getEmail());
            to.setName(user.getName());
            toList.add(to);
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);
            sendSmtpEmail.setHtmlContent(String.format("<html><body><h1>Clique no token disponivel: %s</h1></body></html>", url));
            sendSmtpEmail.setSubject("My {{params.subject}}");
            sendSmtpEmail.setHeaders(headers);
            sendSmtpEmail.setParams(params);
            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);

            return InfoErrors.Ok("Everything ok with sending the email");
        } catch (Exception e) {
            return InfoErrors.Fail("Erro no envio do email, ERROR: " + e.getMessage());
        }
    }
    @Override
    public InfoErrors<String> sendCode(User user, int codeRandom) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(brevoSecretKey);

        try {
            TransactionalEmailsApi api = new TransactionalEmailsApi();
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail("augustocesarsantana53@gmail.com");
            sender.setName("augusto");
            List<SendSmtpEmailTo> toList = new ArrayList<>();
            SendSmtpEmailTo to = new SendSmtpEmailTo();
            Properties headers = new Properties();
            headers.setProperty("Some-Custom-Name", "unique-id-1234");
            Properties params = new Properties();
            params.setProperty("parameter", "My param value");
            params.setProperty("subject", "SEU NUMERO ALEATORIO DE CONFIRMAÇÃO");
            to.setEmail(user.getEmail());
            to.setName(user.getName());
            toList.add(to);
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

            String TextContent = "Seu numero de Confirmação: " + codeRandom;

            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);
            sendSmtpEmail.setHtmlContent(String.format("<html><body><h1>Seu numero de Confirmação:  %s</h1></body></html>", TextContent));
            sendSmtpEmail.setSubject("My {{params.subject}}");
            sendSmtpEmail.setHeaders(headers);
            sendSmtpEmail.setParams(params);
            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);

            return InfoErrors.Ok("Everything ok with sending the email");
        } catch (Exception e) {
            return InfoErrors.Fail("Erro no envio do email, ERROR: " + e.getMessage());
        }
    }
}
