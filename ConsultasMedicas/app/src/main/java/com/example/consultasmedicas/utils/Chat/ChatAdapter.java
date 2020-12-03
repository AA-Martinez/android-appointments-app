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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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

        SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dt2 = new SimpleDateFormat("dd/MM/yyyy");

        if (messages.get(position).getAppointmentId() == sharedPreferences.getInt("id_appointment",0)){
            if (messages.get(position).getAppUserId() == sharedPreferences.getInt("appUserId", 0)){
                holder.otherMessageLayout.setVisibility(View.GONE);
                holder.dateLayout.setVisibility(View.GONE);
                holder.etMyMessage.setText(messages.get(position).getText());
                if (messages.get(position).getCreationTimeStamp() != null){
                    holder.etMyMessageDate.setText(dt1.format(messages.get(position).getCreationTimeStamp()));
                }else{
                    holder.etMyMessageDate.setText(dt1.format(new Date()));
                }
            }else{
                holder.myMessageLayout.setVisibility(View.GONE);
                holder.dateLayout.setVisibility(View.GONE);
                holder.etOtherMessage.setText(messages.get(position).getText());
                holder.etOtherMessageDate.setText(dt1.format(messages.get(position).getCreationTimeStamp()));
            }
        }else {
            holder.dateLayout.setVisibility(View.GONE);
            holder.myMessageLayout.setVisibility(View.GONE);
            holder.otherMessageLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{

        ConstraintLayout dateLayout;
        ConstraintLayout myMessageLayout;
        ConstraintLayout otherMessageLayout;
        TextView etMyMessage, etMyMessageDate;
        TextView etOtherMessage, etOtherMessageDate;
        TextView date;

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

            dateLayout = itemView.findViewById(R.id.dateLayout);
            date = itemView.findViewById(R.id.etdate);
        }
    }

}
