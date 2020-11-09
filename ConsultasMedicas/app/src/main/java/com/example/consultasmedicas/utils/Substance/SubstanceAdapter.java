package com.example.consultasmedicas.utils.Substance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Substance.Substance;

import java.util.ArrayList;
import java.util.List;

public class SubstanceAdapter extends RecyclerView.Adapter<SubstanceAdapter.SubstanceViewHolder> {

    Context context;
    List<Substance> substanceList;

    public SubstanceAdapter(Context context, List<Substance> substanceList) {
        this.context = context;
        this.substanceList = substanceList;
    }

    @NonNull
    @Override
    public SubstanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item, viewGroup, false);
        return new SubstanceAdapter.SubstanceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubstanceViewHolder holder, int position) {
        holder.tvSubstanceName.setText(substanceList.get(position).getName());
        holder.tvSubstanceDescription.setText(substanceList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return substanceList.size();
    }

    public class SubstanceViewHolder extends RecyclerView.ViewHolder{
        TextView tvSubstanceName, tvSubstanceDescription;

        public SubstanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubstanceName = itemView.findViewById(R.id.tvName);
            tvSubstanceDescription = itemView.findViewById(R.id.tvDescription);
        }
    }

    public void filter(ArrayList<Substance> filterSubstance){
        this.substanceList = filterSubstance;
        notifyDataSetChanged();

    }
}
