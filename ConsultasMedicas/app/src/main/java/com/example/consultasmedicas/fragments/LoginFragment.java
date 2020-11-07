package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.AppUserDto;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Login.LoginService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    LoginService loginService = Apis.loginUserService();
    TextInputLayout passwordTextInput;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        final TextInputEditText appUserEditText = view.findViewById(R.id.app_user_edit_text);
        passwordTextInput = view.findViewById(R.id.password_text_input);
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

                    AppUserDto appUserDto = new AppUserDto();
                    appUserDto.setUsername(appUserEditText.getText().toString());
                    appUserDto.setPassword(passwordEditText.getText().toString());

                    authUser(appUserDto, view);


            }
        });


        return view;

    }


    public void authUser(AppUserDto appUserDto, View view){
        Call<Void> call = loginService.login(appUserDto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
                    SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("auth-token", headers.get("Authorization"));
                    editor.apply();

                    Log.e("hfsp", sharedPreferences.getString("auth-token", ""));

                    Toast.makeText(view.getContext(), "Login correcto", Toast.LENGTH_SHORT).show();
                    passwordTextInput.setError(null);
                    ((Navigation) getActivity()).navigateTo(new HomeFragment(), false);

                }else{
                    Toast.makeText(view.getContext(), "Login incorrecto", Toast.LENGTH_SHORT).show();
                    passwordTextInput.setError(getString(R.string.error_password3));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

    }
}
