package com.example.model;

import java.util.HashMap;
import java.util.Map;

public class UserGames {
    public String userId;
    public Map<String, Boolean> gamesWon = new HashMap<>();
    public Map<String, Boolean> gamesPlayed = new HashMap<>();
    public Map<String, Boolean> tickets = new HashMap<>();

    //Constructors

    public UserGames() {
    }

    public UserGames(String userId) {
        this.userId = userId;
    }
    //Getters

    public String getUserId() {
        return userId;
    }

    public Map<String, Boolean> getGamesWon() {
        return gamesWon;
    }

    public Map<String, Boolean> getGamesPlayed() {
        return gamesPlayed;
    }

    public Map<String, Boolean> getTickets() {
        return tickets;
    }

    //Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setGamesWon(Map<String, Boolean> gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setGamesPlayed(Map<String, Boolean> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setTickets(Map<String, Boolean> tickets) {
        this.tickets = tickets;
    }
}
