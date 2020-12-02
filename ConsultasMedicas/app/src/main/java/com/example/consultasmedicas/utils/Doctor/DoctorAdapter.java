package com.example.consultasmedicas.utils.Doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Doctor.Doctor;
import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.utils.Medication.MedicationAdapter;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    Context context;
    List<DoctorDAO> doctors;
    private OnNoteListener mOnNoteListener;

    public DoctorAdapter(Context context, List<DoctorDAO> doctors, OnNoteListener mOnNoteListener) {
        this.context = context;
        this.doctors = doctors;
        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctor_listview_item, viewGroup, false);
        return new DoctorAdapter.DoctorViewHolder(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        holder.tvDoctorName.setText("Dr. " + doctors.get(position).getAppUser().getFirstName() + " "+doctors.get(position).getAppUser().getLastName());

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }


    public class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvDoctorName;
        OnNoteListener onNoteListener;

        public DoctorViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;

            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
