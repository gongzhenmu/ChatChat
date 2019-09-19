package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainPageActivity extends AppCompatActivity {

    private Button btnChange;
    private Button exploreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        Button logOutButton = findViewById(R.id.logout_bt);
        exploreButton = (Button)findViewById(R.id.main_button_explore);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginActivity = new Intent(MainPageActivity.this, LoginActivity.class);
                startActivity(LoginActivity);
                finish();
            }
        });
    }


}


