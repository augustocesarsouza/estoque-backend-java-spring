package com.estoque.backend.application.dto;

public record UserInfo (String sub, String name, String given_name, String family_name, String picture, String email, Boolean email_verified, String locale){

}
