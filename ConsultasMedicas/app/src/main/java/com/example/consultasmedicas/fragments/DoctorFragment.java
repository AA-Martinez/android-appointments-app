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
import com.example.consultasmedicas.utils.Allergy.AllergyAdapter;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Doctor.DoctorAdapter;
import com.example.consultasmedicas.utils.Doctor.DoctorService;

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

                            DoctorDAO doctorDAO = new DoctorDAO();
                            // AppUser for Doctor
                            AppUser appUser = new AppUser();
                            appUser.setAddress(object.getJSONObject("appUser").getString("address"));
                            appUser.setBirthCountry(object.getJSONObject("appUser").getString("birthCountry"));
                            appUser.setBirthDate(object.getJSONObject("appUser").getString("birthDate"));
                            appUser.setCi(object.getJSONObject("appUser").getString("ci"));
                            appUser.setCity(object.getJSONObject("appUser").getString("city"));
                            appUser.setCreationTimeStamp(object.getJSONObject("appUser").getString("creationTimeStamp"));
                            appUser.setEmail(object.getJSONObject("appUser").getString("email"));
                            appUser.setFirstName(object.getJSONObject("appUser").getString("firstName"));
                            appUser.setGenre(object.getJSONObject("appUser").getString("genre"));
                            appUser.setId(object.getJSONObject("appUser").getInt("id"));
                            appUser.setLastName(object.getJSONObject("appUser").getString("lastName"));
                            appUser.setPhone(object.getJSONObject("appUser").getString("phone"));
                            appUser.setUsername(object.getJSONObject("appUser").getString("username"));

                            // DoctorDao

                            doctorDAO.setAppUser(appUser);
                            doctorDAO.setAppointments(null);
                            doctorDAO.setDegrees(null);
                            doctorDAO.setJobs(null);
                            doctorDAO.setId(object.getInt("id"));

                            // Doctor List

                            doctors.add(doctorDAO);
                            doctorAdapter.notifyDataSetChanged();
                            Log.e("aaa", object.getJSONObject("appUser").getString("firstName"));
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
