package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Patient.PatientDAO;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{
    PatientService patientService = Apis.patientService();

    SharedPreferences sharedPreferences;
    String jwtString;


    TextView ciMenuTextView;
    TextView nameMenuTextView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView nav_menu;
    WebView webView;

    String url = "https://www.minsalud.gob.bo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        //webView = view.findViewById(R.id.webview);
        //WebSettings ajustesVisorWeb = webView.getSettings();
        //ajustesVisorWeb.setJavaScriptEnabled(true);
        //webView.loadUrl(url);

        sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        jwtString = (sharedPreferences.getString("auth-token","")).replace("Bearer ","");

        String[] jwtItems = jwtString.split("[.]");

        byte[] decodedBytes = Base64.getDecoder().decode(jwtItems[1]);
        String decodedString = new String(decodedBytes);


        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.navigation_view);
        nav_menu = view.findViewById(R.id.nav_menu_button);

        View headerView = navigationView.getHeaderView(0);

        ciMenuTextView = (TextView) headerView.findViewById(R.id.ciMenu);
        nameMenuTextView = (TextView) headerView.findViewById(R.id.nameMenu);

        navigationDrawer();
        decodeJwt();

        Call<ResponseBody> call = patientService.getPatient("1",(sharedPreferences.getString("auth-token","")));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        PatientDAO patientDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), PatientDAO.class);
                        Log.d("RESPONSE", "onResponse: "+patientDAO.getAppUser().getFirstName()+" "+patientDAO.getAppUser().getLastName());
                        ciMenuTextView.setText(patientDAO.getAppUser().getCi());
                        nameMenuTextView.setText((patientDAO.getAppUser().getFirstName() + " " + patientDAO.getAppUser().getLastName()).toString());

                        sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("appUserId", patientDAO.getAppUser().getId());
                        editor.apply();

                        Log.e("APPUSERID", String.valueOf(sharedPreferences.getInt("appUserId", 0)));


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", "onFailure: "+t.getMessage());

            }
        });


        return view;
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);


        nav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home){
            ((Navigation) getActivity()).navigateTo(new HomeFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.my_account){
            ((Navigation) getActivity()).navigateTo(new ProfileFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.medics){
            ((Navigation) getActivity()).navigateTo(new SpecialtyFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.create_cons){
            ((Navigation) getActivity()).navigateTo(new CreateConsultFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.my_consul){
            ((Navigation) getActivity()).navigateTo(new AppointmentFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.quick_appointment){
            ((Navigation) getActivity()).navigateTo(new QuickAppointmentSymptomsFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        }



        return true;
    }

    public void decodeJwt(){

    }


}
