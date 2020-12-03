package com.example.consultasmedicas.utils.Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultasmedicas.R;
import com.example.consultasmedicas.model.Message.Message;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageHolder> {

    Context context;
    List<Message> messages;
    List<String> dates =  new ArrayList<>();
    Bitmap bmImg;



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
        Boolean f = true;

        if (messages.get(position).getAppointmentId() == sharedPreferences.getInt("id_appointment",0)){

            if (messages.get(position).getCreationTimeStamp() == null){
                f = false;
            }

            if (f){
                if (!dates.contains(dt2.format(messages.get(position).getCreationTimeStamp()))){
                    dates.add(dt2.format(messages.get(position).getCreationTimeStamp()));
                    holder.date.setText(dt2.format(messages.get(position).getCreationTimeStamp()));
                }else{
                    holder.dateLayout.setVisibility(View.GONE);
                }
            }else{
                if (!dates.contains(dt2.format(new Date()))){
                    dates.add(dt2.format(new Date()));
                    holder.date.setText(dt2.format(new Date()));
                }else{
                    holder.dateLayout.setVisibility(View.GONE);
                }
            }

            if (messages.get(position).getAppUserId() == sharedPreferences.getInt("appUserId", 0)){
                holder.otherMessageLayout.setVisibility(View.GONE);
                holder.etMyMessage.setText(messages.get(position).getText());
                if (messages.get(position).getCreationTimeStamp() != null){
                    holder.etMyMessageDate.setText(dt1.format(messages.get(position).getCreationTimeStamp()));
                }else{
                    holder.etMyMessageDate.setText(dt1.format(new Date()));
                }
            }else{
                holder.myMessageLayout.setVisibility(View.GONE);
                if (messages.get(position).getFiles() != null){
                    if (messages.get(position).getFiles().size() > 0){
                        /*List<SliderItem> sliderItems = new ArrayList<>();
                        for (int i = 0 ; i < messages.get(position).getFiles().size() ; i++){
                            sliderItems.add(new SliderItem("Test", messages.get(position).getFiles().get(i)));
                            Log.e("EEE", sliderItems.get(i).getUrl());
                        }
                        holder.easySlider.setTimer(1000);
                        holder.easySlider.setVisibility(View.VISIBLE);
                        holder.easySlider.setPages(sliderItems);*/
                        holder.ivOtherMessage.setVisibility(View.VISIBLE);
                        Picasso.get().load(messages.get(position).getFiles().get(0)).into(holder.ivOtherMessage);
                    }
                }

                holder.etOtherMessage.setText(messages.get(position).getText());
                holder.etOtherMessageDate.setText(dt1.format(messages.get(position).getCreationTimeStamp()));

            }
        }else {
            holder.dateLayout.setVisibility(View.GONE);
            holder.myMessageLayout.setVisibility(View.GONE);
            holder.otherMessageLayout.setVisibility(View.GONE);
        }

        if(messages.get(position) == messages.get(messages.size()-1)){
            dates.clear();
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{

        EasySlider easySlider;

        ConstraintLayout dateLayout;
        ConstraintLayout myMessageLayout;
        ConstraintLayout otherMessageLayout;
        TextView etMyMessage, etMyMessageDate;
        TextView etOtherMessage, etOtherMessageDate;
        TextView date;
        ImageView ivOtherMessage;

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

            ivOtherMessage = itemView.findViewById(R.id.ivOtherMessage);
        }
    }

}
