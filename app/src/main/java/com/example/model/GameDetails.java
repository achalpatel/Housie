package com.example.model;

import com.google.firebase.database.Exclude;

import java.util.Map;

public class GameDetails {
    public String gameId;
    public Map<String, Boolean> gameTickets;
    //{UserId : TicketId}
    public Map<String, String> userTickets;

    //Constructors
    public GameDetails() {
    }

    public GameDetails(String gameId) {
        this.gameId = gameId;
    }

    //Getters

    public String getGameId() {
        return gameId;
    }

    public Map<String, Boolean> getGameTickets() {
        return gameTickets;
    }

    public Map<String, String> getUserTickets() {
        return userTickets;
    }

    //Setters

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setGameTickets(Map<String, Boolean> gameTickets) {
        this.gameTickets = gameTickets;
    }

    public void setUserTickets(Map<String, String> userTickets) {
        this.userTickets = userTickets;
    }

    //Methods
    //Add
    @Exclude
    public void addGameTicket(String ticketId) {
        this.gameTickets.put(ticketId, true);
    }

    @Exclude
    public void addUserTicket(String userId, String ticketId) {
        this.userTickets.put(userId, ticketId);
    }

    //Check
    @Exclude
    public boolean checkTicket(String ticketId) {
        return this.gameTickets.containsKey(ticketId);
    }

    @Exclude
    public boolean checkUserTicket(String userId) {
        return this.gameTickets.containsKey(userId);
    }

    //Get user ticket if Exist
    @Exclude
    public String getUserTicket(String userId) {
        return this.userTickets.containsKey(userId) ? this.userTickets.get(userId) : "";
    }

    //Remove
    @Exclude
    public boolean removeTicket(String ticketId) {
        return this.gameTickets.containsKey(ticketId) && this.gameTickets.remove(ticketId) != null;
    }

    @Exclude
    public boolean removeUserTicket(String userId) {
        return this.userTickets.containsKey(userId) && this.userTickets.remove(userId) != null;
    }

}
