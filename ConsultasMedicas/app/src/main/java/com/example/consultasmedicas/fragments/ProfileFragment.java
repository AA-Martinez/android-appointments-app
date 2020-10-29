package com.example.consultasmedicas.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.consultasmedicas.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.profile_screen, container, false);

        final ListView listView = view.findViewById(R.id.profile_listview);

        String[] values = new String[]{
                "Informacion general", "Condiciones aplicables", "Enfermedades", "Mediciones actuales", "Alergias", "Habitos"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        return view;
    }
}