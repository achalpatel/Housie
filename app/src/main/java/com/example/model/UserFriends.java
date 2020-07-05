package com.example.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserFriends {
    public String userId;
    public String userName;
    public Map<String, Boolean> friendsList = new HashMap<>();
    public Map<String, Boolean> friendRequestSent = new HashMap<>();
    public Map<String, Boolean> friendRequestReceived = new HashMap<>();


    //Constructors

    public UserFriends() {
    }

    public UserFriends(String userId) {
        this.userId = userId;
    }

    public UserFriends(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    //Getters

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
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

    public void setUserName(String userName) {
        this.userName = userName;
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

    //Methods
    //Add
    @Exclude
    public void addToFriendList(String friendId) {
        this.friendsList.put(friendId, true);
    }

    @Exclude
    public void addToFriendReqSent(String friendId) {
        this.friendRequestSent.put(friendId, true);
    }

    @Exclude
    public void addToFriendReqReceived(String friendId) {
        this.friendRequestReceived.put(friendId, true);
    }

    //Check
    @Exclude
    public boolean checkFriend(String friendId) {
        return this.friendsList.containsKey(friendId);
    }

    @Exclude
    public boolean checkFriendReqSent(String friendId) {
        return this.friendRequestSent.containsKey(friendId);
    }

    @Exclude
    public boolean checkFriendReqReceived(String friendId) {
        return this.friendRequestReceived.containsKey(friendId);
    }

    //Remove
    @Exclude
    public boolean removeFriend(String friendId) {
        return this.friendsList.containsKey(friendId) && this.friendsList.remove(friendId) != null;
    }

    @Exclude
    public boolean removeFriendReqSent(String friendId) {
        return this.friendRequestSent.containsKey(friendId) && this.friendRequestSent.remove(friendId) != null;
    }

    @Exclude
    public boolean removeFriendReqReceived(String friendId) {
        return this.friendRequestReceived.containsKey(friendId) && this.friendRequestReceived.remove(friendId) != null;
    }
}
