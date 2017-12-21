package edu.ucsb.cs.cs184.gaucho.gamr;

import java.util.List;

/**
 * Created by william on 12/20/17.
 */

public class Conversation {
    String lowerUser;
    String higherUser;
    List<Message> messages;

    public Conversation() {

    }

    public String getLowerUser() {
        return lowerUser;
    }

    public String getHigherUser() {
        return higherUser;
    }

    public List<Message> getMessages() {
        return messages;
    }

}
