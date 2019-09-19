package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button login;

    private String email;
    private String password;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = (EditText) findViewById(R.id.email);
        passwordInput = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_bt);
        firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString().trim();
                password = passwordInput.getText().toString().trim();
                if(TextUtils.isEmpty(emailInput.getText())){
                    Toast.makeText(LoginActivity.this, "Please enter a valid email", Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(passwordInput.getText())){
                    Toast.makeText(LoginActivity.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
                }
                if (!TextUtils.isEmpty(emailInput.getText()) && !TextUtils.isEmpty(passwordInput.getText())){
                    loginUser(email, password);
                }            }
        });
    }

    private void launchMainPage() {
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    launchMainPage();
                }else {
                    Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

