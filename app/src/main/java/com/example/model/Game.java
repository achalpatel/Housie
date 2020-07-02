package com.example.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Game {
    public String gameId;
    public String ofRoom;
    public LocalDateTime startSession;
    public LocalDateTime endSession;
    public String gameHost;
    public long roundPeriodValue;
    public long countdownValue;
    public long ticketRows;
    public long ticketCols;
    public static final long ticketNumberRange = 100;
    public Map<String, Boolean> rules = new HashMap<>();

    //Constructors
    public Game() {
    }

    public Game(String gameId) {
        this.gameId = gameId;
    }

    //Getters

    public String getGameId() {
        return gameId;
    }

    public String getOfRoom() {
        return ofRoom;
    }

    public LocalDateTime getStartSession() {
        return startSession;
    }

    public LocalDateTime getEndSession() {
        return endSession;
    }

    public String getGameHost() {
        return gameHost;
    }

    public long getRoundPeriodValue() {
        return roundPeriodValue;
    }

    public long getCountdownValue() {
        return countdownValue;
    }

    public long getTicketRows() {
        return ticketRows;
    }

    public long getTicketCols() {
        return ticketCols;
    }

    public static long getTicketNumberRange() {
        return ticketNumberRange;
    }

    public Map<String, Boolean> getRules() {
        return rules;
    }

    //Setters

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setOfRoom(String ofRoom) {
        this.ofRoom = ofRoom;
    }

    public void setStartSession(LocalDateTime startSession) {
        this.startSession = startSession;
    }

    public void setEndSession(LocalDateTime endSession) {
        this.endSession = endSession;
    }

    public void setGameHost(String gameHost) {
        this.gameHost = gameHost;
    }

    public void setRoundPeriodValue(long roundPeriodValue) {
        this.roundPeriodValue = roundPeriodValue;
    }

    public void setCountdownValue(long countdownValue) {
        this.countdownValue = countdownValue;
    }

    public void setTicketRows(long ticketRows) {
        this.ticketRows = ticketRows;
    }

    public void setTicketCols(long ticketCols) {
        this.ticketCols = ticketCols;
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
