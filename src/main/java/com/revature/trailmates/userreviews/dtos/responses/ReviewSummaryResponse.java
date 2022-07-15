package com.revature.trailmates.userreviews.dtos.responses;

import com.revature.trailmates.userreviews.UserReview;

import java.util.List;

public class ReviewSummaryResponse {

    private double avgScore;
    private List<UserReview> reviews;

    public ReviewSummaryResponse() {
    }

    public ReviewSummaryResponse(double avgScore, List<UserReview> reviews) {
        this.avgScore = avgScore;
        this.reviews = reviews;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }

    public List<UserReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<UserReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ReviewSummaryResponse{" +
                "avgScore=" + avgScore +
                ", reviews=" + reviews +
                '}';
    }
}
