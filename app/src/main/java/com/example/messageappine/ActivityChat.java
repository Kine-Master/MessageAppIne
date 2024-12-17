package com.example.messageappine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityChat extends AppCompatActivity {

    private RecyclerView recyclerViewInchtact;
    private TextView txtContactName;
    private EditText txtMessage;
    private Button btnSendMessage;
    private Button btnBackToContacts;
    private Arrays Collections;

    List<Message> messages;
    DisConRecyclerView adapter;

    private String receivername;
    private String sender;
    private String receiver;
    private String name;
    private ApiAccess apiAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtContactName = findViewById(R.id.txtContactName);
        recyclerViewInchtact = findViewById(R.id.recyclerViewInchtact);
        txtMessage = findViewById(R.id.txtMessage);
        btnSendMessage = findViewById(R.id.btnSendMessage);

        btnBackToContacts = findViewById(R.id.btnBackToContacts);
        btnBackToContacts.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityChat.this, ContactActivity.class);
            startActivity(intent);
            // Finish the current activity to prevent back navigation
            finish();
        });

        messages = new ArrayList<>();
        adapter = new DisConRecyclerView(messages, ActivityChat.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewInchtact.setLayoutManager(layoutManager);
        recyclerViewInchtact.setAdapter(adapter);

        receivername = getIntent().getStringExtra("ReceiverName").trim();
        sender = getIntent().getStringExtra("senderID").trim();
        receiver = getIntent().getStringExtra("receiverID").trim();

        txtContactName.setText(receivername);

        setupRetrofit();

        loadMessagesFromServer();

        sendbtn();

    }

    /**
     * Sets up the Retrofit instance for making API calls.
     */
    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.3:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiAccess = retrofit.create(ApiAccess.class);
    }

    /**
     * Loads messages from the server and displays them in the RecyclerView.
     */
    private void loadMessagesFromServer() {
        Call<List<Message>> call = apiAccess.getMessages(sender, receiver);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Message> MessageFromSender = response.body();

                    Call<List<Message>> call1 = apiAccess.getMessages(receiver, sender);
                    call1.enqueue(new Callback<List<Message>>() {
                        @Override
                        public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                            if (response.isSuccessful() && response.body() != null){
                                List<Message> MessageFromReceiver = response.body();

                                messages.clear();
                                messages.addAll(MessageFromSender);
                                messages.addAll(MessageFromReceiver);

                                messages.sort((m1, m2) -> Integer.compare(m1.getMessageId(), m2.getMessageId()));

                                for (Message message : messages){
                                    message.setSent(sender.equals(message.getFrom()));
                                }

                                Log.d("Response", "response" + sender + receiver + messages);

                                adapter.notifyDataSetChanged();
                                recyclerViewInchtact.scrollToPosition(messages.size() - 1);
                            } else {
                                showError("Error fetching receiver messages: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Message>> call, Throwable t) {
                            Toast.makeText(ActivityChat.this, "basta error kay tanga ka", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(ActivityChat.this, "error nanaman", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sets up the send button click listener to send messages.
     */
    private void sendbtn() {
        btnSendMessage.setOnClickListener(view -> {
            String messageCONTENT = txtMessage.getText().toString().trim();

            if (TextUtils.isEmpty(messageCONTENT)){
                Toast.makeText(this, "Message empty", Toast.LENGTH_SHORT).show();
                return;
            }

            btnSendMessage.setEnabled(false);
            Message newMessage = new Message(0, messageCONTENT, true, true, sender, receiver);
            messages.add(newMessage);
            adapter.notifyItemInserted(messages.size() - 1);
            recyclerViewInchtact.scrollToPosition(messages.size() - 1);

            txtMessage.setText("");
            sendMessageToServer(messageCONTENT);
        });
    }

    /**
     * Sends a message to the server.
     *
     * @param messageCONTENT The content of the message to send.
     */
    private void sendMessageToServer(String messageCONTENT) {
        Call<Message> call = apiAccess.sendMessage(sender, receiver, messageCONTENT);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                btnSendMessage.setEnabled(true);
                if (response.isSuccessful() && response.body() != null){
                    Toast.makeText(ActivityChat.this, "Message Successful", Toast.LENGTH_SHORT).show();
                }else {
                    showError("failed to sent");
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                btnSendMessage.setEnabled(true);
                showError("No internet" + t.getMessage());
            }
        });
    }

    /**
     * Displays an error message to the user.
     *
     * @param error The error message to display.
     */
    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}