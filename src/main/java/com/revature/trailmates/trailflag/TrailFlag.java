package com.revature.trailmates.trailflag;

import com.revature.trailmates.auth.dtos.response.Principal;
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
    private String trailId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Column(name = "date_int", nullable=false)
    private long dateInt;

    public TrailFlag() { super(); this.userId= new User();
    }

    public TrailFlag(NewTrailFlagRequest request, Principal user) {
        this.id = UUID.randomUUID().toString();
        this.trailId = request.getTrailId();
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

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trail_id) {
        this.trailId = trail_id;
    }

    public String getUserId() {
        return userId.getId();
    }

    public void setUserId(String user_id) {
        this.userId.setId(user_id);
    }

    public long getDateInt() {
        return dateInt;
    }

    public void setDateInt(long date_int) {
        this.dateInt = date_int;
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
