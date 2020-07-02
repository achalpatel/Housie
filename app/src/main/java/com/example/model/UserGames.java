package com.example.model;

import com.google.firebase.database.Exclude;

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

    //Methods
    //Add
    @Exclude
    public void addToGamesPlayed(String gameId) {
        this.gamesPlayed.put(gameId, true);
    }

    @Exclude
    public void addToGamesWon(String gameId) {
        this.gamesWon.put(gameId, true);
    }

    @Exclude
    public void addTickets(String ticketId) {
        this.tickets.put(ticketId, true);
    }

    //Check
    @Exclude
    public boolean checkGamePlayed(String gameId) {
        return this.gamesPlayed.containsKey(gameId);
    }

    @Exclude
    public boolean checkGameWon(String gameId) {
        return this.gamesWon.containsKey(gameId);
    }

    @Exclude
    public boolean checkTicket(String ticketId) {
        return this.tickets.containsKey(ticketId);
    }

    //Remove
    @Exclude
    public boolean removeGamePlayed(String gameId) {
        return this.gamesPlayed.containsKey(gameId) && this.gamesPlayed.remove(gameId) != null;
    }

    @Exclude
    public boolean removeGameWon(String gameId) {
        return this.gamesWon.containsKey(gameId) && this.gamesWon.remove(gameId) != null;
    }

    @Exclude
    public boolean removeTicket(String ticketId) {
        return this.tickets.containsKey(ticketId) && this.tickets.remove(ticketId) != null;
    }


}
