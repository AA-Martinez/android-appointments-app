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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Disease.Disease;
import com.example.consultasmedicas.model.Medication.Medication;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.model.UpdateResponse.UpdateResponse;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Medication.MedicationService;
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

public class MedicationsFragment extends Fragment {

    MedicationService medicationService = Apis.medicationService();
    PatientService patientService = Apis.patientService();

    FloatingActionButton fabAddNewMedication;
    ExtendedFloatingActionButton fabSaveMedications;
    AutoCompleteTextView actMedicationList;
    ListView lvSelectedMedications;
    MaterialToolbar materialToolbar;

    public void initComponents(View view){
        fabAddNewMedication = view.findViewById(R.id.fabAddConfig);
        fabSaveMedications = view.findViewById(R.id.fabSaveConfig);
        actMedicationList = view.findViewById(R.id.actSearcherConfig);
        lvSelectedMedications = view.findViewById(R.id.lvSelectedItemsConfig);
        materialToolbar = view.findViewById(R.id.topAppBarConfig);
        materialToolbar.setTitle("Medicaciones");
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_config_fragment, container, false);

        List<Medication> medications = new ArrayList<Medication>();
        List<Medication> selectedMedications = new ArrayList<Medication>();

        initComponents(view);

        getListOfMedications(view, medications);

        ArrayAdapter<Medication> medicationArrayAdapter = new ArrayAdapter<Medication>(view.getContext(), android.R.layout.simple_list_item_1, medications);
        actMedicationList.setAdapter(medicationArrayAdapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, selectedMedications);
        lvSelectedMedications.setAdapter(arrayAdapter);
        getSelectedAppUserConfig(view, selectedMedications, arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        fabAddNewMedication.setOnClickListener(new View.OnClickListener() {
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
                        createMedication(view, etAddName.getText(), etAddDescription.getText(), medicationArrayAdapter, medications);
                        medications.add(new Medication(etAddName.getText().toString(), etAddDescription.getText().toString()));
                        ArrayAdapter<Medication> medicationArrayAdapter = new ArrayAdapter<Medication>(view.getContext(), android.R.layout.simple_list_item_1, medications);
                        actMedicationList.setAdapter(medicationArrayAdapter);
                        medicationArrayAdapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        medicationArrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                medicationArrayAdapter.notifyDataSetChanged();
            }
        });

        fabSaveMedications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveListOfSelectedConfig(view, selectedMedications);
                Toast.makeText(view.getContext(), "Se guardaron las configuraciones", Toast.LENGTH_SHORT).show();
            }
        });

        lvSelectedMedications.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Medication medication = (Medication) adapterView.getItemAtPosition(i);
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View subView = inflater.inflate(R.layout.confirmation_message_layout, null);
                Log.v("long-clicked", medication.getName()+ " ID: " + medication.getId());
                TextView tvConfirmation = (TextView) subView.findViewById(R.id.confirmation_message);
                tvConfirmation.setText("¿Esta seguro de borrar " + medication.getName() + " de la lista?");
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(subView);
                builder.setTitle("Mensaje de confirmacion");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<UpdateResponse> updateResponseUpdateResponse = new ArrayList<>();
                        UpdateResponse updateResponse = new UpdateResponse();
                        updateResponse.setId(medication.getId());
                        updateResponseUpdateResponse.add(updateResponse);

                        Call<ResponseBody> call = medicationService.deleteMedicationToPatient(SharedPreferencesUtils.RetrieveIntDataFromSharedPreferences("patient_id", view), updateResponseUpdateResponse, SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.e("Test","Code: " + response.code());
                                selectedMedications.remove(medication);
                                arrayAdapter.notifyDataSetChanged();
                                updateResponseUpdateResponse.clear();
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("ERROR", t.getMessage());
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        medicationArrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                medicationArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

        return view;
    }

    private void getSelectedAppUserConfig(View view, List<Medication> selectedMedications, ArrayAdapter arrayAdapter){
        Call<ResponseBody> call = patientService.getPatient(String.valueOf(SharedPreferencesUtils.RetrieveIntDataFromSharedPreferences("appUserId", view)),(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token",view)));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        PatientDAO patientDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), PatientDAO.class);
                        if (patientDAO.getMedications().size() == 0){
                            Toast.makeText(view.getContext(), "Sin medicacion", Toast.LENGTH_SHORT).show();
                        }else{
                            for (int i = 0; i < patientDAO.getMedications().size(); i++){
                                selectedMedications.add(patientDAO.getMedications().get(i));
                                arrayAdapter.notifyDataSetChanged();
                                Log.e("All", String.valueOf(patientDAO.getMedications().get(i).getName()));
                            }
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }

                actMedicationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Medication medication = (Medication) adapterView.getItemAtPosition(i);
                        Boolean aBoolean = false;
                        /*for (Allergy allergy1: selectedAllergies
                             ) {
                            Log.e("TEST", allergy1.getName());
                        }*/
                        Log.e("TEST", medication.getName());

                        if (selectedMedications.isEmpty()){
                            Log.e("TEST", "dentro de vacio");
                            selectedMedications.add(medication);
                            arrayAdapter.notifyDataSetChanged();
                        }else{

                            for (Medication medicationOnList: selectedMedications
                            ) {
                                if (medicationOnList.getName().equals(medication.getName())){
                                    Log.e("TEST", "dentro de existe");
                                    Toast.makeText(view.getContext(), "La medicacion ya se encuentra en la lista", Toast.LENGTH_SHORT).show();
                                    aBoolean = true;
                                }
                            }
                            if(!aBoolean){
                                Log.e("TEST", "dentro de no existe");
                                selectedMedications.add(medication);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                        actMedicationList.setText("");
                        Log.e("OTCSYM", medication.getName()+ " " + medication.getId());
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", "onFailure: "+t.getMessage());

            }
        });

    }

    private void saveListOfSelectedConfig(View view, List<Medication> selectedMedications){
        List<UpdateResponse> updateResponses = new ArrayList<>();
        for (Medication medication: selectedMedications
        ) {
            UpdateResponse updateResponse = new UpdateResponse();
            updateResponse.setId(medication.getId());
            updateResponses.add(updateResponse);

            Call<ResponseBody> call = medicationService.addMedicationToPatient(SharedPreferencesUtils.RetrieveIntDataFromSharedPreferences("patient_id", view), updateResponses, SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
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


    }

    private void getListOfMedications(View view, List<Medication> medications){
        Call<ResponseBody> call = medicationService.getMedications(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
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
                            Medication medication = gson.fromJson(object.toString(), Medication.class);
                            medications.add(medication);
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


    private void createMedication(View view, Editable name, Editable description, ArrayAdapter<Medication> medicationArrayAdapter, List<Medication> medications){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = medicationService.createMedication(new Medication(name.toString(),description.toString()), sharedPreferences.getString("auth-token", ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                medicationArrayAdapter.notifyDataSetChanged();
                medications.clear();
                getListOfMedications(view, medications);
                medicationArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

}
