package com.example.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserRooms {
    public String userId;
    public String userName;
    public Map<String, Boolean> roomsOwned = new HashMap<>();
    public Map<String, Boolean> roomsHost = new HashMap<>();

    //Constructors

    public UserRooms() {
    }

    public UserRooms(String userId) {
        this.userId = userId;
    }

    public UserRooms(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    //Getters

    public String getUserId() {
        return userId;
    }

    public Map<String, Boolean> getRoomsOwned() {
        return roomsOwned;
    }

    public Map<String, Boolean> getRoomsHost() {
        return roomsHost;
    }

    public String getUserName() {
        return userName;
    }

    //Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoomsOwned(Map<String, Boolean> roomsOwned) {
        this.roomsOwned = roomsOwned;
    }

    public void setRoomsHost(Map<String, Boolean> roomsHost) {
        this.roomsHost = roomsHost;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //Methods
    //Add
    @Exclude
    public void addRoomsOwned(String roomId) {
        this.roomsOwned.put(roomId, true);
    }

    @Exclude
    public void addRoomsHost(String roomId) {
        this.roomsHost.put(roomId, true);
    }

    //Check
    @Exclude
    public boolean checkRoomOwned(String roomId) {
        return this.roomsOwned.containsKey(roomId);
    }

    @Exclude
    public boolean checkRoomHost(String roomId) {
        return this.roomsHost.containsKey(roomId);
    }

    //Remove
    @Exclude
    public boolean removeRoomOwned(String roomId) {
        return this.roomsOwned.containsKey(roomId) && this.roomsOwned.remove(roomId) != null;
    }

    @Exclude
    public boolean removeRoomHost(String roomId) {
        return this.roomsHost.containsKey(roomId) && this.roomsHost.remove(roomId) != null;
    }
}
