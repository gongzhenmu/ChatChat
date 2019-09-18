package com.example.chatchat;

import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainPageActivity extends AppCompatActivity {
    private Button btnChange;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        btnChange = (Button) findViewById(R.id.change_passWord);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChangePasswordActivity();
            }
        });
    }
    private void launchChangePasswordActivity() {
        Intent intent = new Intent(MainPageActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
        finish();
    }
}