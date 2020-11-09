package com.example.consultasmedicas.utils.Disease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Disease.Disease;

import java.util.ArrayList;
import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>{

    Context context;
    List<Disease> diseaseList;

    public DiseaseAdapter(Context context, List<Disease> diseaseList) {
        this.context = context;
        this.diseaseList = diseaseList;
    }

    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item, viewGroup, false);
        return new DiseaseAdapter.DiseaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, int position) {
        holder.tvDiseaseName.setText(diseaseList.get(position).getName());
        holder.tvDiseaseDescription.setText(diseaseList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }


    public class DiseaseViewHolder extends RecyclerView.ViewHolder{

        TextView tvDiseaseName, tvDiseaseDescription;

        public DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDiseaseName = itemView.findViewById(R.id.tvName);
            tvDiseaseDescription = itemView.findViewById(R.id.tvDescription);

        }
    }

    public void filter(ArrayList<Disease> filterDisease){
        this.diseaseList = filterDisease;
        notifyDataSetChanged();

    }
}
