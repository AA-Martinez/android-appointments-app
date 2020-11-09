package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Doctor.DoctorService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPersonalInformationFragment extends Fragment {

    DoctorService doctorService = Apis.doctorService();
    TextView tvProfileName, tvProfileCI, tvProfileEmail, tvProfileAddress, tvProfileCity, tvProfilePhone;
    SharedPreferences sharedPreferences;



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doctor_personal_information, container, false);

        tvProfileName = view.findViewById(R.id.profile_doctor_name);
        tvProfileCI = view.findViewById(R.id.profile_doctor_Ci);
        tvProfileEmail = view.findViewById(R.id.profile_doctor_email);
        tvProfileAddress = view.findViewById(R.id.profile_doctor_address);
        tvProfilePhone = view.findViewById(R.id.profile_doctor_phone);
        tvProfileCity = view.findViewById(R.id.profile_doctor_city);

        sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = doctorService.getDoctor(sharedPreferences.getInt("id_doctor", 0), sharedPreferences.getString("auth-token",""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        DoctorDAO doctorDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), DoctorDAO.class);
                        Log.d("RESPONSE", "onResponse: "+doctorDAO.getAppUser().getFirstName()+" "+doctorDAO.getAppUser().getLastName());
                        tvProfileName.setText(doctorDAO.getAppUser().getFirstName() + " " + doctorDAO.getAppUser().getLastName());
                        tvProfileCI.setText(doctorDAO.getAppUser().getCi());
                        tvProfileEmail.setText(doctorDAO.getAppUser().getEmail());
                        tvProfileAddress.setText(doctorDAO.getAppUser().getAddress());
                        tvProfileCity.setText(doctorDAO.getAppUser().getCity());
                        tvProfilePhone.setText(doctorDAO.getAppUser().getPhone());

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", t.getMessage());

            }
        });

        return view;
    }
}
