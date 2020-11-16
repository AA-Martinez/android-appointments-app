package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Degree.DegreeDAO;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Degree.DegreeAdapter;
import com.example.consultasmedicas.utils.Degree.DegreeService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialtyFragment extends Fragment implements DegreeAdapter.OnNoteListener {
    RecyclerView rvSpecialtiesList;
    DegreeAdapter degreeAdapter;
    List<DegreeDAO> specialties;
    DegreeService degreeService = Apis.degreeService();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.degrees_fragment,container,false);

        rvSpecialtiesList = view.findViewById(R.id.rvDegreesList);
        rvSpecialtiesList.setLayoutManager(new GridLayoutManager(view.getContext(),1));

        specialties = new ArrayList<>();
        getSpecialtiesList(view);

        degreeAdapter = new DegreeAdapter(view.getContext(),specialties,this);
        rvSpecialtiesList.setAdapter(degreeAdapter);
        return view;
    }

    private void getSpecialtiesList(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = degreeService.getSpecialtyList(sharedPreferences.getString("auth-token", ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0; i<jsonArray.length(); i++){
                            Gson gson = new Gson();
                            DegreeDAO degreeDAO = gson.fromJson(jsonArray.getJSONObject(i).toString(),DegreeDAO.class);
                            specialties.add(degreeDAO);
                            degreeAdapter.notifyDataSetChanged();
                            Log.d("SPECIALTY", i+" "+degreeDAO.getSpecialty());
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onNoteClick(int position) {

    }
}
