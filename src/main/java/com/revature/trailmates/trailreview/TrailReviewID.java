package com.revature.trailmates.trailreview;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TrailReviewID implements Serializable {
    @Column(name = "trail_id", nullable = false)
    private String trailID;

    @Column(name = "user_id", nullable = false)
    private String userID;

    public TrailReviewID() {
        super();
    }

    public TrailReviewID(String trailID, String userID) {
        this.trailID = trailID;
        this.userID = userID;
    }

    public String getTrailID() {
        return trailID;
    }

    public void setTrailID(String trailID) {
        this.trailID = trailID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
