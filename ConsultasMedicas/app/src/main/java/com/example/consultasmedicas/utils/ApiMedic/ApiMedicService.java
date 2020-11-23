package com.example.consultasmedicas.utils.ApiMedic;

import com.example.consultasmedicas.model.AppUser.AppUserDto;
import com.example.consultasmedicas.model.Patient.Patient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiMedicService {
    @POST("/login")
    Call<ResponseBody> loginApiMedic(@Header("Authorization") String authorizationApiMedic);
}
