package com.example.model;

import java.util.HashMap;
import java.util.Map;

public class UserFriends {
    public String userId;
    public Map<String, Boolean> friendsList = new HashMap<>();
    public Map<String, Boolean> friendRequestSent = new HashMap<>();
    public Map<String, Boolean> friendRequestReceived = new HashMap<>();


    //Constructors

    public UserFriends() {
    }

    public UserFriends(String userId) {
        this.userId = userId;
    }
    //Getters

    public String getUserId() {
        return userId;
    }

    public Map<String, Boolean> getFriendsList() {
        return friendsList;
    }

    public Map<String, Boolean> getFriendRequestSent() {
        return friendRequestSent;
    }

    public Map<String, Boolean> getFriendRequestReceived() {
        return friendRequestReceived;
    }

    //Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFriendsList(Map<String, Boolean> friendsList) {
        this.friendsList = friendsList;
    }

    public void setFriendRequestSent(Map<String, Boolean> friendRequestSent) {
        this.friendRequestSent = friendRequestSent;
    }

    public void setFriendRequestReceived(Map<String, Boolean> friendRequestReceived) {
        this.friendRequestReceived = friendRequestReceived;
    }
}
