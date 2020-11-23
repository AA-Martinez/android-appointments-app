package com.example.consultasmedicas.utils.Diagnosis;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Diagnosis.Diagnosis;
import com.example.consultasmedicas.model.Diagnosis.DiagnosisDAO;
import com.example.consultasmedicas.model.Diagnosis.Specialisation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisItemListAdapter extends RecyclerView.Adapter<DiagnosisItemListAdapter.DiagnosisViewHolder> {

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
