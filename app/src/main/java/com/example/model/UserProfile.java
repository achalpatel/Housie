package com.example.model;

public class UserProfile {
    public String userId;
    public String name;
    public String email;
    public String currentRoom;
    public Boolean isActive;

    //Constructors
    public UserProfile() {
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

}
