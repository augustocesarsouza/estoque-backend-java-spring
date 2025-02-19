package com.estoque.backend.applicationTest.utilTest;

import com.estoque.backend.application.util.BCryptPasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class BCryptPasswordEncoderUtilTest {
    private final BCryptPasswordEncoderUtil cryptPasswordEncoderUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPasswordEncoderUtilTest() {
        cryptPasswordEncoderUtil = new BCryptPasswordEncoderUtil();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void shouldEncodePasswordSuccessfully(){
        String password = "password123456";

        String resultEncode = cryptPasswordEncoderUtil.encodePassword(password);

        assertTrue(bCryptPasswordEncoder.matches(password, resultEncode));
    }

    @Test
    public void shouldEncodePasswordWithError(){
        String password = "password123456";

        String resultEncode = cryptPasswordEncoderUtil.encodePassword(password);

        assertFalse(bCryptPasswordEncoder.matches("password12345", resultEncode));
    }

    @Test
    public void shouldEncodePasswordNull(){
        String resultEncode = cryptPasswordEncoderUtil.encodePassword(null);

        assertNull(resultEncode);
    }
}
