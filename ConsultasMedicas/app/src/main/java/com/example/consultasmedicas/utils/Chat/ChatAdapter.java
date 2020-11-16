package com.example.consultasmedicas.utils.Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Message.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageHolder> {

    Context context;
    List<Message> messages;

    SharedPreferences sharedPreferences;

    public ChatAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_message, viewGroup, false);
        sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {

        if (messages.get(position).getAppointmentId() == sharedPreferences.getInt("id_appointment",0)){
            if (messages.get(position).getAppUserId() == sharedPreferences.getInt("appUserId", 0)){
                holder.otherMessageLayout.setVisibility(View.GONE);
                holder.etMyMessage.setText(messages.get(position).getText());
                //holder.etMyMessageDate.setText(messages.get(position).getCreationTimeStamp().toString());
            }else{
                holder.myMessageLayout.setVisibility(View.GONE);
                holder.etOtherMessage.setText(messages.get(position).getText());
                //holder.etOtherMessageDate.setText(messages.get(position).getCreationTimeStamp().toString());
            }
        }else {
            holder.myMessageLayout.setVisibility(View.GONE);
            holder.otherMessageLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{

        ConstraintLayout myMessageLayout;
        ConstraintLayout otherMessageLayout;
        TextView etMyMessage, etMyMessageDate;
        TextView etOtherMessage, etOtherMessageDate;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            // Layouts
            myMessageLayout = itemView.findViewById(R.id.MyMessage);
            otherMessageLayout = itemView.findViewById(R.id.OtherMessage);
            // Mis mensajes
            etMyMessage = itemView.findViewById(R.id.etMyMessage);
            etMyMessageDate = itemView.findViewById(R.id.etMyDateMessage);
            // Mensajes de otros
            etOtherMessage = itemView.findViewById(R.id.etMessage);
            etOtherMessageDate = itemView.findViewById(R.id.etDateMessage);
        }
    }

}
