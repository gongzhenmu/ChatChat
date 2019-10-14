package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;


public class MainPageActivity extends AppCompatActivity {


    private Button exploreButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

         exploreButton = findViewById(R.id.main_button_explore);



       
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });
    }



    // add option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_profile:
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.toolbar_serach:
                Intent intent1 = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}


