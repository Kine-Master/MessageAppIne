package com.example.messageappine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    private ApiAccess apiAccess;
    private EditText inputStudentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set window insets listener for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.button);
        inputStudentNum = findViewById(R.id.editTextNumber);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.3:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiAccess = retrofit.create(ApiAccess.class);

        // Check for existing user session
        SharedPreferences account = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String StudentID = account.getString("StudentID", "");

        if (!StudentID.isEmpty()) {
            CallMessages(StudentID);
        }

        // Set click listener for the login button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentNum = inputStudentNum.getText().toString();
                if (studentNum.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Required Student ID.", Toast.LENGTH_SHORT).show();
                } else {
                    // Make API call to get student name
                    Call<Contact> call = apiAccess.getStudentName(studentNum.trim());
                    call.enqueue(new Callback<Contact>() {
                        @Override
                        public void onResponse(Call<Contact> call, Response<Contact> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Contact contact = response.body();

                                // Save student ID in shared preferences
                                SharedPreferences.Editor editor = account.edit();
                                editor.putString("StudentID", contact.StudentID);
                                editor.apply();

                                // Start Messages activity
                                Intent intent = new Intent(MainActivity.this, Messages.class);
                                intent.putExtra("studentNum", studentNum);
                                intent.putExtra("Name", contact.Name);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Contact> call, Throwable t) {
                            startActivity(new Intent(MainActivity.this, Messages.class)
                                    .putExtra("Name", "Error"));
                        }
                    });
                }
            }
        });
    }

    /**
     * Starts the Messages activity if the user is already logged in.
     *
     * @param StudentID The student ID of the logged-in user.
     */
    private void CallMessages(String StudentID) {
        Call<Contact> call = apiAccess.getStudentName(StudentID);

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Contact contact = response.body();

                    startActivity(new Intent(MainActivity.this, Messages.class)
                            .putExtra("Name", contact.Name)
                            .putExtra("StudentID", contact.StudentID)
                    );
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                // Handle failure if necessary
            }
        });
    }
}