package com.example.consultasmedicas.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.consultasmedicas.Navigation;
import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Appointment.Appointment;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Appointment.AppointmentService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateConsultFragment extends Fragment{
    private AppointmentService appointmentService= Apis.appointmentService();
    private String authToken;
    private Appointment appointment;
    public static final int PICKFILE_RESULT_CODE = 1;

    private TextInputEditText etSymptoms;
    private FloatingActionButton fabtnAddFile;
    private ExtendedFloatingActionButton efabNext;
    private ImageView imageView;
    private TextView tvTest;
    private LinearLayout layout;


    private ProgressDialog progressDialog;
    private ArrayList<MediaFile> files;
    private StorageReference storageReference;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth-token","");
        appointment = new Appointment();

        progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        storageReference = FirebaseStorage.getInstance().getReference();
        Log.e("StorageReference", "onCreateView: " + storageReference);
        View view = inflater.inflate(R.layout.create_consult_fragment, container, false);

        etSymptoms = (TextInputEditText) view.findViewById(R.id.symptomsEditText);
        fabtnAddFile = (FloatingActionButton) view.findViewById(R.id.add_files_floating_button);
        efabNext = (ExtendedFloatingActionButton) view.findViewById(R.id.next_floating_button);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        layout = (LinearLayout)view.findViewById(R.id.image_layout);
        tvTest = (TextView) view.findViewById(R.id.test_text_view);

        fabtnAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .setShowVideos(true)
                        .setShowFiles(true)
                        .enableImageCapture(true)
                        .enableVideoCapture(true)
                        .setSuffixes("txt","pdf","zip","rar","doc","docx")
                        .setMaxSelection(3)
                        .setSkipZeroSizeFiles(true)
                        .build());
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });
        efabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointment.setMessage(etSymptoms.getText().toString());
                appointment.setPatientId("1");

                Call<ResponseBody> call = appointmentService.createAppointment(appointment,authToken);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.d("APPOINTMENT", "onResponse: "+response.body().toString());

                            if (!files.isEmpty()){

                                for (int i = 0; i < files.size(); i++) {
                                    StorageReference filesRef = storageReference.child("images/" + files.get(i).getUri());
                                    Log.d("FILE URI", ""+files.get(i).getUri());
                                    UploadTask uploadTask = filesRef.putFile(files.get(i).getUri());
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        }
                                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                            Log.d("PROGRESS", "Upload is " + progress + "% done");
                                        }
                                    });

                                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                        @Override
                                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                            if (!task.isSuccessful()) {
                                                throw task.getException();
                                            }

                                            // Continue with the task to get the download URL
                                            return filesRef.getDownloadUrl();
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                Uri downloadUri = task.getResult();
                                                Log.d("URL DOWNLOAD",downloadUri+"");
                                            } else {
                                                // Handle failures
                                                // ...
                                            }
                                        }
                                    });

                                }

                            }



                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                ((Navigation) getActivity()).navigateTo(new PaymentFragment(), true);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                    String ct = "";
                    Log.d("FILES LIST", "Size " + files.size());
                    for (int i = 0; i < files.size(); i++) {
                        ImageView image = new ImageView(getActivity().getApplicationContext());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
                        layoutParams.setMargins(10, 10, 10, 10);
                        image.setLayoutParams(layoutParams);
                        image.setBackgroundColor(Color.GRAY);
                        image.setMaxHeight(100);
                        image.setMaxWidth(100);
                        image.setImageURI(files.get(i).getUri());
                        layout.addView(image);
                        ct = ct + files.get(i).getUri() + " \n";


                    }

                    Log.d("LIST", "onActivityResult: " + ct);
                    tvTest.setText("");
                }
                break;
        }
    }

}
