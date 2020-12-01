package com.example.consultasmedicas.utils.Doctor;

import com.example.consultasmedicas.model.Doctor.Doctor;
import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.model.RequestResponse.RequestResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DoctorService {

    @GET("/doctor")
    Call<ResponseBody> getDoctorList(@Header("Authorization") String authHeader);


    @GET("/doctor/appUser/{appUserId}")
    Call<ResponseBody> getDoctor(@Path("appUserId") int appUserId, @Header("Authorization") String authHeader);

}
