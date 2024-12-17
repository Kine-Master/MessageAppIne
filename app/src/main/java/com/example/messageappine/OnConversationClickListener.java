package com.example.messageappine;

/**
 * Interface definition for a callback to be invoked when a conversation is clicked.
 */
public interface OnConversationClickListener {
    /**
     * Called when a conversation item is clicked.
     *
     * @param conversation The conversation that was clicked.
     */
    void onConversationClick(Conversation conversation);
}