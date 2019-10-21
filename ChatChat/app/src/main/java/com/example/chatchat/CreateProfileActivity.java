package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import model.User;

public class CreateProfileActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private EditText chatNameInput;
    private Button confirm;

    private String chatName;
    private String email;
    private String password;
    private String userId;
    private User user;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getExtras().getString("email");
        userId = getIntent().getExtras().getString("uid");
        setContentView(R.layout.activity_signup);
        findViewById(R.id.password).setVisibility(View.GONE);
        findViewById(R.id.email).setVisibility(View.GONE);
        chatNameInput = (EditText) findViewById(R.id.chatName);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        chatName = chatNameInput.getText().toString().trim();
        User user = new User(chatName, email, userId);
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userId).set(user.getUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(CreateProfileActivity.this, "successfully created user profile", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateProfileActivity.this, ExploreActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(CreateProfileActivity.this, "Failed to create user profile", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
