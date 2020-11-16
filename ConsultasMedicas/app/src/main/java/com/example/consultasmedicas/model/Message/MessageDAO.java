package com.example.consultasmedicas.model.Message;

import com.example.consultasmedicas.model.AppUser.AppUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageDAO {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("creationTimeStamp")
    @Expose
    private String creationTimeStamp;
    @SerializedName("appUser")
    @Expose
    private AppUser appUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(String creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

}