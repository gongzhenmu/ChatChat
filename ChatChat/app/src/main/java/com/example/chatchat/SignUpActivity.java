package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import model.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private EditText chatNameInput;
    private Button confirm;

    private String chatName;
    private String email;
    private String password;
    private String userId;
    private User user;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        chatNameInput = findViewById(R.id.chatName);
        confirm = findViewById(R.id.confirm);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {

        email = emailInput.getText().toString().trim();
        password = passwordInput.getText().toString().trim();
        chatName = chatNameInput.getText().toString().trim();
        if (!TextUtils.isEmpty(emailInput.getText())
                && !TextUtils.isEmpty(passwordInput.getText())
                && !TextUtils.isEmpty(chatNameInput.getText())) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(SignUpActivity.this, "successfully add user to authentication", Toast.LENGTH_LONG).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        addUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    private void addUser() {
        currentUser = firebaseAuth.getCurrentUser();
        userId = currentUser.getUid();
        user = new User(chatName, email, userId);
        db.collection("Users").document(user.getUser().get(user.USER_ID))
                .set(user.getUser())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this, "successfully create an account", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, MainPageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "fail to create an account", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
