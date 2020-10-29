package com.example.consultasmedicas.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.fragments.profileConfig.AllergyFragment;
import com.example.consultasmedicas.fragments.profileConfig.DiseaseFragment;
import com.example.consultasmedicas.fragments.profileConfig.GeneralInformationFragment;
import com.example.consultasmedicas.fragments.profileConfig.MedicationsFragment;
import com.example.consultasmedicas.fragments.profileConfig.SubstancesFragment;

public class ProfileFragment extends Fragment{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view1 = inflater.inflate(R.layout.profile_screen, container, false);

        final ListView listView = view1.findViewById(R.id.profile_listview);

        String[] values = new String[]{
                "Informacion general", "Enfermedades", "Mediciones actuales", "Alergias", "Substancias"
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

        return view1;
    }

}