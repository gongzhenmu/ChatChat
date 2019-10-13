package com.example.chatchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import model.Chatroom;
import model.Message;
import model.User;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private String chatroom_id;
    private String chatroom_name;
    private TextView titleText;
    private EditText editText;
    private ImageButton button_send;
    private RecyclerView rv;
    private FirebaseUser firebaseUser;
    private String username;
    private String user_uid;
    private String imgurl;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = FirebaseFirestore.getInstance();
        chatroom_id =  getIntent().getStringExtra("Chatroom_ID");
        chatroom_name = getIntent().getStringExtra("Chatroom_NAME");
        titleText = (TextView)findViewById(R.id.activity_chat_title);
        titleText.setText(chatroom_name);
        button_send = (ImageButton)findViewById(R.id.activity_chat_button_send);
        editText = (EditText)findViewById(R.id.activity_chat_message_editText);
        //get uid and username for current user
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_uid = firebaseUser.getUid();
        username = "null_username";
        DocumentReference userRf = db.collection("Users").document(user_uid);
        userRf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot userDoc = task.getResult();
                    username = (String)userDoc.getData().get(User.CHAT_NAME);
                    imgurl = (String)userDoc.getData().get(User.IMAGE);
                }
            }
        });


        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message_content = "";
                message_content = editText.getText().toString();
                Long ts = System.currentTimeMillis()/1000;
                String timestamp = ts.toString();
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(ts * 1000);
                String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
                Message message = new Message(message_content, username, imgurl, user_uid, date);
                message.setTimestamp(timestamp);
                sendMessage(message);
                Toast.makeText(ChatActivity.this, "sending successful", Toast.LENGTH_LONG).show();
                editText.setText(" ");
            }
        });

    }

    public void sendMessage(Message message)
    {
        CollectionReference messageRef = db.collection("Chatroom").document(chatroom_id).
                collection("Messages");
        messageRef.document().set(message.getMessage()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ChatActivity.this, "sent", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatActivity.this, "fail to send message" , Toast.LENGTH_LONG).show();
            }
        });

    }

}
