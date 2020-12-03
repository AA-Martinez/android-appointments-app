package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.AppUser.AppUserDto;
import com.example.consultasmedicas.utils.ApiMedic.ApiMedicService;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.CalendarService;
import com.example.consultasmedicas.utils.Login.LoginService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LoginFragment extends Fragment {

    LoginService loginService = Apis.loginUserService();
    ApiMedicService apiMedicService = Apis.apiMedicServiceLogin();
    TextInputLayout passwordTextInput;

    public static final String ApiMedicUsername = "maximilian.vino@ucb.edu.bo";
    public static final String ApiMedicPassword = "Ac2i8LTp4q5CYz67N";
    public static final String ApiMedicUrl = "https://sandbox-authservice.priaid.ch/login";

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    try {
                        LoadToken(ApiMedicUsername, ApiMedicPassword, ApiMedicUrl, view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Headers headers = response.headers();

                    Intent intent = new Intent(getActivity().getApplicationContext(), CalendarService.class);
                    getActivity(). startService(intent);

                    SharedPreferencesUtils.SaveStringDataToSharedPreferences("auth-token", headers.get("Authorization"), view);

                    Log.e("FromSharedPreferencesTj", SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void LoadToken(String username, String password, String url, View view) throws Exception{
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                password.getBytes(),
                "HmacMD5");

        String computedHashString = "";
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKeySpec);
            byte[] result = mac.doFinal(url.getBytes());

            computedHashString = Base64.getEncoder().encodeToString(result);

            Call<ResponseBody> call = apiMedicService.loginApiMedic("Bearer " + username + ":"+ computedHashString);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        SharedPreferencesUtils.SaveStringDataToSharedPreferences("apiMedicToken", jsonObject.get("Token").toString(), view);
                        Log.e("FromSharedPreferencesT", SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("apiMedicToken", view));

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new Exception("Error (No such algorithm exception)");
        }catch (InvalidKeyException e){
            e.printStackTrace();
            throw new Exception("Error (Invalid key exception)");
        }

    }


}
