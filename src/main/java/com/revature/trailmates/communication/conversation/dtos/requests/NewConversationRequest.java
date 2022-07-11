package com.revature.trailmates.communication.conversation.dtos.requests;

import java.util.ArrayList;

public class NewConversationRequest {
    private String conversationName;
    private ArrayList<String> userIDs;

    public NewConversationRequest() {}
    public NewConversationRequest(String conversationName, ArrayList<String> userIDs) {
        this.conversationName = conversationName;
        this.userIDs = userIDs;
    }

    //region get/set

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public ArrayList<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(ArrayList<String> userIDs) {
        this.userIDs = userIDs;
    }


    //endregion

}
