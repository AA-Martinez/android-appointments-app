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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Degree.Degree;
import com.example.consultasmedicas.model.Degree.DegreeDAO;
import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Degree.DegreeInfoAdapter;
import com.example.consultasmedicas.utils.Doctor.DoctorService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPersonalInformationFragment extends Fragment {

    DoctorService doctorService = Apis.doctorService();
    TextView tvProfileName, tvDoctorSpeciality, tvProfileEmail, tvProfileCity, tvProfilePhone, tvCreationTime;
    RecyclerView rvDegrees;
    SharedPreferences sharedPreferences;
    DegreeInfoAdapter degreeInfoAdapter;
    List<DegreeDAO> degreeDAOS;
    MaterialButton mbSeeMore, mbCreateAppointment;



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doctor_personal_information, container, false);

        tvDoctorSpeciality = view.findViewById(R.id.tvSpecialtyDoctor);
        tvProfileName = view.findViewById(R.id.tvDoctorNameGeneralInfo);
        tvProfileEmail = view.findViewById(R.id.emailDoctorInfo);
        tvProfilePhone = view.findViewById(R.id.phoneDoctorInfo);
        tvProfileCity = view.findViewById(R.id.cityDoctorInfo);
        tvCreationTime = view.findViewById(R.id.creationStampDoctorInfo);
        rvDegrees = view.findViewById(R.id.rvSpecialities);

        degreeDAOS = new ArrayList<>();
        degreeInfoAdapter = new DegreeInfoAdapter(view.getContext(),degreeDAOS);
        rvDegrees.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        rvDegrees.setAdapter(degreeInfoAdapter);

        mbSeeMore = view.findViewById(R.id.mbSeeMore);
        mbCreateAppointment = view.findViewById(R.id.btCreateAppointment);

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
                        tvProfileName.setText("Dr. " + doctorDAO.getAppUser().getFirstName() + " " + doctorDAO.getAppUser().getLastName());
                        tvDoctorSpeciality.setText(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("selected_speciality", view));
                        tvProfileEmail.setText("Correo: " +doctorDAO.getAppUser().getEmail());
                        tvProfileCity.setText("Ciudad: " + doctorDAO.getAppUser().getCity());
                        tvProfilePhone.setText("Celular: " +doctorDAO.getAppUser().getPhone());
                        tvCreationTime.setText("Registrado desde: " +doctorDAO.getAppUser().getCreationTimeStamp());

                        for (int i = 0; i < doctorDAO.getDegrees().size(); i++){
                            degreeDAOS.add(doctorDAO.getDegrees().get(i));
                        }

                        degreeInfoAdapter.notifyDataSetChanged();

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

        mbSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Navigation) getActivity()).navigateTo(new DoctorMorePersonalInfo(), true);
            }
        });

        mbCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Navigation) getActivity()).navigateTo(new CreateAppointmentFragment(), true);
                SharedPreferencesUtils.SaveIntDataToSharedPreferences("ComeFromPersonaInfo", 1, view);
            }
        });

        return view;
    }
}
