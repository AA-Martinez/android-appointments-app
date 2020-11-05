package com.example.consultasmedicas.utils.Allergy;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllergyController {

    public static Retrofit getAllergy(String url){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

}
