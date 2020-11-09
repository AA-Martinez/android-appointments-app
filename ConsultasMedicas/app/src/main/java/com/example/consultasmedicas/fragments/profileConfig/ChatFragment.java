package com.example.consultasmedicas.fragments.profileConfig;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consultasmedicas.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ChatFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        return view;
    }
}
