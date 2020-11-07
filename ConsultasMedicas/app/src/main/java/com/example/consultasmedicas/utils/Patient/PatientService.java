package com.example.consultasmedicas.utils.Patient;

import com.example.consultasmedicas.model.Patient;
import com.example.consultasmedicas.model.RequestResponse;

import org.json.JSONObject;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PatientService {
    @POST("/patient")
    Call<ResponseBody> signUp(@Body Patient patient);

    @GET("/patient/{patientId}")
    Call<RequestResponse> getPatient(@Path("patientId") String patientId, @Header("Authorization") String authHeader);
}
