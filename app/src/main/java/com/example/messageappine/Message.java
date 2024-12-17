package com.example.messageappine;

/**
 * Represents a message in the application.
 */
public class Message {

    public String To;
    public String From;
    public String Content;
    public int MessageId;
    public boolean isReceived;
    private boolean isSent;
    private String Timestamp;


    /**
     * Constructor for the Message class.
     *
     * @param messageId  The ID of the message.
     * @param content    The content of the message.
     * @param isSent     Indicates whether the message was sent by the current user.
     * @param isReceived Indicates whether the message was received by the current user.
     * @param from       The sender of the message.
     * @param to         The recipient of the message.
     */
    public Message(int messageId, String content, boolean isSent, boolean isReceived, String from, String to) {
        this.MessageId = messageId;
        this.Content = content;
        this.isSent = isSent;
        this.isReceived = isReceived;
        this.From = from;
        this.To = to;
    }

    /**
     * Gets the ID of the message.
     *
     * @return The ID of the message.
     */
    public int getMessageId() {
        return MessageId;
    }

    /**
     * Sets the ID of the message.
     *
     * @param messageId The ID of the message to set.
     */
    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    /**
     * Gets the sender of the message.
     *
     * @return The sender of the message.
     */
    public String getFrom() {
        return From.trim();
    }

    /**
     * Sets the sender of the message.
     *
     * @param from The sender of the message to set.
     */
    public void setFrom(String from) {
        From = from;
    }

    /**
     * Gets the recipient of the message.
     *
     * @return The recipient of the message.
     */
    public String getTo() {
        return To.trim();
    }

    /**
     * Sets the recipient of the message.
     *
     * @param to The recipient of the message to set.
     */
    public void setTo(String to) {
        To = to;
    }

    /**
     * Gets the content of the message.
     *
     * @return The content of the message.
     */
    public String getContent() {
        return Content;
    }

    /**
     * Sets the content of the message.
     *
     * @param content The content of the message to set.
     */
    public void setContent(String content) {
        Content = content;
    }

    /**
     * Gets the timestamp of the message.
     *
     * @return The timestamp of the message.
     */
    public String setTimestamp() {
        return Timestamp;
    }

    /**
     * Sets the timestamp of the message.
     *
     * @param timestamp The timestamp of the message to set.
     */
    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    /**
     * Sets whether the message was sent by the current user.
     *
     * @param isSent True if the message was sent by the current user, false otherwise.
     */
    public void setSent(boolean isSent) {
        this.isSent = isSent;
    }

    /**
     * Checks whether the message was sent by the current user.
     *
     * @return True if the message was sent by the current user, false otherwise.
     */
    public boolean isSent() {
        return isSent;
    }
}