package com.example.messageappine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity for displaying and managing messages.
 */
public class Messages extends AppCompatActivity implements OnConversationClickListener {

    Button contactButton;
    Button logoutButton;
    Button refreshButton;
    RecyclerView listReceiveMessages;
    TextView textViewUserName;
    String studentId;
    String studentID;
    String name;
    EditText editTextNumber;
    List<Contact> contactList;
    List<Conversation> conversations = new ArrayList<>();
    ApiAccess apiAccess;
    ImageView imageView;
    ConversationListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_messages);

        // Set window insets listener for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        studentID = getIntent().getStringExtra("studentNum");
        Log.d("Student Number", "ID: " + studentID);

        name = getIntent().getStringExtra("Name");

        textViewUserName = findViewById(R.id.textViewUserName);
        textViewUserName.setText(getIntent().getStringExtra("Name"));

        listReceiveMessages = findViewById(R.id.recyclerViewMessages);
        listReceiveMessages.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ConversationListRecyclerViewAdapter(conversations, Messages.this);
        adapter.conversations = new ArrayList<>();
        listReceiveMessages.setAdapter(adapter);

        contactButton = findViewById(R.id.btnContacts);
        contactButton.setOnClickListener(view -> {
            String studentNum = getIntent().getStringExtra("studentNum");
            Intent intent = new Intent(Messages.this, ContactActivity.class);
            intent.putExtra("studentNum", studentNum);
            startActivity(intent);
        });

        logoutButton = findViewById(R.id.btnLogout);
        SharedPreferences account = getSharedPreferences("UserInfo", MODE_PRIVATE);
        studentId = account.getString("studentNum", "");

        logoutButton.setOnClickListener(view -> {
            // Clear session data
            SharedPreferences.Editor editor = account.edit();
            editor.remove("StudentID");
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(Messages.this, MainActivity.class);
            startActivity(intent);

            // Finish the current activity to prevent back navigation
            finish();
        });

        refreshButton = findViewById(R.id.btnRefresh);
        refreshButton.setOnClickListener(view -> {
            loadMessages();
            Toast.makeText(getApplicationContext(), "Refresh successful!", Toast.LENGTH_SHORT).show();
        });



        setupRetrofit();
        loadMessages();
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
    private void loadMessages() {
        Call<List<Contact>> call = apiAccess.getContacts(studentID);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactList = response.body();

                    for (Contact contact : contactList) {
                        fetchMessages(contact);
                    }
                } else {
                    Toast.makeText(Messages.this, "No contacts available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Toast.makeText(Messages.this, "No contacts available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Fetches messages for a specific contact.
     *
     * @param contact The contact to fetch messages for.
     */
    private void fetchMessages(Contact contact) {
        Call<List<Message>> call = apiAccess.getMessages(studentID, contact.getStudentID().trim());
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Message> MessageFromSender = response.body();

                    Call<List<Message>> call1 = apiAccess.getMessages(contact.getStudentID().trim(), studentID);
                    call1.enqueue(new Callback<List<Message>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<Message> MessageFromReceiver = response.body();

                                List<Message> combinedMessages = new ArrayList<>();
                                combinedMessages.addAll(MessageFromSender);
                                combinedMessages.addAll(MessageFromReceiver);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    combinedMessages.sort((m, m1) -> Integer.compare(m.getMessageId(), m1.getMessageId()));
                                }

                                if (!combinedMessages.isEmpty()) {
                                    Message latestMessage = combinedMessages.get(0);
                                    addConversation(contact, latestMessage);
                                }
                            } else {
                                Toast.makeText(Messages.this, "Error fetching receiver messages: ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Message>> call, Throwable t) {
                            Toast.makeText(Messages.this, "Network Error: ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(Messages.this, "error nanaman", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Adds a conversation to the list and updates the RecyclerView.
     *
     * @param contact The contact associated with the conversation.
     * @param message The latest message in the conversation.
     */
    private void addConversation(Contact contact, Message message) {
        Conversation conversation = new Conversation();
        conversation.contact = contact;
        conversation.messages = new ArrayList<>();
        conversation.messages.add(message);

        conversations.add(conversation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            conversations.sort((m1, m2) -> {
                Message lastMessage = m1.messages.get(m1.messages.size() - 1);
                Message lastMessage1 = m2.messages.get(m2.messages.size() - 1);

                return Integer.compare(lastMessage.getMessageId(), lastMessage1.getMessageId());
            });
        }

        adapter.conversations = conversations;
        adapter.notifyDataSetChanged();
    }

    /**
     * Handles the click event when a conversation is selected.
     *
     * @param conversation The selected conversation.
     */
    @Override
    public void onConversationClick(Conversation conversation) {
        Intent intent = new Intent(this, ActivityChat.class);

        intent.putExtra("ReceiverName", conversation.contact.getName());
        intent.putExtra("receiverID", conversation.contact.getStudentID());
        intent.putExtra("senderID", studentId);
        startActivity(intent);
    }
}