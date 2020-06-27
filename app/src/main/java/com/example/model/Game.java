package com.example.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class Game {
    public String gameId;
    public String ofRoom;
    public long roundPeriod;
    public long countdown;
    public Map<String, Boolean> gameTickets;
    //<TicketId, UserId>
    public Map<String, String> ticketUsers;

}
