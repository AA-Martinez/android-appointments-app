package com.example.consultasmedicas.utils.Login;

import com.example.consultasmedicas.model.AppUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("appUser/login/")
    Call<AppUser>login(@Body AppUser appUser);
}
