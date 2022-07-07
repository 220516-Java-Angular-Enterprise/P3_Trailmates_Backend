package com.revature.trailmates.trailhistory.dto.response;

import com.revature.trailmates.trailhistory.TrailHistory;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * this class will be used to return history objects
 */
public class History {

    private String trailname;
    private String partnername;
    private String comment;
    private Date trail_date;

    public History() {
    }

    public History(String trailName, String partnerName, String comment, Date date) {
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

    public Date getDate() {
        return trail_date;
    }

    public void setDate(Date date) {
        this.trail_date = date;
    }

    public History extractTrail(TrailHistory trail){
        this.comment = trail.getComment();
        this.trailname = trail.getTrail().getName();
        this.trail_date = new Date(trail.getDate().getTime());
        this.partnername = trail.getUser().getUsername();
        return this;
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
