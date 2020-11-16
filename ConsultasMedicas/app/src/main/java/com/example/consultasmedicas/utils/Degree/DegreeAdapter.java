package com.example.consultasmedicas.utils.Degree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Degree.Degree;
import com.example.consultasmedicas.model.Degree.DegreeDAO;
import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.utils.Doctor.DoctorAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DegreeAdapter extends RecyclerView.Adapter<DegreeAdapter.DegreeViewHolder> {
    Context context;
    List<DegreeDAO> degrees;
    private OnNoteListener mOnNoteListener;

    public DegreeAdapter(Context context, List<DegreeDAO> degrees, OnNoteListener mOnNoteListener) {
        this.context = context;
        this.degrees = degrees;
        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @Override
    public DegreeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.degree_listview_item, viewGroup, false);
        return new DegreeAdapter.DegreeViewHolder(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DegreeViewHolder holder, int position) {
        holder.tvDegreeName.setText(degrees.get(position).getSpecialty());
    }

    @Override
    public int getItemCount() {
        return degrees.size();
    }

    public class DegreeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDegreeName;
        OnNoteListener onNoteListener;

        public DegreeViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;

            tvDegreeName = itemView.findViewById(R.id.degree_listview_name);
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

