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
import com.example.consultasmedicas.model.Medication.Medication;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.model.Substance.Substance;
import com.example.consultasmedicas.model.UpdateResponse.UpdateResponse;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.example.consultasmedicas.utils.Substance.SubstanceService;
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

public class SubstancesFragment extends Fragment {

    SubstanceService substanceService = Apis.substanceService();
    PatientService patientService = Apis.patientService();

    FloatingActionButton fabAddNewSubstance;
    ExtendedFloatingActionButton fabSaveSubstances;
    AutoCompleteTextView actSubstanceList;
    ListView lvSelectedSubstances;
    MaterialToolbar materialToolbar;

    public void initComponents(View view){
        fabAddNewSubstance = view.findViewById(R.id.fabAddConfig);
        fabSaveSubstances = view.findViewById(R.id.fabSaveConfig);
        actSubstanceList = view.findViewById(R.id.actSearcherConfig);
        lvSelectedSubstances = view.findViewById(R.id.lvSelectedItemsConfig);
        materialToolbar = view.findViewById(R.id.topAppBarConfig);
        materialToolbar.setTitle("Sustancias");
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_config_fragment, container, false);

        List<Substance> substances = new ArrayList<Substance>();
        List<Substance> selectedSubstances = new ArrayList<Substance>();

        initComponents(view);

        getListOfSubstances(view, substances);

        ArrayAdapter<Substance> substanceArrayAdapter = new ArrayAdapter<Substance>(view.getContext(), android.R.layout.simple_list_item_1, substances);
        actSubstanceList.setAdapter(substanceArrayAdapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, selectedSubstances);
        lvSelectedSubstances.setAdapter(arrayAdapter);
        getSelectedAppUserConfig(view, selectedSubstances, arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        actSubstanceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Substance substance = (Substance) adapterView.getItemAtPosition(i);
                if (selectedSubstances.isEmpty()){
                    selectedSubstances.add(substance);
                    arrayAdapter.notifyDataSetChanged();
                }else{
                    if (selectedSubstances.contains(substance)){
                        Toast.makeText(view.getContext(), "La sustancia ya se encuentra en la lista", Toast.LENGTH_SHORT).show();
                    }else{
                        selectedSubstances.add(substance);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
                actSubstanceList.setText("");
                Log.e("OTCSYM", substance.getName()+ " " + substance.getId());
            }
        });

        fabAddNewSubstance.setOnClickListener(new View.OnClickListener() {
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
                        createSubstance(view, etAddName.getText(), etAddDescription.getText(), substanceArrayAdapter, substances);
                        substances.add(new Substance(etAddName.getText().toString(), etAddDescription.getText().toString()));
                        ArrayAdapter<Substance> substanceArrayAdapter = new ArrayAdapter<Substance>(view.getContext(), android.R.layout.simple_list_item_1, substances);
                        actSubstanceList.setAdapter(substanceArrayAdapter);
                        substanceArrayAdapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        substanceArrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                substanceArrayAdapter.notifyDataSetChanged();
            }
        });

        fabSaveSubstances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveListOfSelectedConfig(view, selectedSubstances);
                Toast.makeText(view.getContext(), "Se guardaron las configuraciones", Toast.LENGTH_SHORT).show();
            }
        });

        lvSelectedSubstances.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Substance substance = (Substance) adapterView.getItemAtPosition(i);
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View subView = inflater.inflate(R.layout.confirmation_message_layout, null);
                Log.v("long-clicked", substance.getName()+ " ID: " + substance.getId());
                TextView tvConfirmation = (TextView) subView.findViewById(R.id.confirmation_message);
                tvConfirmation.setText("¿Esta seguro de borrar " + substance.getName() + " de la lista?");
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(subView);
                builder.setTitle("Mensaje de confirmacion");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<UpdateResponse> updateResponseUpdateResponse = new ArrayList<>();
                        UpdateResponse updateResponse = new UpdateResponse();
                        updateResponse.setId(substance.getId());
                        updateResponseUpdateResponse.add(updateResponse);

                        Call<ResponseBody> call = substanceService.deleteSubstanceToPatient(1, updateResponseUpdateResponse, SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.e("Test","Code: " + response.code());
                                selectedSubstances.remove(substance);
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
                        substanceArrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                substanceArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return view;
    }

    private void getSelectedAppUserConfig(View view, List<Substance> selectedSubstances, ArrayAdapter arrayAdapter){
        Call<ResponseBody> call = patientService.getPatient("1",(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token",view)));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        PatientDAO patientDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), PatientDAO.class);
                        if (patientDAO.getSubstances().size() == 0){
                            Toast.makeText(view.getContext(), "Sin substancias", Toast.LENGTH_SHORT).show();
                        }else{
                            for (int i = 0; i < patientDAO.getSubstances().size(); i++){
                                selectedSubstances.add(patientDAO.getSubstances().get(i));
                                arrayAdapter.notifyDataSetChanged();
                                Log.e("All", String.valueOf(patientDAO.getSubstances().get(i).getName()));
                            }
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }

                actSubstanceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Substance substance = (Substance) adapterView.getItemAtPosition(i);
                        Boolean aBoolean = false;
                        /*for (Allergy allergy1: selectedAllergies
                             ) {
                            Log.e("TEST", allergy1.getName());
                        }*/
                        Log.e("TEST", substance.getName());

                        if (selectedSubstances.isEmpty()){
                            Log.e("TEST", "dentro de vacio");
                            selectedSubstances.add(substance);
                            arrayAdapter.notifyDataSetChanged();
                        }else{

                            for (Substance substanceOnList: selectedSubstances
                            ) {
                                if (substanceOnList.getName().equals(substance.getName())){
                                    Log.e("TEST", "dentro de existe");
                                    Toast.makeText(view.getContext(), "La medicacion ya se encuentra en la lista", Toast.LENGTH_SHORT).show();
                                    aBoolean = true;
                                }
                            }
                            if(!aBoolean){
                                Log.e("TEST", "dentro de no existe");
                                selectedSubstances.add(substance);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                        actSubstanceList.setText("");
                        Log.e("OTCSYM", substance.getName()+ " " + substance.getId());
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", "onFailure: "+t.getMessage());

            }
        });

    }

    private void saveListOfSelectedConfig(View view, List<Substance> selectedSubstances){
        List<UpdateResponse> updateResponses = new ArrayList<>();
        for (Substance substance: selectedSubstances
        ) {
            UpdateResponse updateResponse = new UpdateResponse();
            updateResponse.setId(substance.getId());
            updateResponses.add(updateResponse);

            Call<ResponseBody> call = substanceService.addSubstanceToPatient(1, updateResponses, SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
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

    private void getListOfSubstances(View view, List<Substance> substances){
        Call<ResponseBody> call = substanceService.getSubstance(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
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
                            Substance substance = gson.fromJson(object.toString(), Substance.class);
                            substances.add(substance);
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


    private void createSubstance(View view, Editable name, Editable description, ArrayAdapter<Substance> substanceArrayAdapter, List<Substance> substances){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = substanceService.createSubstance(new Substance(name.toString(),description.toString()), sharedPreferences.getString("auth-token", ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                substanceArrayAdapter.notifyDataSetChanged();
                substances.clear();
                getListOfSubstances(view, substances);
                substanceArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}
