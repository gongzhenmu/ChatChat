package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.login_bt);
        Button SignUpButton = findViewById(R.id.signUp_bt);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginActivity);
            }
        });
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUpActivity = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(SignUpActivity);
            }
        });

    }





}
