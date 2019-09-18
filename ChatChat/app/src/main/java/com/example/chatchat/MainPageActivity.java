package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainPageActivity extends AppCompatActivity {
    private Button imgUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        imgUpload = (Button) findViewById(R.id.MainPage_changeImg);

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImg();

            }
        });



    }



    private void changeImg(){
        Intent intent = new Intent(getApplicationContext(),ImageUpload.class);
        startActivity(intent);



    }
}
