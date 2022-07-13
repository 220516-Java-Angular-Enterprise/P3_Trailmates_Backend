package com.revature.trailmates.trailflag.dtos.requests;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class NewTrailFlagRequest {


    private String trailId;

    private long dateInt;
    public NewTrailFlagRequest() { super();
    }

    public NewTrailFlagRequest(String trailId, long dateInt) {
        this.trailId = trailId;
        this.dateInt = dateInt;
    }
    public String getTrailId() {
        return trailId;
    }
    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }
    public long getDateInt() {
        return dateInt;
    }
    public void setDateInt(long dateInt) {
        this.dateInt = dateInt;
    }
    @Override
    public String toString() {
        return "NewTrailFlagRequest{" +
                "trail_id='" + trailId + '\'' +
                ", date_int=" + dateInt +
                '}';
    }
}
