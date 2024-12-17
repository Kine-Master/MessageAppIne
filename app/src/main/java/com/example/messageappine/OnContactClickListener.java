package com.example.messageappine;

/**
 * Interface definition for a callback to be invoked when a contact is clicked.
 */
public interface OnContactClickListener {
    /**
     * Called when a contact item is clicked.
     *
     * @param contact The contact that was clicked.
     */
    void onItemClick(Contact contact);
}