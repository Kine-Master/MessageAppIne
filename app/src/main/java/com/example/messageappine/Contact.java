package com.example.messageappine;

/**
 * Represents a contact in the application.
 */
public class Contact {
    public String StudentID;
    public String Name;
    public String Contacts;
    public String StudentId;

    /**
     * Gets the student ID.
     *
     * @return The student ID.
     */
    public String getStudentID() {
        return StudentID;
    }

    /**
     * Gets the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getName() {
        return Name.trim();
    }

    /**
     * Sets the name of the contact.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Sets the student ID.
     *
     * @param StudentId The student ID to set.
     */
    public void setStudentId(String StudentId){
        this.Name = Name.trim();
    }

    /**
     * Sets the contact information.
     *
     * @param contacts The contact information to set.
     */
    public void setContacts(String contacts){
        this.Contacts = contacts;
    }
}