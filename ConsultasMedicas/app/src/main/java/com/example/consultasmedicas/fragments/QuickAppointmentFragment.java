package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuickAppointmentFragment extends Fragment {
    ExtendedFloatingActionButton fabConsult;
    MaterialAlertDialogBuilder dialogBuilder;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quick_appointment, container, false);

        fabConsult = view.findViewById(R.id.consult_fabutton);

        fabConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                .setTitle("Resultados")
                        .setMessage("Parece que tienes: \n"+"Resfrio común")
                        .setNeutralButton("OK",(dialogInterface, which) ->{
                            ((Navigation) getActivity()).navigateTo(new HomeFragment(), false);
                        })
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //dialogBuilder.setNeutralButton()
    }
}
