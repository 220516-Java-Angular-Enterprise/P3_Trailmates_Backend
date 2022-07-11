package com.revature.trailmates.userreviews;

import com.revature.trailmates.userreviews.dtos.requests.NewUserReviewRequest;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_reviews")
public class UserReview {

    @EmbeddedId
    private UserReviewId compId;

    @Column(name = "rating", nullable=false)
    private int rating;

    @Column(name = "comment")
    private String comment;

    public UserReview() {
        super();
        this.compId = new UserReviewId();
    }

    public UserReview(UserReviewId compId, int rating, String comment) {
        this.compId = compId;
        this.rating = rating;
        this.comment = comment;
    }
    public UserReview(NewUserReviewRequest request) {
        this.compId = compId;
        this.rating = rating;
        this.comment = comment;
    }
    public UserReviewId getCompId() {
        return compId;
    }

    public void setCompId(UserReviewId compId) {
        this.compId = compId;
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
    public String extractUser_id(){
        return this.compId.getUser_id().getId();
    }
    public String extractReviewer_id(){
        return this.compId.getReviewer_id().getId();
    }
}
