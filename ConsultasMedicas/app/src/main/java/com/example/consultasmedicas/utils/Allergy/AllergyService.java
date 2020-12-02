package com.example.consultasmedicas.utils.Allergy;

import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Patient.Patient;
import com.example.consultasmedicas.model.RequestResponse.RequestResponse;
import com.example.consultasmedicas.model.UpdateResponse.UpdateResponse;

import org.json.JSONArray;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AllergyService {

    @GET("/allergy")
    Call<ResponseBody> getAllergies(@Header("Authorization") String authHeader);

    @POST("/allergy")
    Call<ResponseBody> createAllergy(@Body Allergy allergy, @Header("Authorization") String authHeader);

    @PUT("/patient/{appUserId}/allergy")
    Call<ResponseBody> addAllergyToPatien(@Path("appUserId") int appUserId, @Body List<UpdateResponse> updateResponses, @Header("Authorization") String authHeader);

    @HTTP(method = "DELETE", path = "/patient/{appUserId}/allergy", hasBody = true)
    Call<ResponseBody> deleteAllergyToPatient(@Path("appUserId") int appUserId, @Body List<UpdateResponse> updateResponses, @Header("Authorization") String authHeader);
}
