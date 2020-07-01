package com.example.model;

import java.util.HashMap;
import java.util.Map;

public class UserRooms {
    public String userId;
    public Map<String, Boolean> roomsOwned = new HashMap<>();
    public Map<String, Boolean> roomsHost = new HashMap<>();

    //Constructors

    public UserRooms() {
    }

    public UserRooms(String userId) {
        this.userId = userId;
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
}
