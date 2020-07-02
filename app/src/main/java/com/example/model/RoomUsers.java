package com.example.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class RoomUsers {
    public String roomId;
    public Map<String, Boolean> roomUsers = new HashMap<>();

    //Constructors

    public RoomUsers() {
    }

    public RoomUsers(String roomId) {
        this.roomId = roomId;
    }

    //Getters

    public String getRoomId() {
        return roomId;
    }

    public Map<String, Boolean> getRoomUsers() {
        return roomUsers;
    }

    //Setters

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomUsers(Map<String, Boolean> roomUsers) {
        this.roomUsers = roomUsers;
    }

    //Methods
    @Exclude
    public void addUser(String userId){
        this.roomUsers.put(userId, true);
    }
    @Exclude
    public boolean checkUser(String userId){
        return this.roomUsers.containsKey(userId);
    }
    @Exclude
    public boolean removeUser(String userId){
        return this.roomUsers.containsKey(userId) && this.roomUsers.remove(userId)!=null;
    }
}
