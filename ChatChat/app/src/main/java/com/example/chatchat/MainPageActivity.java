package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainPageActivity extends AppCompatActivity {

    private Button imgUpload;
    private Button exploreButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        imgUpload = (Button) findViewById(R.id.MainPage_changeImg);
         exploreButton = (Button)findViewById(R.id.main_button_explore);

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImg();

            }
        });
       
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });



    }


       


    
   private void changeImg(){
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);




    }


}


