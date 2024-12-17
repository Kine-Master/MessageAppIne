package com.example.messageappine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for displaying a list of contacts in a RecyclerView.
 */
public class ContactListRecyclerViewAdapter extends RecyclerView.Adapter<ContactListViewHolder> {

    private final List<Contact> contactList;
    private final Context context;
    private final OnContactClickListener listener;

    /**
     * Constructor for the ContactListRecyclerViewAdapter.
     *
     * @param contactList The list of contacts to display.
     * @param context     The context of the activity.
     * @param listener    The listener for click events on contact items.
     */
    public ContactListRecyclerViewAdapter(List<Contact> contactList, Context context, OnContactClickListener listener) {
        this.contactList = contactList;
        this.context = context;
        this.listener = listener;
    }

    /**
     * Creates a new ViewHolder instance.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type of the new View.
     * @return A new ContactListViewHolder instance.
     */
    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, parent, false);
        return new ContactListViewHolder(v);
    }

    /**
     * Binds the contact data to the ViewHolder's views.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the contact in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull ContactListViewHolder holder, int position) {
        Contact data = contactList.get(position);
        holder.contacts.setText(data.getName());

        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(data);
        });
    }

    /**
     * Returns the total number of contacts in the list.
     *
     * @return The total number of contacts.
     */
    @Override
    public int getItemCount() {
        return contactList.size();
    }
}