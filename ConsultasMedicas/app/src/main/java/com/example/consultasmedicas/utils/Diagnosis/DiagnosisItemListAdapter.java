package com.example.consultasmedicas.utils.Diagnosis;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.fragments.DoctorFragment;
import com.example.consultasmedicas.model.Degree.DegreeDAO;
import com.example.consultasmedicas.model.Diagnosis.Diagnosis;
import com.example.consultasmedicas.model.Diagnosis.DiagnosisDAO;
import com.example.consultasmedicas.model.Diagnosis.Specialisation;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Degree.DegreeService;
import com.example.consultasmedicas.utils.SharedPreferences.SharedPreferencesUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosisItemListAdapter extends RecyclerView.Adapter<DiagnosisItemListAdapter.DiagnosisViewHolder> {

    DegreeService degreeService = Apis.degreeService();
    Context context;
    List<DiagnosisDAO> diagnoses;

    public DiagnosisItemListAdapter(Context context, List<DiagnosisDAO> diagnoses) {
        this.context = context;
        this.diagnoses = diagnoses;
    }

    @NonNull
    @Override
    public DiagnosisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diagnosis_item, parent, false);
        return new DiagnosisItemListAdapter.DiagnosisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosisViewHolder holder, int position) {

        List<Specialisation> specialisations = new ArrayList<>();

        holder.tvDiagnosisName.setText(diagnoses.get(position).getIssue().getName());
        holder.tvDiagnosisCause.setText(diagnoses.get(position).getIssue().getProfName());

        if (diagnoses.get(position).getIssue().getAccuracy() >= 90){
            holder.tvDiagnosisAccuracy.setTextColor(Color.parseColor("#006400"));
        }else if(diagnoses.get(position).getIssue().getAccuracy() >= 60 && diagnoses.get(position).getIssue().getAccuracy() < 90){
            holder.tvDiagnosisAccuracy.setTextColor(Color.parseColor("#F9C70C"));

        }else if (diagnoses.get(position).getIssue().getAccuracy() >= 30 && diagnoses.get(position).getIssue().getAccuracy() < 60){
            holder.tvDiagnosisAccuracy.setTextColor(Color.parseColor("#FF8C00"));

        }else if (diagnoses.get(position).getIssue().getAccuracy() >= 0 && diagnoses.get(position).getIssue().getAccuracy() < 30){
            holder.tvDiagnosisAccuracy.setTextColor(Color.parseColor("#8B0000"));

        }
        DecimalFormat formato1 = new DecimalFormat("#.00");
        holder.tvDiagnosisAccuracy.setText(formato1.format(diagnoses.get(position).getIssue().getAccuracy()) + "%");

        specialisations.addAll(diagnoses.get(position).getSpecialisation());

        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, specialisations);
        holder.lvSpecialty.setAdapter(arrayAdapter);

        holder.lvSpecialty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<DegreeDAO> specialties = new ArrayList<>();
                final String selected;
                final boolean[] f = {false};
                Call<ResponseBody> call = degreeService.getSpecialtyList(SharedPreferencesUtils.RetrieveStringDataFromSharedPreferences("auth-token", view));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for(int i = 0; i<jsonArray.length(); i++){
                                    Gson gson = new Gson();
                                    DegreeDAO degreeDAO = gson.fromJson(jsonArray.getJSONObject(i).toString(),DegreeDAO.class);
                                    specialties.add(degreeDAO);
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        }

                        for (DegreeDAO degreeDAO: specialties
                             ) {
                            if (degreeDAO.getSpecialty().toLowerCase().equals(adapterView.getItemAtPosition(i).toString().toLowerCase())){
                                f[0] = true;
                            }
                        }

                        if (f[0]){
                            SharedPreferencesUtils.SaveStringDataToSharedPreferences("selected_speciality", adapterView.getItemAtPosition(i).toString(), view);
                            ((Navigation) context).navigateTo(new DoctorFragment(), true);


                        }else{
                            Toast.makeText(view.getContext(), "No tenemos registrados doctores con esa especializacion en el sistema", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });


                Log.e("TEST", String.valueOf(adapterView.getItemAtPosition(i)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return diagnoses.size();
    }

    public class DiagnosisViewHolder extends RecyclerView.ViewHolder {

        TextView tvDiagnosisName, tvDiagnosisCause, tvDiagnosisAccuracy;
        ListView lvSpecialty;

        public DiagnosisViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDiagnosisAccuracy = itemView.findViewById(R.id.tvDiagnosisAccuracy);
            tvDiagnosisName = itemView.findViewById(R.id.tvDiagnosisName);
            tvDiagnosisCause = itemView.findViewById(R.id.tvDiagnosisCause);
            lvSpecialty = itemView.findViewById(R.id.lvDiagnosisSpecialty);

        }
    }
}
