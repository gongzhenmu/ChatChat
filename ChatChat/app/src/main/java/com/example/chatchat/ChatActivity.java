package com.example.chatchat;

import Adapter.ChatAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import model.Chatroom;
import model.Message;
import model.User;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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

    private ArrayList<Message> messageArrayList;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_uid = firebaseUser.getUid();

        rv = (RecyclerView)findViewById(R.id.activity_chat_message_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(ChatActivity.this);
        rv.setLayoutManager(llm);

        messageArrayList = new ArrayList<>();
        adapter = new ChatAdapter(messageArrayList, user_uid, ChatActivity.this);
        rv.setAdapter(adapter);


        chatroom_id =  getIntent().getStringExtra("Chatroom_ID");
        chatroom_name = getIntent().getStringExtra("Chatroom_NAME");
        titleText = (TextView)findViewById(R.id.activity_chat_title);
        titleText.setText(chatroom_name);
        button_send = (ImageButton)findViewById(R.id.activity_chat_button_send);
        editText = (EditText)findViewById(R.id.activity_chat_message_editText);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rv.scrollToPosition(adapter.getItemCount()-1);
                return false;
            }
    });

        //get  username for current user
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
   //     retriveMessage();
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

        receiveMessage();

    }

    public void sendMessage(Message message)
    {
        CollectionReference messageRef = db.collection("Chatroom").document(chatroom_id).
                collection("Messages");
        messageRef.document(message.getTimestamp()).set(message.getMessage()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(ChatActivity.this, "sent", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatActivity.this, "fail to send message" , Toast.LENGTH_LONG).show();
            }
        });

    }

    public void retriveMessage()
    {
        CollectionReference messageRef = db.collection("Chatroom").document(chatroom_id).
                collection("Messages");
        messageRef.orderBy("timestamp", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        String content = (String)doc.getData().get(Message.CONTENT);
                        String date = (String)doc.getData().get(Message.DATE);
                        String timestamp = (String)doc.getData().get(Message.TIMESTAMP);
                        String url = (String)doc.getData().get(Message.URL);
                        String user_id = (String)doc.getData().get(Message.USER_ID);
                        String username = (String)doc.getData().get(Message.USERNAME);
                        Message tmpMessage = new Message(content, username, url, user_id, date);
                        tmpMessage.setTimestamp(timestamp);
                        messageArrayList.add(tmpMessage);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    public void receiveMessage()
    {
        CollectionReference messageRef = db.collection("Chatroom").document(chatroom_id).
                collection("Messages");
        messageRef.orderBy("timestamp", Query.Direction.ASCENDING);
        messageRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges())
                {
                    switch (dc.getType())
                    {
                        case ADDED:
                            String content = (String)dc.getDocument().getData().get(Message.CONTENT);
                            String date = (String)dc.getDocument().getData().get(Message.DATE);
                            String timestamp = (String)dc.getDocument().getData().get(Message.TIMESTAMP);
                            String url = (String)dc.getDocument().getData().get(Message.URL);
                            String user_id = (String)dc.getDocument().getData().get(Message.USER_ID);
                            String username = (String)dc.getDocument().getData().get(Message.USERNAME);
                            Message tmpMessage = new Message(content, username, url, user_id, date);
                            tmpMessage.setTimestamp(timestamp);
                            messageArrayList.add(tmpMessage);
                            adapter.notifyDataSetChanged();
                            rv.scrollToPosition(adapter.getItemCount()-1);
                            break;
                        case MODIFIED:
                            break;
                        case REMOVED:
                            break;
                    }

                }
            }
        });
    }



}
