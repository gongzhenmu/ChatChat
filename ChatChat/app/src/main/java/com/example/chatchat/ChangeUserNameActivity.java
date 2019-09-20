package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.app.AppCompatActivity;


public class ChangeUserNameActivity extends AppCompatActivity {
    private EditText newUserNameInput;
    private Button confirm;
    private String newUserName;
    private String userId;
    private int usernameLimit = 14;

    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_username);
        newUserNameInput = findViewById(R.id.newUserName);
        confirm = findViewById(R.id.confirm_newUserName);
        db = FirebaseFirestore.getInstance();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserName();
            }
        });
    }
    private void changeUserName(){
        if (!TextUtils.isEmpty(newUserNameInput.getText())){
            newUserName =  newUserNameInput.getText().toString().trim();
            if(newUserName.length() > usernameLimit){
                Toast.makeText(ChangeUserNameActivity.this, "Error. chat ID too long", Toast.LENGTH_LONG).show();
            }
            else if(newUserName.length() == 0){
                Toast.makeText(ChangeUserNameActivity.this, "Error. Display name cannot be empty", Toast.LENGTH_LONG).show();
            }
            else if(!newUserName.matches("[A-Za-z0-9_]+")){
                Toast.makeText(ChangeUserNameActivity.this, "Error. Invalid chat ID", Toast.LENGTH_LONG).show();
            }
            else {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                userId = currentUser.getUid();
                DocumentReference userNameRef = db.collection("Users").document(userId);
                userNameRef
                        .update("chatName", newUserName)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                launchMainPage();
                            }
                        });
            }
        }
    }
    private void launchMainPage() {
        Intent intent = new Intent(ChangeUserNameActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

}