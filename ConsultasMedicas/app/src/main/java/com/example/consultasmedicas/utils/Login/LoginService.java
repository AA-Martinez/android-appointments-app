package com.example.consultasmedicas.utils.Login;

import com.example.consultasmedicas.model.AppUser.AppUserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/login")
    Call<Void>login(@Body AppUserDto appUserDto);
}
