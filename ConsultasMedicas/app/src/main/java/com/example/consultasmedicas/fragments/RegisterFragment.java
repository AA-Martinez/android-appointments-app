package com.example.consultasmedicas.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Patient.Patient;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterFragment extends Fragment {

    PatientService patientService = Apis.signUpPatientService();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        String[] genres = new String[] {"Masculino", "Femenino"};
        String[] cities = new String[] {"La Paz", "Cochabamba","Santa Cruz","Tarija","Sucre","Oruro","Potosí","Beni","Pando"};

        ArrayAdapter<String> genresMenuAdapter = new ArrayAdapter<String>(
                view.getContext(),
                R.layout.genre_dropdown_menu,
                genres);

        AutoCompleteTextView genresMenu = (AutoCompleteTextView) view.findViewById(R.id.genre_dropdown);
        genresMenu.setThreshold(1);
        genresMenu.setAdapter(genresMenuAdapter);

        ArrayAdapter<String> citiesMenuAdapter = new ArrayAdapter<String>(
                view.getContext(),
                R.layout.city_dropdown_menu,
                cities);

        AutoCompleteTextView citiesMenu = view.findViewById(R.id.city_dropdown);
        citiesMenu.setThreshold(1);
        citiesMenu.setAdapter(citiesMenuAdapter);

        final TextInputEditText tietCi = view.findViewById(R.id.ci_edit_text);

        final TextInputLayout tilPassword = view.findViewById(R.id.password_text_input);
        final TextInputEditText tietPassword = view.findViewById(R.id.password_edit_text);

        final TextInputLayout tilConfirmPass = view.findViewById(R.id.confirm_pass_text_input);
        final TextInputEditText tietConfirmPass = view.findViewById(R.id.confirm_pass_edit_text);

        final TextInputEditText tietFirstName = view.findViewById(R.id.first_name_edit_text);

        final TextInputEditText tietLastName = view.findViewById(R.id.last_name_edit_text);

        final TextInputEditText tietAge = view.findViewById(R.id.age_edit_text);

        final TextInputEditText tietEmail = view.findViewById(R.id.email_edit_text);

        final TextInputEditText tietPhone = view.findViewById(R.id.phone_edit_text);

        final TextInputEditText tietWeight = view.findViewById(R.id.weight_edit_text);

        final TextInputEditText tietHeight = view.findViewById(R.id.height_edit_text);

        final TextInputEditText tietAddress = view.findViewById(R.id.address_edit_text);

        final TextInputEditText tietBirthDate = view.findViewById(R.id.birth_date_edit_text);

        final TextInputEditText tietBirthCountry = view.findViewById(R.id.birth_country_edit_text);

        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        MaterialButton nextButton = view.findViewById(R.id.next_button);

        Patient patient = new Patient();

        tietPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((isPasswordValid(tietPassword.getText()))){
                    tilPassword.setError(null);
                }
                return false;
            }
        });
        tietConfirmPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((tietPassword.getText().toString().equals(tietConfirmPass.getText().toString()))){
                    tilConfirmPass.setError(null);
                }
                return false;
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Navigation) getActivity()).navigateTo(new LoginFragment(), false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(isPasswordValid(tietPassword.getText()))){
                    tilPassword.setError(getString(R.string.error_password));
                }
                else{
                    //tilPassword.setError(null);
                    if(!(tietPassword.getText().toString().equals(tietConfirmPass.getText().toString()))){
                        tilConfirmPass.setError(getString(R.string.error_password2));
                    }
                    else {

                        patient.setCi(tietCi.getText().toString());
                        patient.setPassword(tietPassword.getText().toString());
                        patient.setFirstName(tietFirstName.getText().toString());
                        patient.setLastName(tietLastName.getText().toString());
                        patient.setGenre(genresMenu.getText().toString());
                        patient.setEmail(tietEmail.getText().toString());
                        patient.setPhone(tietPhone.getText().toString());
                        patient.setWeight(tietWeight.getText().toString());
                        patient.setHeight(tietHeight.getText().toString());
                        patient.setAddress(tietAddress.getText().toString());
                        patient.setCity(citiesMenu.getText().toString());
                        patient.setBirthDate(tietBirthDate.getText().toString()+" 00:00:00");
                        patient.setBirthCountry(tietBirthCountry.getText().toString());

                        Log.d("TAGR", "CI: "+patient.getCi()+" GENRE: "+patient.getGenre()+" CITY: "+patient.getCity()+" BD: "+patient.getBirthDate());

                        registrarPaciente(patient);

                    }
                }

            }
        });




        return view;
    }


    public void registrarPaciente(Patient patient){
        Call<ResponseBody> call = patientService.signUp(patient);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                String responseValue = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                     responseValue = jsonObject.getString("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("TAG", "onResponse: "+responseValue);

                if(responseValue.equals("true")){
                    Toast.makeText(getActivity(), "Registrado correctamente!", Toast.LENGTH_SHORT).show();
                    ((Navigation) getActivity()).navigateTo(new LoginFragment(), false);
                }
                else {
                    Toast.makeText(getActivity(), "Error al registrar usuario! Intente nuevamente", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al registrar usuario!", Toast.LENGTH_SHORT).show();

                Log.e("tag",t.getMessage());
            }
        });
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }
}
