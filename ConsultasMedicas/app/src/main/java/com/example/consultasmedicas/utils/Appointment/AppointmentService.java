package com.example.consultasmedicas.utils.Appointment;

import com.example.consultasmedicas.model.Appointment.Appointment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppointmentService {
    @POST("/appointment")
    Call<ResponseBody> createAppointment (@Body Appointment appointment, @Header("Authorization") String authHeader);

    @GET("/appointment/patient/{patientId}")
    Call<ResponseBody> getAppointmentList(@Path("patientId") String patientId, @Header("Authorization") String authHeader);
}
