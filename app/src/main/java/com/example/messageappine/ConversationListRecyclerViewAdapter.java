package com.example.messageappine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for displaying a list of conversations in a RecyclerView.
 */
public class ConversationListRecyclerViewAdapter extends RecyclerView.Adapter<ConversationListViewHolder> {

    public List<Conversation> conversations;
    public OnConversationClickListener listener;

    /**
     * Constructor for the ConversationListRecyclerViewAdapter.
     *
     * @param conversations The list of conversations to display.
     * @param listener      The listener for click events on conversation items.
     */
    public ConversationListRecyclerViewAdapter(List<Conversation> conversations, OnConversationClickListener listener) {
        this.conversations = conversations;
        this.listener = listener;
    }

    /**
     * Creates a new ViewHolder instance.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type of the new View.
     * @return A new ConversationListViewHolder instance.
     */
    @NonNull
    @Override
    public ConversationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
        return new ConversationListViewHolder(v);
    }

    /**
     * Binds the conversation data to the ViewHolder's views.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the conversation in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull ConversationListViewHolder holder, int position) {
        Conversation conversation = conversations.get(position);

        holder.message.setText(conversation.getMessage());
        holder.contact.setText(conversation.contact.getName());

        holder.itemView.setOnClickListener(view -> listener.onConversationClick(conversation));
    }

    /**
     * Returns the total number of conversations in the list.
     *
     * @return The total number of conversations.
     */
    @Override
    public int getItemCount() {
        if (conversations == null) {
            return 0;
        }
        return conversations.size();
    }
}