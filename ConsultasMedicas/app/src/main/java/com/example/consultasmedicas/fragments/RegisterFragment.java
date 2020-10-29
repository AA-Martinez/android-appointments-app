package com.example.consultasmedicas.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.consultasmedicas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static java.security.AccessController.getContext;

public class RegisterFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        final TextInputLayout tilPassword = view.findViewById(R.id.password_text_input);
        final TextInputEditText tietPassword = view.findViewById(R.id.password_edit_text);

        final TextInputLayout tilConfirmPass = view.findViewById(R.id.confirm_pass_text_input);
        final TextInputEditText tietConfirmPass = view.findViewById(R.id.confirm_pass_edit_text);

        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        MaterialButton nextButton = view.findViewById(R.id.next_button);

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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(isPasswordValid(tietPassword.getText()))){
                    tilPassword.setError(getString(R.string.error_password));
                }
                if(!(tietPassword.getText().toString().equals(tietConfirmPass.getText().toString()))){
                    tilConfirmPass.setError(getString(R.string.error_password2));
                    //Log.d("tag","EQUALS:    ");
                }
            }
        });

        String[] genres = new String[] {"Masculino", "Femenino"};
        String[] cities = new String[] {"La Paz", "Cochabamba","Santa Cruz","Tarija","Sucre","Oruro","Potosí","Beni","Pando"};

        ArrayAdapter<String> genresMenuAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.genre_dropdown_menu,
                genres);

        AutoCompleteTextView genresMenu = view.findViewById(R.id.genre_dropdown);
        genresMenu.setAdapter(genresMenuAdapter);

        ArrayAdapter<String> citiesMenuAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.city_dropdown_menu,
                cities);

        AutoCompleteTextView citiesMenu = view.findViewById(R.id.city_dropdown);
        citiesMenu.setAdapter(citiesMenuAdapter);


        return view;
    }
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }
}
