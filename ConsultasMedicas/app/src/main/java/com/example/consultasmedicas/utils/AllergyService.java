package com.example.consultasmedicas.utils;

import com.example.consultasmedicas.model.Allergy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AllergyService {

    @GET()
    Call<List<Allergy>> getAllergies();
}
