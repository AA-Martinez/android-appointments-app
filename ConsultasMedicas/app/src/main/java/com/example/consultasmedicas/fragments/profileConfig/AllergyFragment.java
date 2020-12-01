package com.example.consultasmedicas.fragments.profileConfig;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.fragments.ProfileFragment;
import com.example.consultasmedicas.model.Allergy.Allergy;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.model.Symptom.Symptom;
import com.example.consultasmedicas.model.UpdateResponse.UpdateResponse;
import com.example.consultasmedicas.utils.Allergy.AllergyAdapter;
import com.example.consultasmedicas.utils.Allergy.AllergyService;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

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

public class AllergyFragment extends Fragment {

    AllergyService allergyService = Apis.allergyService();
    PatientService patientService = Apis.patientService();

    FloatingActionButton fabAddNewAllergy;
    ExtendedFloatingActionButton fabSaveAllergies;
    AutoCompleteTextView actAllergyList;
    ListView lvSelectedAllergies;
    MaterialToolbar materialToolbar;



    public void initComponents(View view){
        fabAddNewAllergy = view.findViewById(R.id.fabAddConfig);
        fabSaveAllergies = view.findViewById(R.id.fabSaveConfig);
        actAllergyList = view.findViewById(R.id.actSearcherConfig);
        lvSelectedAllergies = view.findViewById(R.id.lvSelectedItemsConfig);
        materialToolbar = view.findViewById(R.id.topAppBarConfig);
        materialToolbar.setTitle("Alergias");
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_config_fragment, container, false);

        List<Allergy> allergies = new ArrayList<Allergy>();
        List<Allergy> selectedAllergies = new ArrayList<>();

        initComponents(view);

        getListOfAllergies(view, allergies);

        ArrayAdapter<Allergy> allergyArrayAdapter = new ArrayAdapter<Allergy>(view.getContext(), android.R.layout.simple_list_item_1, allergies);
        actAllergyList.setAdapter(allergyArrayAdapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, selectedAllergies);
        lvSelectedAllergies.setAdapter(arrayAdapter);
        getSelectedAppUserConfig(view, selectedAllergies, arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        fabAddNewAllergy.setOnClickListener(new View.OnClickListener() {
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
                        createAllergy(view, etAddName.getText(), etAddDescription.getText(), allergyArrayAdapter);
                        allergies.add(new Allergy(etAddName.getText().toString(), etAddDescription.getText().toString()));
                        ArrayAdapter<Allergy> allergyArrayAdapter = new ArrayAdapter<Allergy>(view.getContext(), android.R.layout.simple_list_item_1, allergies);
                        actAllergyList.setAdapter(allergyArrayAdapter);
                        allergyArrayAdapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        allergyArrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                allergyArrayAdapter.notifyDataSetChanged();
            }
        });

        fabSaveAllergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveListOfSelectedConfig(view, selectedAllergies);
                Toast.makeText(view.getContext(), "Se guardaron las configuraciones", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void getSelectedAppUserConfig(View view, List<Allergy> selectedAllergies, ArrayAdapter arrayAdapter){
        //List<Allergy> pruebas = new ArrayList<>();
        Log.e("TEST", "Comienza la respuesta");
        Call<ResponseBody> call = patientService.getPatient("1",(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token",view)));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        PatientDAO patientDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), PatientDAO.class);
                        if (patientDAO.getAllergies().size() == 0){
                            Toast.makeText(view.getContext(), "Sin enfermedades base", Toast.LENGTH_SHORT).show();
                        }else{
                            for (int i = 0; i < patientDAO.getAllergies().size(); i++){
                                //pruebas.add(patientDAO.getAllergies().get(i));
                                selectedAllergies.add(patientDAO.getAllergies().get(i));
                                Log.e("TEST", "Se agrega datos");
                                arrayAdapter.notifyDataSetChanged();
                                //Log.e("All", String.valueOf(selectedAllergies.get(i).getName()));
                            }
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("TEST", "Termina la respuesta");

                actAllergyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Allergy allergy = (Allergy) adapterView.getItemAtPosition(i);
                        Boolean aBoolean = false;
                        /*for (Allergy allergy1: selectedAllergies
                             ) {
                            Log.e("TEST", allergy1.getName());
                        }*/
                        Log.e("TEST", allergy.getName());

                        if (selectedAllergies.isEmpty()){
                            Log.e("TEST", "dentro de vacio");
                            selectedAllergies.add(allergy);
                            arrayAdapter.notifyDataSetChanged();
                        }else{

                            for (Allergy allergyOnList: selectedAllergies
                                 ) {
                                if (allergyOnList.getName().equals(allergy.getName())){
                                    Log.e("TEST", "dentro de existe");
                                    Toast.makeText(view.getContext(), "La alergia ya se encuentra en la lista", Toast.LENGTH_SHORT).show();
                                    aBoolean = true;
                                }
                            }
                            if(!aBoolean){
                                Log.e("TEST", "dentro de no existe");
                                selectedAllergies.add(allergy);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                        actAllergyList.setText("");
                        Log.e("OTCSYM", allergy.getName()+ " " + allergy.getId());
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", "onFailure: "+t.getMessage());

            }
        });
    }

    private void saveListOfSelectedConfig(View view, List<Allergy> selectedAllergies){
        List<UpdateResponse> updateResponses = new ArrayList<>();
        for (Allergy allergyDAO: selectedAllergies
             ) {
            UpdateResponse updateResponse = new UpdateResponse();
            updateResponse.setId(allergyDAO.getId());
            updateResponses.add(updateResponse);

            Call<ResponseBody> call = allergyService.addAllergyToPatien(1, updateResponses, SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
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

    private void getListOfAllergies(View view, List<Allergy> allergies){
        Call<ResponseBody> call = allergyService.getAllergies(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
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
                            Allergy allergyDAO = gson.fromJson(object.toString(), Allergy.class);
                            allergies.add(allergyDAO);
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


    private void createAllergy(View view, Editable name, Editable description, ArrayAdapter<Allergy> allergyArrayAdapter){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = allergyService.createAllergy(new Allergy(name.toString(),description.toString()), sharedPreferences.getString("auth-token", ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                allergyArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }




}
