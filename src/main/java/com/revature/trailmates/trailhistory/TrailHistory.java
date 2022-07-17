package com.revature.trailmates.trailhistory;


import com.revature.trailmates.imagedata.ImageData;
import com.revature.trailmates.trails.Trail;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "trailhistory")
public class TrailHistory {

    @Id
    private String id;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "trail_date", nullable = false)
    private Timestamp date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trail_id", referencedColumnName = "id")
    private Trail trail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "url")
    private ImageData image;

    public TrailHistory() {
        super();
    }

    public TrailHistory(String id, String comment, Timestamp date, User user, Trail trail) {
        this.id = id;
        this.comment = comment;
        this.date = date;
        this.user = user;
        this.trail = trail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public ImageData getImage() {
        return image;
    }

    public void setImage(ImageData image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TrailHistory{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", trail='" + trail + '\'' +
                '}';
    }
}
