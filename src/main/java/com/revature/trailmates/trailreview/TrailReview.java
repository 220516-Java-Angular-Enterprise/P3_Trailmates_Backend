package com.revature.trailmates.trailreview;

import com.revature.trailmates.trails.Trail;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trail_review")
public class TrailReview {

    @EmbeddedId
    private TrailReviewID trailReviewID;

    @Column(name = "rating", nullable = false)
    private BigDecimal rating;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userID;

    @ManyToOne
    @JoinColumn(name = "trail_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Trail trailID;

    public TrailReview() {
        super();
    }

    public TrailReviewID getTrailReviewID() {
        return trailReviewID;
    }

    public void setTrailReviewID(TrailReviewID trailReviewID) {
        this.trailReviewID = trailReviewID;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Trail getTrailID() {
        return trailID;
    }

    public void setTrailID(Trail trailID) {
        this.trailID = trailID;
    }
}
