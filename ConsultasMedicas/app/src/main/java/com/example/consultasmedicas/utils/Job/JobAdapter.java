package com.example.consultasmedicas.utils.Job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Degree.DegreeDAO;
import com.example.consultasmedicas.model.Job.Job;
import com.example.consultasmedicas.utils.Degree.DegreeInfoAdapter;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    Context context;
    List<Job> jobs;

    public JobAdapter(Context context, List<Job> jobs) {
        this.context = context;
        this.jobs = jobs;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_item, parent, false);
        return new JobAdapter.JobViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        holder.tvWorkplace.setText("Institucion: "+ jobs.get(position).getWorkplace());
        holder.tvStartYear.setText("Año inicio: "+ jobs.get(position).getStartYear());
        holder.tvEndYear.setText("Año fin: "+ jobs.get(position).getEndYear());
        holder.tvDescription.setText("Especialidad: "+ jobs.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tvWorkplace, tvStartYear, tvEndYear, tvDescription;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWorkplace = itemView.findViewById(R.id.tvWorkplace);
            tvStartYear = itemView.findViewById(R.id.tvStartYear);
            tvEndYear = itemView.findViewById(R.id.tvEndYear);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }
    }
}
