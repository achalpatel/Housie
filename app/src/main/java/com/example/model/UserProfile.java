package com.example.model;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class UserProfile {
    public String userId;
    public String name;
    public String email;
    public String currentRoom;
    public Boolean isActive;

    //Constructors
    public UserProfile() {
    }

    public UserProfile(String userId) {
        this.userId = userId;
    }

    public UserProfile(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    //Getters

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    //Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    //Methods
    @Exclude
    public void mapToUserProfile(HashMap map) {
        this.setName((String) map.get("name"));
        this.setEmail((String) map.get("email"));
        this.setCurrentRoom((String) map.get("currentRoom"));
        this.setIsActive((Boolean) map.get("isActive"));
    }

}
