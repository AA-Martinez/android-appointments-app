package com.example.consultasmedicas.utils.Allergy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Allergy.Allergy;

import java.util.ArrayList;
import java.util.List;

public class AllergyAdapter extends RecyclerView.Adapter<AllergyAdapter.AllergyViewHolder>{

    Context context;
    List<Allergy> allergyList;

    public AllergyAdapter(Context context, List<Allergy> allergyList) {
        this.context = context;
        this.allergyList = allergyList;
    }

    @NonNull
    @Override
    public AllergyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item, viewGroup, false);
        return new AllergyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllergyViewHolder holder, int position) {
        holder.tvAllergyName.setText(allergyList.get(position).getName());
        holder.tvAllergyDescription.setText(allergyList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return allergyList.size();
    }

    public class AllergyViewHolder extends RecyclerView.ViewHolder{

        TextView tvAllergyName, tvAllergyDescription;

        public AllergyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAllergyName = itemView.findViewById(R.id.tvName);
            tvAllergyDescription = itemView.findViewById(R.id.tvDescription);

        }
    }

    public void filter(ArrayList<Allergy> filterAllergy){
        this.allergyList = filterAllergy;
        notifyDataSetChanged();

    }
}
