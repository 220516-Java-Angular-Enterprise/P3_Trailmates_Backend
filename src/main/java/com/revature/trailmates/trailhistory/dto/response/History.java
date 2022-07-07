package com.revature.trailmates.trailhistory.dto.response;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * this class will be used to return history objects
 */
public class History {

    private String trailname;
    private String partnername;
    private String comment;
    private Timestamp trail_date;

    public History() {
    }

    public History(String trailName, String partnerName, String comment, Timestamp date) {
        this.trailname = trailName;
        this.partnername = partnerName;
        this.comment = comment;
        this.trail_date = date;
    }

    public String getTrailName() {
        return trailname;
    }

    public void setTrailName(String trailName) {
        this.trailname = trailName;
    }

    public String getPartnerName() {
        return partnername;
    }

    public void setPartnerName(String partnerName) {
        this.partnername = partnerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getDate() {
        return trail_date;
    }

    public void setDate(Timestamp date) {
        this.trail_date = date;
    }

    @Override
    public String toString() {
        return "History{" +
                "trailName='" + partnername + '\'' +
                ", partnerName='" + trailname + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + trail_date +
                '}';
    }
}
