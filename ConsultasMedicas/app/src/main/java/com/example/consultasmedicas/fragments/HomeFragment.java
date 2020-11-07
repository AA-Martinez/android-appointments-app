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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.AppUser;
import com.example.consultasmedicas.model.Data;
import com.example.consultasmedicas.model.Patient;
import com.example.consultasmedicas.model.RequestResponse;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{
    PatientService patientService = Apis.patientService();

    SharedPreferences sharedPreferences;
    String jwtString;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    TextView ciMenuTextView;
    TextView nameMenuTextView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView nav_menu;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        final TextView testTextView = view.findViewById(R.id.test_text_view);

        sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        jwtString = (sharedPreferences.getString("auth-token","")).replace("Bearer ","");

        String[] jwtItems = jwtString.split("[.]");

        byte[] decodedBytes = Base64.getDecoder().decode(jwtItems[1]);
        String decodedString = new String(decodedBytes);

        //Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        //String appUserId = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtString).getBody().get("appUserId", String.class);



        Call<RequestResponse> call = patientService.getPatient("1",(sharedPreferences.getString("auth-token","")));

        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {

                if(response.isSuccessful()){
                    RequestResponse requestResponse = response.body();
                    Data patientData = requestResponse.getData();
                    AppUser appUserData = patientData.getAppUser();


                    Log.d("RESPONSE", "onResponse: "+appUserData.getFirstName()+" "+appUserData.getLastName());
                    //testTextView.setText(patient.getFirstName()+" "+patient.getLastName());
                }
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                Log.e("ERROR", "onFailure: "+t.getMessage());
            }
        });



        //testTextView.setText("");

        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.navigation_view);
        nav_menu = view.findViewById(R.id.nav_menu_button);

        View headerView = navigationView.getHeaderView(0);

        ciMenuTextView = (TextView) headerView.findViewById(R.id.ciMenu);
        nameMenuTextView = (TextView) headerView.findViewById(R.id.nameMenu);

        ciMenuTextView.setText("5972726 LP");
        nameMenuTextView.setText("Miguel Martinez");

        navigationDrawer();
        decodeJwt();
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
        }
        else if (id == R.id.medics){
            ((Navigation) getActivity()).navigateTo(new MedicsFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        }/*else if (id == R.id.home){
            ((Navigation) getActivity()).navigateTo(new HomeFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if (id == R.id.home){
            ((Navigation) getActivity()).navigateTo(new HomeFragment(), true);
            drawerLayout.closeDrawer(GravityCompat.START);
        }*/



        return true;
    }

    public void decodeJwt(){

    }


}
