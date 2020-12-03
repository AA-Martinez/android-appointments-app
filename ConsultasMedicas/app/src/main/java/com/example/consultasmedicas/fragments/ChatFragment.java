package com.example.consultasmedicas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.model.Message.Message;
import com.example.consultasmedicas.utils.Apis;
import com.example.consultasmedicas.utils.Chat.ChatAdapter;
import com.example.consultasmedicas.utils.Doctor.DoctorService;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

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
    ImageButton btnSendMessage;

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

    public void initComponents(View view){

        rvChatField = view.findViewById(R.id.rvChatField);
        tvDoctorNameChat = view.findViewById(R.id.tvDoctorNameChat);
        tvDoctorDegreeChat = view.findViewById(R.id.tvDoctorDegreeChat);
        etChatMessage = view.findViewById(R.id.etChatMessage);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);

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
                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
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
                message.setAppointmentId(sharedPreferences.getInt("id_appointment",0));
                message.setAppUserId(sharedPreferences.getInt("appUserId", 0));
                message.setText(etChatMessage.getText().toString());
                FirebaseFirestore.getInstance().collection("messages").add(message);
                Log.e("Prueba", String.valueOf(message.getCreationTimeStamp()));
                etChatMessage.setText("");
            }
        });
    }

    public void getDoctorInformation(View view){
        sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseBody> call = doctorService.getDoctor(sharedPreferences.getInt("id_doctor", 0), sharedPreferences.getString("auth-token",""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        DoctorDAO doctorDAO = gson.fromJson(jsonObject.getJSONObject("data").toString(), DoctorDAO.class);
                        Log.d("RESPONSE", "onResponse: "+doctorDAO.getAppUser().getFirstName()+" "+doctorDAO.getAppUser().getLastName());
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


}
