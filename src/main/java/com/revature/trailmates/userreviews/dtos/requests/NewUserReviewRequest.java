package com.revature.trailmates.userreviews.dtos.requests;

import com.revature.trailmates.userreviews.UserReviewId;


public class NewUserReviewRequest {
//    private UserReviewId compId;
    private String userId;
    private int rating;
    private String comment;
    private String reviewerId;
    public NewUserReviewRequest() {
    }

    public NewUserReviewRequest(String userId, String reviewerId, int rating, String comment) {
//        this.compId = new UserReviewId(userId,reviewerId);
        this.userId = userId;
        this.reviewerId = reviewerId;
        this.rating = rating;
        this.comment = comment;
    }

//    public UserReviewId getCompId() {
//        return compId;
//    }
//
//    public void setCompId(UserReviewId compId) {
//        this.compId = compId;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "NewUserReviewRequest{" +
                "userId='" + userId + '\'' +
                ", reviewerId='" + reviewerId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
