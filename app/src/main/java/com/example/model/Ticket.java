package com.example.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@IgnoreExtraProperties
public class Ticket {
    public String ticketId;
    public String ticketOwner;
    public String ofGame;
    public long rows;
    public long cols;
    public long pointsClaimed;
    public List<List<Long>> ticketData;
    public List<List<Boolean>> checkedData;
    public List<Long> ticketNumbers;
    public static final long ticketNumberRange = 100;

    //Constructors
    public Ticket() {
    }

    public Ticket(long rows, long cols, String game) {
        this.rows = rows;
        this.cols = cols;
        this.ofGame = game;
        this.ticketData = new ArrayList<>();
        this.checkedData = new ArrayList<>();
        this.checkedDataInit();
        this.fillTickNumbers();
        this.generateTicketData();
    }

    //Getters

    public String getTicketId() {
        return ticketId;
    }

    public String getTicketOwner() {
        return ticketOwner;
    }

    public String getGame() {
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

    public List<List<Long>> getTicketData() {
        return ticketData;
    }

    public List<List<Boolean>> getCheckedData() {
        return checkedData;
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

    //Methods
    public void checkData(int i, int j) {
        if (!this.checkedData.get(i).get(j)) {
            this.checkedData.get(i).set(j, true);
        }
    }

    public void checkedDataInit() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.checkedData.get(i).set(j, false);
            }
        }
    }

    public void fillTickNumbers() {
        for (long i = 1; i <= ticketNumberRange; i++) {
            this.ticketNumbers.add(i);
        }
    }

    public void generateTicketData() {
        for (int i = 0; i < this.rows; i++) {
            List<Long> addRow = new ArrayList<>((int) this.cols);
            this.ticketData.add(addRow);
            for (int j = 0; j < this.cols; j++) {
                Collections.shuffle(this.ticketNumbers);
                long value = this.ticketNumbers.remove(0);
                addRow.add(value);
            }
        }
    }

}
