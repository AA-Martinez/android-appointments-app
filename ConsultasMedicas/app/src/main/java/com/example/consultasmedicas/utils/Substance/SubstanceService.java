package com.example.consultasmedicas.utils.Substance;

import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Substance.Substance;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SubstanceService {

    @GET("/substance")
    Call<ResponseBody> getSubstance(@Header("Authorization") String authHeader);

    @POST("/substance")
    Call<ResponseBody> createSubstance(@Body Substance substance, @Header("Authorization") String authHeader);
}
