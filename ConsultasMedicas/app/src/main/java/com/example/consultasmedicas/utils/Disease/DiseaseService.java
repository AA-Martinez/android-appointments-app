package com.example.consultasmedicas.utils.Disease;

import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Disease.Disease;
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

public interface DiseaseService {

    @GET("/disease")
    Call<ResponseBody> getDiseases(@Header("Authorization") String authHeader);

    @POST("/disease")
    Call<ResponseBody> createDisease(@Body Disease disease, @Header("Authorization") String authHeader);

    @PUT("/patient/{appUserId}/disease")
    Call<ResponseBody> addDiseaseToPatien(@Path("appUserId") int appUserId, @Body List<UpdateResponse> updateResponses, @Header("Authorization") String authHeader);
}
