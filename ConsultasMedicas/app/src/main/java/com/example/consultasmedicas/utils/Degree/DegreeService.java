package com.example.consultasmedicas.utils.Degree;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DegreeService {
    @GET("/degree/specialty")
    Call<ResponseBody> getSpecialtyList(@Header("Authorization") String authHeader);

    @GET("/degree/specialty/doctor")
    Call<ResponseBody> getSpecialtyDoctor(@Header("Authorization") String authHeader);
}
