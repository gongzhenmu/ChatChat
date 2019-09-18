package com.example.chatchat;

import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainPageActivity extends AppCompatActivity {
    private Button btnChange;
    private TextView txtUsername;
    private FirebaseUser currentUser;
    private String userId;
    private String username="hi";

    private FirebaseFirestore db;
    private DocumentSnapshot ds;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        btnChange = (Button) findViewById(R.id.change_userName);
        txtUsername = (TextView) findViewById(R.id.userName);
        db = FirebaseFirestore.getInstance();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = currentUser.getUid();

        DocumentReference userNameRef = db.collection("Users").document(userId);
        userNameRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                ds = task.getResult();
                username = ds.getString("chatName");
                txtUsername.setText(username);
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChangeUsernameActivity();
            }
        });

    }
    private void launchChangeUsernameActivity() {
        Intent intent = new Intent(MainPageActivity.this, ChangeUserNameActivity.class);
        startActivity(intent);
        finish();
    }
}