package com.estoque.backend.application.util;

import java.util.regex.Pattern;

public class ValidateUUID {
    public static boolean Validate(String uuid){
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(uuid).matches();
    }
}
