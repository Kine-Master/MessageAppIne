//CONVERSATION

package com.example.messageappine;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation in the application.
 */
public class Conversation {
    public int Conversation;
    public Contact contact;
    public List<Message> messages;
    public String time;

    /**
     * Constructor for the Conversation class.
     * Initializes the messages list.
     */
    public Conversation() {
        messages = new ArrayList<>();
    }

    /**
     * Gets the content of the first message in the conversation.
     *
     * @return The content of the first message.
     */
    public String getMessage() {
        return messages.get(0).Content.trim();
    }
}