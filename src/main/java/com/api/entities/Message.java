package com.api.entities;

import java.sql.Timestamp;

public class Message {
    Conversation conversation;
    String message;
    Timestamp timeStamp;
    Profile fromUser;

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Profile getFromUser() {
        return fromUser;
    }

    public void setFromUser(Profile fromUser) {
        this.fromUser = fromUser;
    }
}
