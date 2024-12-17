package com.example.messageappine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * ViewHolder for displaying a received message item in the RecyclerView.
 * This ViewHolder represents a message received from another user.
 * It inflates the `item_received_message` layout and binds the message content to the TextView.
 */
public class ReceivedViewHolder extends BaseViewHolder {

    /**
     * TextView to display the received message content.
     */
    TextView Receive;

    /**
     * Constructor for the ReceivedViewHolder.
     *
     * @param itemView The View for the ViewHolder.
     */
    public ReceivedViewHolder(@NonNull View itemView) {
        super(itemView);
        Receive = itemView.findViewById(R.id.Receive);
    }

    /**
     * Binds the message data to the ViewHolder's views.
     * This method sets the text of the `Receive` TextView to the content of the message.
     *
     * @param message The message to bind.
     */
    @Override
    public void bind(Message message) {
        Receive.setText(message.getContent());
    }
}