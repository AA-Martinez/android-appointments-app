package com.example.consultasmedicas.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.model.Message.Message;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Chat.ChatAdapter;
import com.example.consultasmedicas.utils.Doctor.DoctorService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    RecyclerView rvChatField;
    TextView tvDoctorNameChat, tvDoctorDegreeChat;
    EditText etChatMessage;
    ImageButton btnSendMessage, btnSendImage;
    public static final int PICKFILE_RESULT_CODE = 1;

    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private MediaFile files;

    SharedPreferences sharedPreferences;
    DoctorService doctorService = Apis.doctorService();

    List<Message> messages;
    ChatAdapter chatAdapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        initComponents(view);
        return view;
    }

    public void initComponents(View view) {

        rvChatField = view.findViewById(R.id.rvChatField);
        tvDoctorNameChat = view.findViewById(R.id.tvDoctorNameChat);
        tvDoctorDegreeChat = view.findViewById(R.id.tvDoctorDegreeChat);
        etChatMessage = view.findViewById(R.id.etChatMessage);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);
        btnSendImage = view.findViewById(R.id.btnSendImage);

        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(view.getContext(), messages);

        rvChatField.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        rvChatField.setAdapter(chatAdapter);
        rvChatField.setHasFixedSize(true);
        rvChatField.getRecycledViewPool().setMaxRecycledViews(0, 0);

        sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        FirebaseFirestore.getInstance().collection("messages").orderBy("creationTimeStamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                messages.add(documentChange.getDocument().toObject(Message.class));
                                chatAdapter.notifyDataSetChanged();
                                rvChatField.smoothScrollToPosition(messages.size());
                            }
                        }
                    }
                });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etChatMessage.length() == 0)
                    return;
                Message message = new Message();
                message.setAppointmentId(sharedPreferences.getInt("id_appointment", 0));
                message.setAppUserId(sharedPreferences.getInt("appUserId", 0));
                message.setText(etChatMessage.getText().toString());

                if (files != null){

                    StorageReference filesRef = storageReference.child("images/" + files.getUri());
                    Log.d("FILE URI", ""+files.getUri());
                    UploadTask uploadTask = filesRef.putFile(files.getUri());
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
                FirebaseFirestore.getInstance().collection("messages").add(message);
                Log.e("Prueba", String.valueOf(message.getCreationTimeStamp()));
                etChatMessage.setText("");
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
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
                        .setSuffixes("txt", "pdf", "zip", "rar", "doc", "docx")
                        .setMaxSelection(3)
                        .setSkipZeroSizeFiles(true)
                        .build());
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });
    }

    public void getDoctorInformation(View view) {
        sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = doctorService.getDoctor(sharedPreferences.getInt("id_doctor", 0), sharedPreferences.getString("auth-token", ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        DoctorDAO doctorDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), DoctorDAO.class);
                        Log.d("RESPONSE", "onResponse: " + doctorDAO.getAppUser().getFirstName() + " " + doctorDAO.getAppUser().getLastName());
                        tvDoctorNameChat.setText(doctorDAO.getAppUser().getFirstName() + " " + doctorDAO.getAppUser().getLastName());
                        tvDoctorDegreeChat.setText(doctorDAO.getDegrees().get(1).toString());
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", t.getMessage());

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {

                    break;
                }
        }

    }

}
