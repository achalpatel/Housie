package com.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDetails {
    public String ticketId;
    public Map<String, ArrayList<Long>> ticketData = new HashMap<>();
    public Map<String, ArrayList<Boolean>> checkedData = new HashMap<>();

    //Constructors

    public TicketDetails() {
    }

    public TicketDetails(String ticketId) {
        this.ticketId = ticketId;
    }
    //Getters

    public String getTicketId() {
        return ticketId;
    }

    public Map<String, ArrayList<Long>> getTicketData() {
        return ticketData;
    }

    public Map<String, ArrayList<Boolean>> getCheckedData() {
        return checkedData;
    }

    //Setters

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setTicketData(Map<String, ArrayList<Long>> ticketData) {
        this.ticketData = ticketData;
    }

    public void setCheckedData(Map<String, ArrayList<Boolean>> checkedData) {
        this.checkedData = checkedData;
    }
}
