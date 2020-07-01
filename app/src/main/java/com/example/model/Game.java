package com.example.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.time.LocalDateTime;
import java.util.Map;

@IgnoreExtraProperties
public class Game {
    public String gameId;
    public String ofRoom;
    public long roundPeriodValue;
    public long countdownValue;
    public Map<String, Boolean> gameTickets;
    //{UserId : TicketId}
    public Map<String, String> userTickets;
    public LocalDateTime startSession;
    public LocalDateTime endSession;
    public String gameHost;

}
