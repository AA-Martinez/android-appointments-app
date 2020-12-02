package com.example.consultasmedicas.utils.Substance;

import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Substance.Substance;
import com.example.consultasmedicas.model.UpdateResponse.UpdateResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SubstanceService {

    @GET("/substance")
    Call<ResponseBody> getSubstance(@Header("Authorization") String authHeader);

    @POST("/substance")
    Call<ResponseBody> createSubstance(@Body Substance substance, @Header("Authorization") String authHeader);

    @PUT("/patient/{appUserId}/substance")
    Call<ResponseBody> addSubstanceToPatient(@Path("appUserId") int appUserId, @Body List<UpdateResponse> updateResponses, @Header("Authorization") String authHeader);

    @HTTP(method = "DELETE", path = "/patient/{appUserId}/substance", hasBody = true)
    Call<ResponseBody> deleteSubstanceToPatient(@Path("appUserId") int appUserId, @Body List<UpdateResponse> updateResponses, @Header("Authorization") String authHeader);
}
