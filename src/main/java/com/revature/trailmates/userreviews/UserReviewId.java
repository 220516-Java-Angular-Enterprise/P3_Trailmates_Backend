package com.revature.trailmates.userreviews;

import com.revature.trailmates.user.User;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

//@Embeddable
public class UserReviewId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;
    @ManyToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    private User reviewer_id;

    public UserReviewId() {
        super();
        this.user_id= new User();
        this.reviewer_id= new User();
    }

    public UserReviewId(String user_id, String reviewer_id) {
        this.user_id = new User();
        this.user_id.setId(user_id);
        this.reviewer_id = new User();
        this.reviewer_id.setId(reviewer_id);
    }
    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public User getReviewer_id() {
        return reviewer_id;
    }

    public void setReviewer_id(User reviewer_id) {
        this.reviewer_id = reviewer_id;
    }

    @Override
    public String toString() {
        return "UserReviewId{" +
                "user_id=" + user_id +
                ", reviewer_id=" + reviewer_id +
                '}';
    }
}
