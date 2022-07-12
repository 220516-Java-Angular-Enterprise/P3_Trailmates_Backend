package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.communication.conversation.Conversation;
import com.revature.trailmates.user.User;

import javax.persistence.*;

@Entity
@Table(name="owned_conversations")
public class OwnedConversation {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name="owner", referencedColumnName = "id", nullable = false)
    private User owner;
    @ManyToOne
    @JoinColumn(name="conversation", referencedColumnName = "id", nullable = true)
    private Conversation conversation;

    //region get/set

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    //endregion

}


