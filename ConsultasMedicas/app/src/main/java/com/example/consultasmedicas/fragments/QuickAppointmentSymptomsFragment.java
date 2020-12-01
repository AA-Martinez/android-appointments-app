package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Symptom.Symptom;
import com.example.consultasmedicas.utils.ApiMedic.ApiMedicService;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuickAppointmentSymptomsFragment extends Fragment {

    public static String TAG = "ERROR";
    ApiMedicService apiMedicService = Apis.apiMedicServiceData();

    ExtendedFloatingActionButton fabConsult;
    AutoCompleteTextView actEditText;
    ListView lvSelectedSymptoms;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quick_appointment_symptoms, container, false);


        List<Symptom> symptoms = new ArrayList<Symptom>();
        List<Symptom> selectedSymptoms = new ArrayList<Symptom>();
        Call<List<Symptom>> listCall = apiMedicService.getAllSymptoms(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("apiMedicToken", view), "json", "es-es");
        listCall.enqueue(new Callback<List<Symptom>>() {
            @Override
            public void onResponse(Call<List<Symptom>> call, Response<List<Symptom>> response) {
                for (Symptom symptom: response.body()
                     ) {
                    if (!"".equals(symptom.getName())){
                        symptoms.add(symptom);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Symptom>> call, Throwable t) {
                Log.e(TAG, t.getMessage());

            }
        });

        initComponents(view);

        ArrayAdapter<Symptom> symptomArrayAdapter = new ArrayAdapter<Symptom>(view.getContext(), android.R.layout.simple_list_item_1, symptoms);
        actEditText.setAdapter(symptomArrayAdapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, selectedSymptoms);
        lvSelectedSymptoms.setAdapter(arrayAdapter);

        actEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Symptom symptom = (Symptom) adapterView.getItemAtPosition(i);
                if (selectedSymptoms.isEmpty()){
                    selectedSymptoms.add(symptom);
                    arrayAdapter.notifyDataSetChanged();
                }else{
                    if (selectedSymptoms.contains(symptom)){
                        Toast.makeText(view.getContext(), "El sintoma ya se encuentra en la lista", Toast.LENGTH_SHORT).show();
                    }else{
                        selectedSymptoms.add(symptom);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
                actEditText.setText("");
                Log.e("OTCSYM", symptom.getName()+ " " + symptom.getID());
            }
        });

        fabConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diagnosisSym = "[";
                int c = 0;

                for (Symptom symptom: selectedSymptoms
                     ) {
                    c++;
                    if (selectedSymptoms.size() == c){
                        diagnosisSym += symptom.getID();
                    }else{
                        diagnosisSym += symptom.getID() + ",";
                    }
                }

                diagnosisSym += "]";
                SharedPreferencesUtils.SaveStringDataToSharedPreferences("symptoms-appuser", diagnosisSym, view);

                ((Navigation) getActivity()).navigateTo(new QuickAppointmentFragment(), false);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //dialogBuilder.setNeutralButton()
    }

    public void initComponents(View view){
         actEditText = view.findViewById(R.id.actSearcherConfig);
         lvSelectedSymptoms = view.findViewById(R.id.lvSelectedItemsConfig);
         fabConsult = view.findViewById(R.id.fabSaveConfig);
    }
}
