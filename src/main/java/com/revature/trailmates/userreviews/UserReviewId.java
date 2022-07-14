package com.revature.trailmates.userreviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.trailmates.user.User;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserReviewId implements Serializable {

    @Column(name = "user_id", nullable = false)
    private String user_id;
    @Column(name = "reviewer_id", nullable = false)
    @JsonIgnore
    private String reviewer_id;

    public UserReviewId() {
    }

    public UserReviewId(String user_id, String reviewer_id) {
        this.user_id = user_id;
        this.reviewer_id = reviewer_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReviewer_id() {
        return reviewer_id;
    }

    public void setReviewer_id(String reviewer_id) {
        this.reviewer_id = reviewer_id;
    }
}
