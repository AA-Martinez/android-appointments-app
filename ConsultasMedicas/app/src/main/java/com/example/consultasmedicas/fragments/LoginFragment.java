package com.example.consultasmedicas.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.AppUser;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Login.LoginService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    LoginService loginService = Apis.loginUserService();
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        final TextInputEditText appUserEditText = view.findViewById(R.id.app_user_edit_text);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);

        MaterialButton logButton = view.findViewById(R.id.log_button);
        MaterialButton regButton = view.findViewById(R.id.register_button);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Navigation) getActivity()).navigateTo(new RegisterFragment(), true);
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    AppUser appUser = new AppUser();
                    appUser.setUsername(appUserEditText.getText().toString());
                    appUser.setPassword(passwordEditText.getText().toString());

                    autenticarUsuario(appUser);

                    passwordTextInput.setError(null);
                    ((Navigation) getActivity()).navigateTo(new HomeFragment(), false);

            }
        });


        return view;

    }


    public void autenticarUsuario(AppUser appUser){
        Log.d("tag",appUser.getUsername()+" "+ appUser.getPassword());
        Call<AppUser> call = loginService.login(appUser);
        call.enqueue(new Callback<AppUser>() {
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                    Headers headers = response.headers();
                    Log.d("tag","AUTH: "+headers.get("Authorization"));
            }

            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {
                    Log.e("tag",t.getMessage());
            }
        });
    }
}
