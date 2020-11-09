package com.example.consultasmedicas.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.consultasmedicas.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateConsultFragment extends Fragment{
    public static final int PICKFILE_RESULT_CODE = 1;

    private FloatingActionButton fabtnAddFile;
    private ExtendedFloatingActionButton efabNext;
    private ImageView imageView;
    private TextView tvTest;
    private LinearLayout layout;
    private Uri fileUri;
    private String filePath;

    private ArrayList<Uri> uriList = new ArrayList<>();
    private ArrayList<String> pathList = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_consult_fragment, container, false);

        fabtnAddFile = (FloatingActionButton) view.findViewById(R.id.add_files_floating_button);
        efabNext = (ExtendedFloatingActionButton) view.findViewById(R.id.next_floating_button);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        layout = (LinearLayout)view.findViewById(R.id.image_layout);
        tvTest = (TextView) view.findViewById(R.id.test_text_view);

        fabtnAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Escoja un archivo");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {

                    fileUri = data.getData();
                    filePath = fileUri.getPath();

                    uriList.add(fileUri);
                    pathList.add(filePath);

                    String ct = "";
                    Log.d("LIST", "Size "+pathList.size());
                    for(int i=0;i<pathList.size();i++){
                        ct=ct+pathList.get(i)+" \n";

                    }

                    ImageView image = new ImageView(getActivity().getApplicationContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200,200);
                    layoutParams.setMargins(20,20,20,20);
                    image.setLayoutParams(layoutParams);
                    image.setBackgroundColor(Color.GRAY);
                    image.setMaxHeight(100);
                    image.setMaxWidth(100);

                    image.setImageURI(fileUri);
                    layout.addView(image);
                    Log.d("LIST", "onActivityResult: "+ct);
                    tvTest.setText("");

                    //imageView.setImageURI(fileUri);



                }

                break;
        }
    }

}
