package com.example.consultasmedicas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MyConsultFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_consult_fragment, container, false);

        final ListView listView = view.findViewById(R.id.listview_consults);

        String[] values = new String[]{
                "Consulta con Dr. Miguel Vilca"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    ((Navigation) getActivity()).navigateTo(new ChatFragment(), true);
                }
            }
        });



        return view;
    }


}
