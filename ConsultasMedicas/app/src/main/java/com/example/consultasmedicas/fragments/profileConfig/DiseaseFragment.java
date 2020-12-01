package com.example.consultasmedicas.fragments.profileConfig;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Disease.Disease;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.model.UpdateResponse.UpdateResponse;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Disease.DiseaseService;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class DiseaseFragment extends Fragment {

    DiseaseService diseaseService = Apis.diseaseService();
    PatientService patientService = Apis.patientService();

    FloatingActionButton fabAddNewDisease;
    ExtendedFloatingActionButton fabSaveDiseases;
    AutoCompleteTextView actDiseaseList;
    ListView lvSelectedDiseases;
    MaterialToolbar materialToolbar;

    public void initComponents(View view){
        fabAddNewDisease = view.findViewById(R.id.fabAddConfig);
        fabSaveDiseases = view.findViewById(R.id.fabSaveConfig);
        actDiseaseList = view.findViewById(R.id.actSearcherConfig);
        lvSelectedDiseases = view.findViewById(R.id.lvSelectedItemsConfig);
        materialToolbar = view.findViewById(R.id.topAppBarConfig);
        materialToolbar.setTitle("Enfermedades base");
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_config_fragment, container, false);

        List<Disease> diseases = new ArrayList<Disease>();
        List<Disease> selectedDiseases = new ArrayList<Disease>();

        initComponents(view);

        getListOfDiseases(view, diseases);

        ArrayAdapter<Disease> diseaseArrayAdapter = new ArrayAdapter<Disease>(view.getContext(), android.R.layout.simple_list_item_1, diseases);
        actDiseaseList.setAdapter(diseaseArrayAdapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, selectedDiseases);
        lvSelectedDiseases.setAdapter(arrayAdapter);
        getSelectedAppUserConfig(view, selectedDiseases, arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        actDiseaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Disease disease = (Disease) adapterView.getItemAtPosition(i);
                if (selectedDiseases.isEmpty()){
                    selectedDiseases.add(disease);
                    arrayAdapter.notifyDataSetChanged();
                }else{
                    if (selectedDiseases.contains(disease)){
                        Toast.makeText(view.getContext(), "La enfermedad base ya se encuentra en la lista", Toast.LENGTH_SHORT).show();
                    }else{
                        selectedDiseases.add(disease);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
                actDiseaseList.setText("");
                Log.e("OTCSYM", disease.getName()+ " " + disease.getId());
            }
        });

        fabAddNewDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View subView = inflater.inflate(R.layout.add_layout, null);

                final EditText etAddName = (EditText)subView.findViewById(R.id.add_name);
                final EditText etAddDescription = (EditText)subView.findViewById(R.id.add_description);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(subView);
                builder.setTitle("Nueva");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createDisease(view, etAddName.getText(), etAddDescription.getText(), diseaseArrayAdapter);
                        diseases.add(new Disease(etAddName.getText().toString(), etAddDescription.getText().toString()));
                        ArrayAdapter<Disease> diseaseArrayAdapter = new ArrayAdapter<Disease>(view.getContext(), android.R.layout.simple_list_item_1, diseases);
                        actDiseaseList.setAdapter(diseaseArrayAdapter);
                        diseaseArrayAdapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        diseaseArrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                diseaseArrayAdapter.notifyDataSetChanged();
            }
        });

        fabSaveDiseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveListOfSelectedConfig(view, selectedDiseases);
                Toast.makeText(view.getContext(), "Se guardaron las configuraciones", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void getSelectedAppUserConfig(View view, List<Disease> selectedDiseases, ArrayAdapter arrayAdapter){
        Call<ResponseBody> call = patientService.getPatient("1",(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token",view)));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        PatientDAO patientDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), PatientDAO.class);
                        if (patientDAO.getDiseases().size() == 0){
                            Toast.makeText(view.getContext(), "Sin enfermedades base", Toast.LENGTH_SHORT).show();
                        }else{
                            for (int i = 0; i < patientDAO.getDiseases().size(); i++){
                                selectedDiseases.add(patientDAO.getDiseases().get(i));
                                arrayAdapter.notifyDataSetChanged();
                                Log.e("All", String.valueOf(patientDAO.getAllergies().get(i).getName()));
                            }
                        }

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

    private void saveListOfSelectedConfig(View view, List<Disease> selectedDiseases){
        List<UpdateResponse> updateResponses = new ArrayList<>();
        for (Disease disease: selectedDiseases
        ) {
            UpdateResponse updateResponse = new UpdateResponse();
            updateResponse.setId(disease.getId());
            updateResponses.add(updateResponse);
        }

        Call<ResponseBody> call = diseaseService.addDiseaseToPatien(1, updateResponses, SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.e("SCC" , "Nashe");
                }
                Log.e("Fallo", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void getListOfDiseases(View view, List<Disease> diseases){
        Call<ResponseBody> call = diseaseService.getDiseases(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
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
                            Disease disease = gson.fromJson(object.toString(), Disease.class);
                            diseases.add(disease);
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


    private void createDisease(View view, Editable name, Editable description, ArrayAdapter<Disease> diseaseArrayAdapter){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = diseaseService.createDisease(new Disease(name.toString(),description.toString()), sharedPreferences.getString("auth-token", ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                diseaseArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }




}
