package com.example.model;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;


@IgnoreExtraProperties
public class Room {
    public String roomId;
    public String roomName;
    public String roomOwner;
    public String roomHost;
    public String roomPassword;
    public boolean isOpen;
    public long roomSize;
    public long maxRoomSize;
    public boolean isLocked;
    public Date roomCreateDate;
    public Date roomCloseDate;

    //Constructors
    public Room() {
        this.isOpen = true;
        this.isLocked = false;
    }

    public Room(String roomId, String roomName, String roomOwner) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomOwner = roomOwner;
        this.isOpen = true;
        this.isLocked = false;
    }

    public Room(String roomId, String roomName, String roomOwner, String roomPassword) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomOwner = roomOwner;
        this.roomPassword = roomPassword;
        this.isOpen = false;
        this.isLocked = false;
    }

    //Getters

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public String getRoomHost() {
        return roomHost;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public long getRoomSize() {
        return roomSize;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public long getMaxRoomSize() {
        return maxRoomSize;
    }


    //Setters

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomOwner(String roomOwner) {
        this.roomOwner = roomOwner;
    }

    public void setRoomHost(String roomHost) {
        this.roomHost = roomHost;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setRoomSize(long roomSize) {
        this.roomSize = roomSize;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setMaxRoomSize(long maxRoomSize) {
        this.maxRoomSize = maxRoomSize;
    }

}
