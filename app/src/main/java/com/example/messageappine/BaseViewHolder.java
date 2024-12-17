package com.example.messageappine;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Base ViewHolder class for the RecyclerView.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    /**
     * Constructor for the BaseViewHolder.
     *
     * @param itemView The View for the ViewHolder.
     */
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * Binds the message data to the ViewHolder's views.
     *
     * @param message The message to bind.
     */
    public abstract void bind(Message message);
}