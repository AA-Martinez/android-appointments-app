package com.example.consultasmedicas.utils.Medication;

import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Medication.Medication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MedicationService {

    @GET("/medication")
    Call<ResponseBody> getMedications(@Header("Authorization") String authHeader);

    @POST("/medication")
    Call<ResponseBody> createMedication(@Body Medication medication, @Header("Authorization") String authHeader);
}
