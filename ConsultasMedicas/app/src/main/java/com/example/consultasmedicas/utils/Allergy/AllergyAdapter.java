package com.example.consultasmedicas.utils.Allergy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.fragments.profileConfig.AllergyFragment;
import com.example.consultasmedicas.model.Allergy;

import java.util.List;

public class AllergyAdapter extends ArrayAdapter<Allergy> {

    private Context context;
    private List<Allergy> allergies;

    public AllergyAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Allergy> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.allergies = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.listview_item, parent, false);

        TextView textViewIdAllergy = (TextView) rowView.findViewById(R.id.id_allergy);
        TextView textViewAllergyName= (TextView) rowView.findViewById(R.id.allergy_name);

        textViewIdAllergy.setText(String.format("ID:%s", allergies.get(position).getId()));
        textViewAllergyName.setText(String.format("Nombre:%s", allergies.get(position).getName()));

        return rowView;


    }
}
