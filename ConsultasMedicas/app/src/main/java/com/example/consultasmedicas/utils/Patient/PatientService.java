package com.example.consultasmedicas.utils.Patient;

import com.example.consultasmedicas.model.Patient;

import org.json.JSONObject;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PatientService {
    @POST("/patient")
    Call<ResponseBody> signUp(@Body Patient patient);
}
