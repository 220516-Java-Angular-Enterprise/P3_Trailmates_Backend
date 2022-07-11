package com.revature.trailmates.communication.conversation;

import com.revature.trailmates.communication.privatemessages.PrivateMessage;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    private String id;
    @Column (name="name", nullable = false)
    private String name;
//    @OneToOne
//    @JoinColumn(name="recent_message", referencedColumnName = "id", nullable = true)
//    private PrivateMessage recent_message;
    public Conversation(){}
    public Conversation(String id, String name){
        this.id = id;
        this.name = name;
    }

    //region Get/Set

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    public PrivateMessage getRecent_message() {
//        return recent_message;
//    }
//
//    public void setRecent_message(PrivateMessage recent_message) {
//        this.recent_message = recent_message;
//    }

    //endregion


}
