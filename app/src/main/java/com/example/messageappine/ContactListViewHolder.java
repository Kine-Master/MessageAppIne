package com.example.messageappine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder for displaying a contact item in the RecyclerView.
 */
public class ContactListViewHolder extends RecyclerView.ViewHolder {

    TextView contacts;

    /**
     * Constructor for the ContactListViewHolder.
     *
     * @param itemView The View for the ViewHolder.
     */
    public ContactListViewHolder(@NonNull View itemView) {
        super(itemView);
        contacts = itemView.findViewById(R.id.textView8);
    }
}