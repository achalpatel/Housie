package com.example.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class User {
    public String userId;
    public String name;
    public String email;
    public Map<String, Boolean> friendsList = new HashMap<>();
    public Map<String, Boolean> friendRequestList = new HashMap<>();
    public Map<String, Boolean> roomsOwned = new HashMap<>();
    public Map<String, Boolean> roomsHost = new HashMap<>();
    public String currentRoom;
    public Map<String, Boolean> gamesWon = new HashMap<>();
    public Boolean isActive;


    //Constructors
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }


    //Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Map<String, Boolean> getFriendsList() {
        return friendsList;
    }

    public Map<String, Boolean> getFriendRequestList() {
        return friendRequestList;
    }

    public Map<String, Boolean> getRoomsOwned() {
        return roomsOwned;
    }

    public Map<String, Boolean> getRoomsHost() {
        return roomsHost;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public Boolean isActive() {
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

    public void setFriendsList(Map<String, Boolean> friendsList) {
        this.friendsList = friendsList;
    }

    public void setFriendRequestList(Map<String, Boolean> friendRequestList) {
        this.friendRequestList = friendRequestList;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    //Methods

    public void addToFriendsList(String userId) {
        this.friendsList.put(userId, true);
    }

    public void addToFriendRequestList(String userId) {
        this.friendRequestList.put(userId, true);
    }

    public void addToRoomsOwned(String roomId) {
        this.roomsOwned.put(roomId, true);
    }

    public void addToRoomsHost(String roomId) {
        this.roomsHost.put(roomId, true);
    }

}
