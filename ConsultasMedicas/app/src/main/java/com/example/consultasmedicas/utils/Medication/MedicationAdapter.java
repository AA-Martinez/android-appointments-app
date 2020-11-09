package com.example.consultasmedicas.utils.Medication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Medication.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    Context context;
    List<Medication> medicationList;

    public MedicationAdapter(Context context, List<Medication> medicationList) {
        this.context = context;
        this.medicationList = medicationList;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item, viewGroup, false);
        return new MedicationAdapter.MedicationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        holder.tvMedicationName.setText(medicationList.get(position).getName());
        holder.tvMedicationDescription.setText(medicationList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder{

        TextView tvMedicationName, tvMedicationDescription;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMedicationName = itemView.findViewById(R.id.tvName);
            tvMedicationDescription = itemView.findViewById(R.id.tvDescription);

        }
    }

    public void filter(ArrayList<Medication> filterMedication){
        this.medicationList = filterMedication;
        notifyDataSetChanged();

    }
}
