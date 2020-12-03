package com.example.consultasmedicas.utils.Appointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Appointment.AppointmentDAO;
import com.example.consultasmedicas.utils.Doctor.DoctorAdapter;

import java.util.List;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>{

    Context context;
    List<AppointmentDAO> appointments;
    private OnNoteListener mOnNoteListener;

    public AppointmentAdapter(Context context, List<AppointmentDAO> appointments, OnNoteListener mOnNoteListener) {
        this.context = context;
        this.appointments = appointments;
        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointment_listview_item, viewGroup, false);
        return new AppointmentAdapter.AppointmentViewHolder(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        holder.tvAppointmentName.setText(appointments.get(position).getMessages().get(0).getText());
        holder.tvCreation.setText(appointments.get(position).getCreationTimeStamp());
        holder.tvId.setText("ID consulta: " + appointments.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }


    public class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvAppointmentName;
        TextView tvId;
        TextView tvCreation;
        OnNoteListener onNoteListener;

        public AppointmentViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            tvId = itemView.findViewById(R.id.tvIdAppointment);
            tvAppointmentName = itemView.findViewById(R.id.tvTextMessage);
            tvCreation = itemView.findViewById(R.id.tvCreationStamp);

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
