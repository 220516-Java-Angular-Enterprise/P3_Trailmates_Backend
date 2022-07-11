package com.revature.trailmates.trailreview.dtos.requests;

import com.revature.trailmates.trailreview.TrailReview;
import com.revature.trailmates.trailreview.TrailReviewID;
import com.revature.trailmates.trailreview.TrailReviewRepository;

import java.math.BigDecimal;

public class TrailReviewRequest {
    private String comment;
    private BigDecimal rating;

    public TrailReviewRequest() {
        super();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public TrailReview createTrailReview(String userID, String trailID){
        TrailReview trailReview = new TrailReview();
        TrailReviewID trailReviewID = new TrailReviewID(trailID, userID);
        trailReview.setTrailReviewID(trailReviewID);
        trailReview.setComment(getComment());
        trailReview.setRating(getRating());
        return trailReview;
    }
}
