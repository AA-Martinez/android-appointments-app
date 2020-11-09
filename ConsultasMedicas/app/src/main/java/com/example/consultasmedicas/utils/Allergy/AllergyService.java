package com.example.consultasmedicas.utils.Allergy;

import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Patient.Patient;
import com.example.consultasmedicas.model.RequestResponse.RequestResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AllergyService {

    @GET("/allergy")
    Call<ResponseBody> getAllergies(@Header("Authorization") String authHeader);

    @POST("/allergy")
    Call<ResponseBody> createAllergy(@Body Allergy allergy, @Header("Authorization") String authHeader);
}
