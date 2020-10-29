package com.example.consultasmedicas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.fragments.profileConfig.AllergyFragment;
import com.example.consultasmedicas.fragments.profileConfig.DiseaseFragment;
import com.example.consultasmedicas.fragments.profileConfig.GeneralInformationFragment;
import com.example.consultasmedicas.fragments.profileConfig.MedicationsFragment;
import com.example.consultasmedicas.fragments.profileConfig.SubstancesFragment;

public class MedicsFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medics_fragment, container, false);

        final ListView listView = view.findViewById(R.id.listview_medics);

        String[] values = new String[]{
                "Dr. Edmundo Calisaya"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    ((Navigation) getActivity()).navigateTo(new MedicPersonalInformationFragment(), true);
                }
            }
        });



        return view;
    }

}
