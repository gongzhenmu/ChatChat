package com.example.chatchat;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ChatRoomInfoActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private String chatroom_id;
    private TextView chatName;
    private TextView description;
    private TextView count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        chatroom_id =  getIntent().getStringExtra("Chatroom_ID");
        chatName = findViewById(R.id.chatName);
        description = findViewById(R.id.description);
        count = findViewById(R.id.count);
        db = FirebaseFirestore.getInstance();
        db.collection("Chatroom").document(chatroom_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot chatDoc = task.getResult();
                chatName.setText(chatDoc.get("chatName").toString());
                description.setText(chatDoc.get("description").toString());
                count.setText(chatDoc.get("likes").toString());
            }
        });

    }
}
