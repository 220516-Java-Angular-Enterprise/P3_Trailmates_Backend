package com.revature.trailmates.userreviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.trailmates.user.User;
import com.revature.trailmates.userreviews.dtos.requests.NewUserReviewRequest;

import javax.persistence.*;

@Entity
@Table(name = "user_reviews")
public class UserReview {

    @EmbeddedId
    private UserReviewId compId;
    @Column(name = "rating", nullable=false)
    private int rating;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private User userId;
    @ManyToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private User reviewerId;
    public UserReview() {
        super();
    }

    public UserReview(NewUserReviewRequest request) {
        this.compId = new UserReviewId(request.getUserId(),request.getReviewerId());
        this.rating = request.getRating();
        this.comment = request.getComment();
        this.userId = new User();
        this.reviewerId = new User();
        this.userId.setId(request.getUserId());
        this.reviewerId.setId(request.getReviewerId());
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public User getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(User reviewerId) {
        this.reviewerId = reviewerId;
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



    @Override
    public String toString() {
        return "UserReview{" +
                "compId=" + compId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", userId='" + userId + '\'' +
                ", reviewerId='" + reviewerId + '\'' +
                '}';
    }
}
