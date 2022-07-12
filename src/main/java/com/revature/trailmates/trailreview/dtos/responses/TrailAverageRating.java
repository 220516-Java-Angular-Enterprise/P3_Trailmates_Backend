package com.revature.trailmates.trailreview.dtos.responses;

import java.math.BigDecimal;

public class TrailAverageRating {
    private String trailID;
    private BigDecimal ratingAvg;
    private int ratingCount;

    public TrailAverageRating(String trailID, BigDecimal ratingAvg, int ratingCount) {
        this.trailID = trailID;
        this.ratingAvg = ratingAvg;
        this.ratingCount = ratingCount;
    }

    public String getTrailID() {
        return trailID;
    }

    public void setTrailID(String trailID) {
        this.trailID = trailID;
    }

    public TrailAverageRating() {
    }

    public BigDecimal getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(BigDecimal ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
