package com.example.messageappine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for displaying messages in a RecyclerView.
 */
public class DisConRecyclerView extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    private final List<Message> messages;
    private final Context context;

    /**
     * Constructor for the DisConRecyclerView.
     *
     * @param messages The list of messages to display.
     * @param context  The context of the activity.
     */
    public DisConRecyclerView(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    /**
     * Creates a new ViewHolder instance.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder instance.
     */
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sent_message, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_received_message, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    /**
     * Binds the message data to the ViewHolder's views.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the message in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message);
    }

    /**
     * Returns the view type of the item at the given position.
     *
     * @param position The position of the item.
     * @return The view type of the item.
     */
    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        return message.isSent() ? VIEW_TYPE_SENT : VIEW_TYPE_RECEIVED;
    }

    /**
     * Returns the total number of messages in the list.
     *
     * @return The total number of messages.
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * Adds a new message to the list and updates the RecyclerView.
     *
     * @param newMessage The new message to add.
     */
    public void addMessage(Message newMessage) {
        messages.add(newMessage);
        notifyItemInserted(messages.size() - 1);
    }
}