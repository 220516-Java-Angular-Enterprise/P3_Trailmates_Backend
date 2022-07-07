package com.revature.trailmates.trailhistory.dto.requests;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * this class will be used to create a new trail history
 */
public class NewHistory {

    private String trail_name;
    private String comment;
    private Timestamp date;

    public NewHistory(String trail_name, String comment, Timestamp date) {
        this.trail_name = trail_name;
        this.comment = comment;
        this.date = date;
    }

    public NewHistory() {
    }

    public String getTrail_name() {
        return trail_name;
    }

    public void setTrail_name(String trail_name) {
        this.trail_name = trail_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewHistory{" +
                "trail_name='" + trail_name + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
