package com.example.reservationsystem;

import java.sql.Date;
import java.io.Serializable;

public class BookingItem implements Serializable{
    private String passengerNIC;
    private String passengerName;
    private Date date;
    private String path;
    private String train;
    private int seatCount;
    private int id;

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
// Constructors, getters, and setters

    public BookingItem(String passengerNIC, String passengerName, Date date, String path, String train, int seatCount, int id) {
        this.passengerNIC = passengerNIC;
        this.passengerName = passengerName;
        this.date = date;
        this.path = path;
        this.train = train;
        this.seatCount = seatCount;
        this.id = id;

    }



    public String getPassengerNIC() {
        return passengerNIC;
    }

    public void setPassengerNIC(String passengerNIC) {
        this.passengerNIC = passengerNIC;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}
