package com.example.model;

import com.google.firebase.database.Exclude;

import java.util.HashSet;
import java.util.Set;

public class UserFriends {
    public String userId;
    public String userName;
    public Set<String> friendsList = new HashSet<>();
    public Set<String> friendRequestSent = new HashSet<>();
    public Set<String> friendRequestReceived = new HashSet<>();


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

    public Set<String> getFriendsList() {
        return friendsList;
    }

    public Set<String> getFriendRequestSent() {
        return friendRequestSent;
    }

    public Set<String> getFriendRequestReceived() {
        return friendRequestReceived;
    }

    //Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFriendsList(Set<String> friendsList) {
        this.friendsList = friendsList;
    }

    public void setFriendRequestSent(Set<String> friendRequestSent) {
        this.friendRequestSent = friendRequestSent;
    }

    public void setFriendRequestReceived(Set<String> friendRequestReceived) {
        this.friendRequestReceived = friendRequestReceived;
    }

    //Methods
    //Add
    @Exclude
    public void addToFriendList(String friendId) {
        this.friendsList.add(friendId);
    }

    @Exclude
    public void addToFriendReqSent(String friendId) {
        this.friendRequestSent.add(friendId);
    }

    @Exclude
    public void addToFriendReqReceived(String friendId) {
        this.friendRequestReceived.add(friendId);
    }

    //Check
    @Exclude
    public boolean checkFriend(String friendId) {
        return this.friendsList.contains(friendId);
    }

    @Exclude
    public boolean checkFriendReqSent(String friendId) {
        return this.friendRequestSent.contains(friendId);
    }

    @Exclude
    public boolean checkFriendReqReceived(String friendId) {
        return this.friendRequestReceived.contains(friendId);
    }

    //Remove
    @Exclude
    public boolean removeFriend(String friendId) {
        return this.friendsList.remove(friendId);
    }

    @Exclude
    public boolean removeFriendReqSent(String friendId) {
        return this.friendRequestSent.remove(friendId);
    }

    @Exclude
    public boolean removeFriendReqReceived(String friendId) {
        return this.friendRequestReceived.remove(friendId);
    }
}
