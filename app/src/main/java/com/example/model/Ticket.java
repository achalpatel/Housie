package com.example.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Ticket {
    public String ticketId;
    public String ticketOwner;
    public String ofGame;
    public long rows;
    public long cols;
    public long pointsClaimed;
    public static final long ticketNumberRange = 100;
    public Map<String, Boolean> rules = new HashMap<>();

    //Constructors
    public Ticket() {
    }

    public Ticket(String ticketId, long rows, long cols, String game) {
        this.ticketId = ticketId;
        this.rows = rows;
        this.cols = cols;
        this.ofGame = game;
    }

    //Getters

    public String getTicketId() {
        return ticketId;
    }

    public String getTicketOwner() {
        return ticketOwner;
    }

    public String getOfGame() {
        return ofGame;
    }

    public long getRows() {
        return rows;
    }

    public long getCols() {
        return cols;
    }

    public long getPointsClaimed() {
        return pointsClaimed;
    }

    public static long getTicketNumberRange() {
        return ticketNumberRange;
    }

    public Map<String, Boolean> getRules() {
        return rules;
    }


    //Setters


    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setTicketOwner(String ticketOwner) {
        this.ticketOwner = ticketOwner;
    }

    public void setOfGame(String ofGame) {
        this.ofGame = ofGame;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public void setCols(long cols) {
        this.cols = cols;
    }

    public void setPointsClaimed(long pointsClaimed) {
        this.pointsClaimed = pointsClaimed;
    }

    public void setRules(Map<String, Boolean> rules) {
        this.rules = rules;
    }

    //Methods
    @Exclude
    public void addRule(String rule){
        this.rules.put(rule, true);
    }
    @Exclude
    public boolean checkRule(String rule){
        return this.rules.containsKey(rule);
    }
    @Exclude
    public boolean removeRule(String rule){
        return this.rules.containsKey(rule) && this.rules.remove(rule)!=null;
    }
}
