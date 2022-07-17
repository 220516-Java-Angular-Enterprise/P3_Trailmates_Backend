package com.revature.trailmates.trailhistory.dto.requests;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * this class will be used to create a new trail history
 */
public class NewHistoryRequest {

    private String trail_name;
    private String comment;
    private String date;
    private String imageURL;

    public NewHistoryRequest(String trail_name, String comment, String date, String imageURL) {
        this.trail_name = trail_name;
        this.comment = comment;
        this.date = date;
        this.imageURL = imageURL;
    }

    public NewHistoryRequest() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "NewHistoryRequest{" +
                "trail_name='" + trail_name + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
