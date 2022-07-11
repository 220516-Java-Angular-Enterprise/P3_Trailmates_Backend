package com.revature.trailmates.trailflag;

import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "trail_flags")
public class TrailFlag {
    @Id
    private String id;
    @Column(name = "trail_id", nullable=false)
    private String trail_id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;

    @Column(name = "date_int", nullable=false)
    private long date_int;

    public TrailFlag() { super(); this.user_id= new User();
    }

    public TrailFlag(NewTrailFlagRequest request) {
        this.id = UUID.randomUUID().toString();
        this.trail_id = request.getTrail_id();
        this.user_id = new User();
        this.user_id.setId(request.getUser_id());
        this.date_int = request.getDate_int();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrail_id() {
        return trail_id;
    }

    public void setTrail_id(String trail_id) {
        this.trail_id = trail_id;
    }

    public String getUser_id() {
        return user_id.getId();
    }

    public void setUser_id(String user_id) {
        this.user_id.setId(user_id);
    }

    public long getDate_int() {
        return date_int;
    }

    public void setDate_int(long date_int) {
        this.date_int = date_int;
    }

    @Override
    public String toString() {
        return "TrailFlag{" +
                "id='" + id + '\'' +
                ", trail_id='" + trail_id + '\'' +
                ", user_id=" + user_id +
                ", date_int=" + date_int +
                '}';
    }
}
