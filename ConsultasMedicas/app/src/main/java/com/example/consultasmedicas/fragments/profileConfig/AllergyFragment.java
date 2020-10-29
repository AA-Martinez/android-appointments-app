package com.example.consultasmedicas.fragments.profileConfig;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.consultasmedicas.AllergyAdapter;
import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.fragments.HomeFragment;
import com.example.consultasmedicas.fragments.RegisterFragment;
import com.example.consultasmedicas.model.Allergy;
import com.example.consultasmedicas.utils.AllergyService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllergyFragment extends Fragment {

    AllergyService allergyService;
    List<Allergy> allergies = new ArrayList<>();
    ListView listViewAllergies;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.allergy_fragment, container, false);

        listViewAllergies = view.findViewById(R.id.listview_allergies);
        getAllergies();

        return view;
    }

    public void getAllergies(){
        Call<List<Allergy>>call = allergyService.getAllergies();
        call.enqueue(new Callback<List<Allergy>>() {
            @Override
            public void onResponse(Call<List<Allergy>> call, Response<List<Allergy>> response) {
                allergies = response.body();
                listViewAllergies.setAdapter(new AllergyAdapter(getActivity().getApplicationContext(), R.layout.allergy_fragment, allergies));

            }

            @Override
            public void onFailure(Call<List<Allergy>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());

            }
        });
    }
}
