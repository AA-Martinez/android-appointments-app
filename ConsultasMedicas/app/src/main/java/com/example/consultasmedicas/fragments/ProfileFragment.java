package com.example.consultasmedicas.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.fragments.profileConfig.AllergyFragment;
import com.example.consultasmedicas.fragments.profileConfig.DiseaseFragment;
import com.example.consultasmedicas.fragments.profileConfig.GeneralInformationFragment;
import com.example.consultasmedicas.fragments.profileConfig.MedicationsFragment;
import com.example.consultasmedicas.fragments.profileConfig.SubstancesFragment;
import com.example.consultasmedicas.model.Patient.Patient;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.PATCH;

public class ProfileFragment extends Fragment{

    PatientService patientService = Apis.patientService();
    TextView tvProfileName, tvProfileCI, tvProfileEmail, tvProfileAddress, tvProfileGenre, tvProfileWeight, tvProfileHeight, tvProfileBirthDay, tvProfileCity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.profile_screen, container, false);

        // Profile Data

        tvProfileName = view.findViewById(R.id.profile_name);
        tvProfileCI = view.findViewById(R.id.profile_ci);
        tvProfileEmail = view.findViewById(R.id.profile_email);
        tvProfileAddress = view.findViewById(R.id.profile_address);
        tvProfileGenre = view.findViewById(R.id.profile_genre);
        tvProfileHeight = view.findViewById(R.id.profile_height);
        tvProfileWeight = view.findViewById(R.id.profile_weight);
        tvProfileBirthDay = view.findViewById(R.id.profile_birthDay);
        tvProfileCity = view.findViewById(R.id.profile_city);


        Call<ResponseBody> call = patientService.getPatient("1", SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        PatientDAO patientDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), PatientDAO.class);
                        Log.d("RESPONSE", "onResponse: "+patientDAO.getAppUser().getFirstName()+" "+patientDAO.getAppUser().getLastName());
                        tvProfileName.setText(patientDAO.getAppUser().getFirstName() + " " + patientDAO.getAppUser().getLastName());
                        tvProfileCI.setText(patientDAO.getAppUser().getCi());
                        tvProfileEmail.setText(patientDAO.getAppUser().getEmail());
                        tvProfileAddress.setText(patientDAO.getAppUser().getAddress());
                        tvProfileGenre.setText(patientDAO.getAppUser().getGenre());
                        tvProfileHeight.setText("Altura: " + patientDAO.getHeight());
                        tvProfileWeight.setText("Peso: "+ patientDAO.getWeight());
                        tvProfileBirthDay.setText("Fecha de nacimiento: " + patientDAO.getAppUser().getBirthDate());
                        tvProfileCity.setText(patientDAO.getAppUser().getCity());

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



        // Configuraciones

        final ListView listView = view.findViewById(R.id.profile_listview);

        String[] values = new String[]{
                "Informacion general", "Enfermedades", "Medicamentos", "Alergias", "Substancias"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    ((Navigation) getActivity()).navigateTo(new GeneralInformationFragment(), true);
                }else if (i == 1){
                    ((Navigation) getActivity()).navigateTo(new DiseaseFragment(), true);
                }else if (i == 2){
                    ((Navigation) getActivity()).navigateTo(new MedicationsFragment(), true);
                }else if (i == 3){
                    ((Navigation) getActivity()).navigateTo(new AllergyFragment(), true);
                }else if (i == 4){
                    ((Navigation) getActivity()).navigateTo(new SubstancesFragment(), true);
                }
            }
        });

        return view;
    }

}