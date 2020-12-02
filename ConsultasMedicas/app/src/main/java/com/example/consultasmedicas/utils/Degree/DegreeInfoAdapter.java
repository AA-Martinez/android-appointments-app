package com.example.consultasmedicas.utils.Degree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Degree.DegreeDAO;

import java.util.List;

public class DegreeInfoAdapter extends RecyclerView.Adapter<DegreeInfoAdapter.DegreeViewHolder> {

    Context context;
    List<DegreeDAO> degrees;

    public DegreeInfoAdapter(Context context, List<DegreeDAO> degrees) {
        this.context = context;
        this.degrees = degrees;
    }

    @NonNull
    @Override
    public DegreeInfoAdapter.DegreeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.degree_item, viewGroup, false);
        return new DegreeInfoAdapter.DegreeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DegreeViewHolder holder, int position) {
        holder.tvInstitution.setText("Institucion: "+ degrees.get(position).getInstitution());
        holder.tvStartYear.setText("Año inicio: "+ degrees.get(position).getStartYear());
        holder.tvEndYear.setText("Año fin: "+ degrees.get(position).getEndYear());
        holder.tvSpeciality.setText("Especialidad: "+ degrees.get(position).getSpecialty());
    }

    @Override
    public int getItemCount() {
        return degrees.size();
    }

    public class DegreeViewHolder extends RecyclerView.ViewHolder {
        TextView tvInstitution, tvStartYear, tvEndYear, tvSpeciality;
        public DegreeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInstitution = itemView.findViewById(R.id.tvInstitution);
            tvStartYear = itemView.findViewById(R.id.tvStartYear);
            tvEndYear = itemView.findViewById(R.id.tvEndYear);
            tvSpeciality = itemView.findViewById(R.id.tvSpecialityItem);
        }


    }
}
