package com.example.consultasmedicas.fragments.profileConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.fragments.LoginFragment;
import com.example.consultasmedicas.fragments.ProfileFragment;
import com.example.consultasmedicas.model.AppUser.AppUserModel;
import com.example.consultasmedicas.model.Patient.Patient;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralInformationFragment extends Fragment {

    PatientService patientService = Apis.patientService();

    TextInputLayout tilFirstName, tilLastName, tilEmail, tilPhone, tilCity, tilWeight, tilHeight, tilAddress;
    TextInputEditText tietFirstName, tietLastName, tietEmail, tietPhone, tietWeight, tietHeight, tietAddress;
    AutoCompleteTextView actvCitiesMenu;
    MaterialButton cancelButton, nextButton;

    String username;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_information_fragment, container, false);
        initComponents(view);
        getGeneralInformation(view);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String modifiedFirstName = tietFirstName.getText().toString();
                String modifiedLastName = tietLastName.getText().toString();
                String modifiedEmail = tietEmail.getText().toString();
                String modifiedPhone = tietPhone.getText().toString();
                String modifiedWeight = tietWeight.getText().toString();
                String modifiedHeight = tietHeight.getText().toString();
                String modifiedAddress = tietAddress.getText().toString();
                String modifiedCity = actvCitiesMenu.getText().toString();

                if (TextUtils.isEmpty(modifiedAddress)) modifiedAddress = tilAddress.getHint().toString();
                if (TextUtils.isEmpty(modifiedHeight)) modifiedHeight = tilHeight.getHint().toString();
                if (TextUtils.isEmpty(modifiedEmail)) modifiedEmail = tilEmail.getHint().toString();
                if (TextUtils.isEmpty(modifiedPhone)) modifiedPhone = tilPhone.getHint().toString();
                if (TextUtils.isEmpty(modifiedWeight)) modifiedWeight = tilWeight.getHint().toString();
                if (TextUtils.isEmpty(modifiedFirstName)) modifiedFirstName = tilFirstName.getHint().toString();
                if (TextUtils.isEmpty(modifiedLastName)) modifiedLastName = tilLastName.getHint().toString();
                if (TextUtils.isEmpty(modifiedCity)) modifiedCity = tilCity.getHint().toString();

                AppUserModel appUserModel = new AppUserModel(username, modifiedFirstName, modifiedLastName, modifiedEmail, modifiedPhone, modifiedAddress, modifiedCity, modifiedWeight, modifiedHeight);

                Log.e("Prueba", appUserModel.getUsername() + " " +appUserModel.getFirstName() + " " + appUserModel.getLastName() + " " + appUserModel.getEmail() + " " + appUserModel.getPhone() + " " + appUserModel.getWeight() + " " + appUserModel.getHeight() + " " + appUserModel.getAddress() + " " + appUserModel.getCity());

                updateAppUser(view, appUserModel);


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Navigation) getActivity()).navigateTo(new ProfileFragment(), false);
            }
        });

        return view;
    }

    public void getGeneralInformation(View view){
        Call<ResponseBody> call = patientService.getPatient(String.valueOf(SharedPreferencesUtils.RetrieveIntDataFromSharedPreferences("appUserId", view)),(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token",view)));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        PatientDAO patientDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), PatientDAO.class);
                        Log.d("RESPONSE", "onResponse: "+patientDAO.getAppUser().getFirstName()+" "+patientDAO.getAppUser().getLastName());
                        tilFirstName.setHint(patientDAO.getAppUser().getFirstName());
                        tilLastName.setHint(patientDAO.getAppUser().getLastName());
                        tilAddress.setHint(patientDAO.getAppUser().getAddress());
                        tilEmail.setHint(patientDAO.getAppUser().getEmail());
                        tilHeight.setHint(patientDAO.getHeight());
                        tilWeight.setHint(patientDAO.getWeight());
                        tilPhone.setHint(patientDAO.getAppUser().getPhone());
                        tilCity.setHint(patientDAO.getAppUser().getCity());

                        username = patientDAO.getAppUser().getUsername();



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

    public void updateAppUser(View view, AppUserModel appUserModel){
        Call<ResponseBody> call = patientService.updatePatientGeneralInfo(appUserModel, SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("Prueba","Code: " + response.code());
                Toast.makeText(view.getContext(), "Cambios realizados correctamente", Toast.LENGTH_SHORT).show();
                ((Navigation) getActivity()).navigateTo(new ProfileFragment(), false);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(view.getContext(), "Hubo un problema con el servidor", Toast.LENGTH_SHORT).show();
                Log.e("Error","Mensaje: " + t.getMessage());

            }
        });
    }

    public void initComponents(View view){

        // Layouts
        tilFirstName = view.findViewById(R.id.first_name_text_input_config);
        tilLastName = view.findViewById(R.id.last_name_text_input_config);
        tilAddress = view.findViewById(R.id.address_text_input_config);
        tilEmail = view.findViewById(R.id.email_text_input_config);
        tilPhone = view.findViewById(R.id.phone_text_input_config);
        tilCity = view.findViewById(R.id.city_text_input_config);
        tilWeight = view.findViewById(R.id.weight_text_input_config);
        tilHeight = view.findViewById(R.id.height_text_input_config);

        // TextInputEditText
        tietFirstName = view.findViewById(R.id.first_name_edit_text_config);
        tietLastName = view.findViewById(R.id.last_name_edit_text_config);
        tietEmail = view.findViewById(R.id.email_edit_text_config);
        tietPhone = view.findViewById(R.id.phone_edit_text_config);
        tietWeight = view.findViewById(R.id.weight_edit_text_config);
        tietHeight = view.findViewById(R.id.height_edit_text_config);
        tietAddress = view.findViewById(R.id.address_edit_text_config);

        // Buttons

        nextButton = view.findViewById(R.id.next_button_config);
        cancelButton = view.findViewById(R.id.cancel_button_config);

        // Dropdown Ciudades
        actvCitiesMenu = view.findViewById(R.id.city_dropdown_config);

        String[] cities = new String[] {"La Paz", "Cochabamba","Santa Cruz","Tarija","Sucre","Oruro","Potosí","Beni","Pando"};
        ArrayAdapter<String> citiesMenuAdapter = new ArrayAdapter<String>(
                view.getContext(),
                R.layout.city_dropdown_menu,
                cities);
        actvCitiesMenu.setThreshold(1);
        actvCitiesMenu.setAdapter(citiesMenuAdapter);

    }
}
