package com.example.messageappine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder for displaying a conversation item in the RecyclerView.
 */
public class ConversationListViewHolder extends RecyclerView.ViewHolder {

    TextView contact;
    TextView message;

    /**
     * Constructor for the ConversationListViewHolder.
     *
     * @param itemView The View for the ViewHolder.
     */
    public ConversationListViewHolder(@NonNull View itemView) {
        super(itemView);
        contact = itemView.findViewById(R.id.textView4);
        message = itemView.findViewById(R.id.textView5);
    }
}