package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.AppUser.AppUser;
import com.example.consultasmedicas.model.Doctor.Doctor;
import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.model.RequestResponse.RequestResponse;
import com.example.consultasmedicas.utils.Allergy.AllergyAdapter;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Doctor.DoctorAdapter;
import com.example.consultasmedicas.utils.Doctor.DoctorService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorFragment extends Fragment implements DoctorAdapter.OnNoteListener {

    RecyclerView rvDoctorList;
    DoctorAdapter doctorAdapter;
    List<DoctorDAO> doctors;
    DoctorService doctorService = Apis.doctorService();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doctors_fragment, container, false);

        rvDoctorList = view.findViewById(R.id.rvDoctorList);
        rvDoctorList.setLayoutManager(new GridLayoutManager(view.getContext(), 1));

        doctors = new ArrayList<>();
        getDoctorsList(view);

        doctorAdapter = new DoctorAdapter(view.getContext(), doctors, this);
        rvDoctorList.setAdapter(doctorAdapter);

        return view;
    }

    private void getDoctorsList(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = doctorService.getDoctorList(sharedPreferences.getString("auth-token", ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            DoctorDAO doctorDAO = gson.fromJson(object.toString(), DoctorDAO.class);
                            for (int j = 0; j < doctorDAO.getDegrees().size(); j++){
                                if (doctorDAO.getDegrees().get(j).getSpecialty().equals(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("selected_speciality", view))){
                                    doctors.add(doctorDAO);
                                    doctorAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ADAO", t.getMessage());
            }
        });

    }

    @Override
    public void onNoteClick(int position) {
        Log.d("TAG", "onNoteClick: " + position);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id_doctor", doctors.get(position).getAppUser().getId());
        editor.apply();
        ((Navigation) getActivity()).navigateTo(new DoctorPersonalInformationFragment(), true);

    }
}
