package com.example.consultasmedicas.utils;

public class Apis {

    public static final String URL_001 = "http://localhost:8080";

    public static AllergyService getAllergyService(){
        return AllergyController.getAllergy(URL_001).create(AllergyService.class);
    }
    public static LoginService loginUserService(){
        return LoginController.loginUser(URL_001).create(LoginService.class);
    }

}
