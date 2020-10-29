package com.example.consultasmedicas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static java.security.AccessController.getContext;

public class RegisterFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

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

}
