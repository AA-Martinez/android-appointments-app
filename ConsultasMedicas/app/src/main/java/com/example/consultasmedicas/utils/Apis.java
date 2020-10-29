package com.example.consultasmedicas.utils;

public class Apis {

    public static final String URL_001 = "";

    public static AllergyService getAllergyService(){
        return AllergyController.getAllergy(URL_001).create(AllergyService.class);
    }


}
