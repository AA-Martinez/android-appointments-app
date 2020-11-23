package com.example.consultasmedicas.fragments;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Diagnosis.DiagnosisDAO;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.model.Symptom.Symptom;
import com.example.consultasmedicas.utils.ApiMedic.ApiMedicService;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Diagnosis.DiagnosisItemListAdapter;
import com.example.consultasmedicas.utils.Doctor.DoctorAdapter;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
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

public class QuickAppointmentFragment extends Fragment {

    ListView lvSymptoms;
    TextView tvDiagnosisName, tvDiagnosisGender, tvDiagnosisYear, tvDiagnosisAccuracy;

    String gender, year;

    RecyclerView rvDiagnosis;
    List<DiagnosisDAO> diagnosisDAOS = new ArrayList<>();
    DiagnosisItemListAdapter diagnosisItemListAdapter;

    PatientService patientService = Apis.patientService();
    ApiMedicService apiMedicService = Apis.apiMedicServiceData();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quick_appointment_fragment, container, false);
        initComponents(view);
        setPatientData(view);
        rvDiagnosis.getRecycledViewPool().setMaxRecycledViews(0, 0);


        rvDiagnosis.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        diagnosisItemListAdapter = new DiagnosisItemListAdapter(view.getContext(), diagnosisDAOS);
        rvDiagnosis.setAdapter(diagnosisItemListAdapter);

        return view;
    }

    public void initComponents(View view){
        lvSymptoms = view.findViewById(R.id.lvAppUserSymptoms);
        tvDiagnosisGender = view.findViewById(R.id.tvDiagnosisGender);
        tvDiagnosisYear = view.findViewById(R.id.tvDiagnosisYear);
        tvDiagnosisName = view.findViewById(R.id.tvDiagnosisName);
        tvDiagnosisAccuracy = view.findViewById(R.id.tvDiagnosisAccuracy);
        rvDiagnosis = view.findViewById(R.id.rvDiagnosis);
        List<Symptom> selectedSymptoms = new ArrayList<Symptom>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, selectedSymptoms);
        getSymptoms(selectedSymptoms, view, arrayAdapter);
        lvSymptoms.setAdapter(arrayAdapter);
    }

    public void setPatientData(View view){
        Call<ResponseBody> call = patientService.getPatient("1",(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token",view)));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        PatientDAO patientDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), PatientDAO.class);
                        tvDiagnosisName.setText("Nombre: "+patientDAO.getAppUser().getFirstName() + " " + patientDAO.getAppUser().getLastName());

                        String[] parts = patientDAO.getAppUser().getBirthDate().split(" ");


                        if (patientDAO.getAppUser().getGenre().equals("Masculino")){
                            gender = "male";
                        }else if (patientDAO.getAppUser().getGenre().equals("Femenino")){
                            gender = "female";
                        }

                        String[] parts2 = parts[0].split("-");
                        year = parts2[0];

                        tvDiagnosisGender.setText("Genero: "+patientDAO.getAppUser().getGenre());
                        tvDiagnosisYear.setText("Año de nacimiento: "+parts[0]);

                        getDiagnosis(gender, year, view);


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", "onFailure: "+t.getMessage());

            }
        });
    }

    public void getDiagnosis(String gender, String year, View view){
        Call<List<DiagnosisDAO>> call = apiMedicService.getDiagnosis(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("apiMedicToken", view), "json","es-es", gender, year, SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("symptoms-appuser",view));
        call.enqueue(new Callback<List<DiagnosisDAO>>() {
            @Override
            public void onResponse(Call<List<DiagnosisDAO>> call, Response<List<DiagnosisDAO>> response) {
                for (DiagnosisDAO diagnosisDAO: response.body()
                     ) {
                    Log.e("prueba", diagnosisDAO.getIssue().getName());
                }
                diagnosisDAOS.addAll(response.body());
                diagnosisItemListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DiagnosisDAO>> call, Throwable t) {
                Log.e("ERRORRR", t.getMessage());

            }
        });

    }

    public void getSymptoms(List<Symptom> symptoms, View view, ArrayAdapter arrayAdapter){
        Call<List<Symptom>> listCall = apiMedicService.getSymptomsByIds(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("apiMedicToken", view), "json", "es-es", SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("symptoms-appuser", view));
        listCall.enqueue(new Callback<List<Symptom>>() {
            @Override
            public void onResponse(Call<List<Symptom>> call, Response<List<Symptom>> response) {
                symptoms.addAll(response.body());
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Symptom>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());

            }
        });
    }

}
