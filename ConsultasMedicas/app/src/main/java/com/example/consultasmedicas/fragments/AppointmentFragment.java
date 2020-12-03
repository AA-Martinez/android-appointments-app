package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Appointment.AppointmentDAO;
import com.example.consultasmedicas.model.Degree.DegreeDAO;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Appointment.AppointmentAdapter;
import com.example.consultasmedicas.utils.Appointment.AppointmentService;
import com.example.consultasmedicas.utils.Degree.DegreeAdapter;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFragment extends Fragment implements AppointmentAdapter.OnNoteListener {
    RecyclerView rvAppointmentsList;
    AppointmentAdapter appointmentAdapter;
    List<AppointmentDAO> appointments;
    AppointmentService appointmentService = Apis.appointmentService();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appointments_fragment,container,false);

        rvAppointmentsList = view.findViewById(R.id.rvAppointmentList);
        rvAppointmentsList.setLayoutManager(new GridLayoutManager(view.getContext(),1));

        appointments = new ArrayList<>();
        getAppointmentsList(view);
        appointmentAdapter = new AppointmentAdapter(view.getContext(),appointments,this);
        rvAppointmentsList.setAdapter(appointmentAdapter);
        return view;
    }

    private void getAppointmentsList(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = appointmentService.getAppointmentList(String.valueOf(SharedPreferencesUtils.RetrieveIntDataFromSharedPreferences("patient_id", view)),sharedPreferences.getString("auth-token", ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0; i<jsonArray.length(); i++){
                            Gson gson = new Gson();
                            AppointmentDAO appointmentDAO = gson.fromJson(jsonArray.getJSONObject(i).toString(),AppointmentDAO.class);
                            appointments.add(appointmentDAO);
                            appointmentAdapter.notifyDataSetChanged();
                            //Log.d("APPOINTMENT", i+" "+appointmentDAO.getId());
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Log.d("TAG", "onNoteClick: " + position);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id_appointment", appointments.get(position).getId());
        editor.apply();

        Log.e("APPOINTMENTID", String.valueOf(sharedPreferences.getInt("id_appointment", 0)));

        ((Navigation) getActivity()).navigateTo(new ChatFragment(), true);

    }
}
