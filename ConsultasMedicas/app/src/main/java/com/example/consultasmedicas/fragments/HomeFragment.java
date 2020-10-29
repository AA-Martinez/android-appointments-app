package com.example.consultasmedicas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView nav_menu;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);



        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.navigation_view);
        nav_menu = view.findViewById(R.id.nav_menu_button);

        navigationDrawer();
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



}
