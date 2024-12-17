package com.example.messageappine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Defines the API endpoints for the application.
 */
public interface ApiAccess {

    /**
     * Retrieves the student's name.
     *
     * @param Value The student's ID.
     * @return A Call object that can be used to execute the request.
     */
    @GET("/getStudentName")
    Call<Contact> getStudentName(@Query("value") String Value);

    /**
     * Retrieves a list of contacts for the given student ID.
     *
     * @param value The student's ID.
     * @return A Call object that can be used to execute the request.
     */
    @GET("/getContacts")
    Call<List<Contact>> getContacts(@Query("studentId") String value);

    /**
     * Sends a message.
     *
     * @param from The sender's ID.
     * @param to   The recipient's ID.
     * @param message The message content.
     * @return A Call object that can be used to execute the request.
     */
    @GET("/sendMessage")
    Call<Message> sendMessage(
            @Query("from") String from,
            @Query("to") String to,
            @Query("message") String message
    );

    /**
     * Retrieves a list of messages between two users.
     *
     * @param from The sender's ID.
     * @param to   The recipient's ID.
     * @return A Call object that can be used to execute the request.
     */
    @GET("/getMessages")
    Call<List<Message>> getMessages(
            @Query("from") String from,
            @Query("to") String to
    );
}