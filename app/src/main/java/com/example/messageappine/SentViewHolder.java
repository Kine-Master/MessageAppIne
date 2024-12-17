package com.example.messageappine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * ViewHolder for displaying a sent message item in the RecyclerView.
 * This ViewHolder represents a message sent by the current user.
 * It inflates the `item_sent_message` layout and binds the message content to the TextView.
 */
public class SentViewHolder extends BaseViewHolder {

    /**
     * TextView to display the sent message content.
     */
    TextView sent;

    /**
     * Constructor for the SentViewHolder.
     *
     * @param itemView The View for the ViewHolder.
     */
    public SentViewHolder(@NonNull View itemView) {
        super(itemView);
        sent = itemView.findViewById(R.id.Sent);
    }

    /**
     * Binds the message data to the ViewHolder's views.
     * This method sets the text of the `sent` TextView to the content of the message.
     *
     * @param message The message to bind.
     */
    @Override
    public void bind(Message message) {
        sent.setText(message.getContent());
    }
}