package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainPageActivity extends AppCompatActivity {


    private Button exploreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        exploreButton = (Button)findViewById(R.id.main_button_explore);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });
    }



}

