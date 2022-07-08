package com.revature.trailmates.trailflag.dtos.requests;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class NewTrailFlagRequest {


    private String trail_id;

    private String user_id;
    private long date_int;
    public NewTrailFlagRequest() { super();
    }

    public NewTrailFlagRequest(String trail_id, String user_id, long date_int) {
        this.trail_id = trail_id;
        this.user_id = user_id;
        this.date_int = date_int;
    }

    public String getTrail_id() {
        return trail_id;
    }

    public void setTrail_id(String trail_id) {
        this.trail_id = trail_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getDate_int() {
        return date_int;
    }

    public void setDate_int(long date_int) {
        this.date_int = date_int;
    }

    @Override
    public String toString() {
        return "NewTrailFlagRequest{" +
                "trail_id='" + trail_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", date_int=" + date_int +
                '}';
    }
}
