package com.revature.trailmates.trailflag;

import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.trails.Trail;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "trail_flags")
public class TrailFlag {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "trail_id", referencedColumnName = "id", nullable=false)
    private Trail trailId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable=false)
    private User userId;

    @Column(name = "date_int", nullable=false)
    private long dateInt;

    public TrailFlag() { super(); this.userId= new User(); this.trailId = new Trail();
    }

    public TrailFlag(NewTrailFlagRequest request, Principal user) {
        this.id = UUID.randomUUID().toString();
        this.trailId = new Trail();
        this.trailId.setId(request.getTrailId());
        this.userId = new User();
        this.userId.setId(user.getId());
        this.dateInt = request.getDateInt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Trail getTrailId() {
        return trailId;
    }

    public void setTrailId(Trail trailId) {
        this.trailId = trailId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public long getDateInt() {
        return dateInt;
    }

    public void setDateInt(long dateInt) {
        this.dateInt = dateInt;
    }

    @Override
    public String toString() {
        return "TrailFlag{" +
                "id='" + id + '\'' +
                ", trailId='" + trailId + '\'' +
                ", userId=" + userId +
                ", dateInt=" + dateInt +
                '}';
    }
}
