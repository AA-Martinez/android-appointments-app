package com.example.consultasmedicas.utils.ApiMedic;

import com.example.consultasmedicas.model.AppUser.AppUserDto;
import com.example.consultasmedicas.model.Diagnosis.Diagnosis;
import com.example.consultasmedicas.model.Diagnosis.DiagnosisDAO;
import com.example.consultasmedicas.model.Patient.Patient;
import com.example.consultasmedicas.model.Symptom.Symptom;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiMedicService {
    @POST("/login")
    Call<ResponseBody> loginApiMedic(@Header("Authorization") String authorizationApiMedic);

    @GET("/symptoms")
    Call<List<Symptom>> getAllSymptoms(@Query("token") String token, @Query("format") String format, @Query("language") String language);

    @GET("/diagnosis")
    Call<List<DiagnosisDAO>> getDiagnosis(@Query("token") String token, @Query("format") String format, @Query("language") String language, @Query("Gender") String gender, @Query("year_of_birth") String year, @Query("symptoms") String symptoms);

    @GET("/symptoms")
    Call<List<Symptom>> getSymptomsByIds(@Query("token") String token, @Query("format") String format, @Query("language") String language, @Query("symptoms") String symptoms);
}
