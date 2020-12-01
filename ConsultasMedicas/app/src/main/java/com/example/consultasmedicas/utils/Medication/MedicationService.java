package com.example.consultasmedicas.utils.Medication;

import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Medication.Medication;
import com.example.consultasmedicas.model.UpdateResponse.UpdateResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MedicationService {

    @GET("/medication")
    Call<ResponseBody> getMedications(@Header("Authorization") String authHeader);

    @POST("/medication")
    Call<ResponseBody> createMedication(@Body Medication medication, @Header("Authorization") String authHeader);

    @PUT("/patient/{appUserId}/medication")
    Call<ResponseBody> addMedicationToPatient(@Path("appUserId") int appUserId, @Body List<UpdateResponse> updateResponses, @Header("Authorization") String authHeader);
}
