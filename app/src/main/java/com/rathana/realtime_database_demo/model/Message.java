package com.rathana.realtime_database_demo.model;

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Message {
    private String userId;
    private String message;
    private Long dateCreated;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("message",message);
        map.put("dateCreated",dateCreated);

        return  map;
    }


}
