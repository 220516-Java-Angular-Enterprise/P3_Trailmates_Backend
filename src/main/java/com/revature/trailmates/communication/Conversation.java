package com.revature.trailmates.communication;

import com.revature.trailmates.communication.privatemessages.PrivateMessage;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name="recent_message", referencedColumnName = "id", nullable = true)
    private PrivateMessage recent_message;

    //region Get/Set

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PrivateMessage getRecent_message() {
        return recent_message;
    }

    public void setRecent_message(PrivateMessage recent_message) {
        this.recent_message = recent_message;
    }

    //endregion


}
