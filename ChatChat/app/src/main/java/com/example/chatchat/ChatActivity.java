package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Adapter.ChatAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import model.Message;
import model.User;

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
    private List<String> fList = new ArrayList<>();
    private ToggleButton toggle_button_like;
    private int numberOfLikes;
    private ChatAdapter adapter;
    private ImageButton informationbtn;
    private String inputRegex = "^[a-zA-Z0-9]*[.,_+=:'\";!?\\w\\s]*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = FirebaseFirestore.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser()!= null)
        {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            user_uid = firebaseUser.getUid();
        }


        rv = (RecyclerView) findViewById(R.id.activity_chat_message_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(ChatActivity.this);
        rv.setLayoutManager(llm);

        messageArrayList = new ArrayList<>();
        adapter = new ChatAdapter(messageArrayList, user_uid, ChatActivity.this);
        rv.setAdapter(adapter);

        informationbtn = findViewById(R.id.activity_chat_button_info);

        chatroom_id = getIntent().getStringExtra("Chatroom_ID");
        chatroom_name = getIntent().getStringExtra("Chatroom_NAME");
        titleText = (TextView) findViewById(R.id.activity_chat_title);
        titleText.setText(chatroom_name);
        button_send = (ImageButton) findViewById(R.id.activity_chat_button_send);
        editText = (EditText) findViewById(R.id.activity_chat_message_editText);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rv.scrollToPosition(adapter.getItemCount() - 1);
                return false;
            }
        });
        informationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, ChatRoomInfoActivity.class);
                intent.putExtra("Chatroom_ID", chatroom_id);
                startActivity(intent);
            }
        });
        //get  username for current user
        username = "null_username";
        final DocumentReference userRf = db.collection("Users").document(user_uid);
        userRf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userDoc = task.getResult();
                    username = (String) userDoc.getData().get(User.CHAT_NAME);
                    imgurl = (String) userDoc.getData().get(User.IMAGE);
                }
            }
        });
        //     retriveMessage();
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message_content = "";
                message_content = editText.getText().toString();
                //check if message_content is a valid input
                if(message_content.length() == 0)
                {
                    Toast.makeText(ChatActivity.this, "Empty message!", Toast.LENGTH_LONG).show();
                }else if(!message_content.matches(inputRegex))
                {
                    Toast.makeText(ChatActivity.this, "message contains invalid input!", Toast.LENGTH_LONG).show();
                }else if(message_content.length() > 150)
                {
                    Toast.makeText(ChatActivity.this, "Message too long!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Long ts = System.currentTimeMillis() / 1000;
                    String timestamp = ts.toString();
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(ts * 1000);
                    String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
                    Message message = new Message(message_content, username, imgurl, user_uid, date);
                    message.setTimestamp(timestamp);
                    sendMessage(message);
                    Toast.makeText(ChatActivity.this, "sent", Toast.LENGTH_LONG).show();
                    editText.setText("");
                }

            }
        });

        //get number of likes in the chatroom
        final DocumentReference chatroomRef =  db.collection("Chatroom").document(chatroom_id);
        chatroomRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        numberOfLikes = Integer.parseInt(document.getString("likes"));
                    } else {
                        Toast.makeText(ChatActivity.this, "Error chatroom document not exists", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChatActivity.this, "Error read chatroom document", Toast.LENGTH_LONG).show();
                }
            }
        });

        //add to favorite list
        toggle_button_like = (ToggleButton)findViewById(R.id.activity_chat_button_like);
        userRf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(document.contains("favoriteList")) {
                            fList = (List<String>) document.get("favoriteList");
                            if (fList.contains(chatroom_id)) {
                                toggle_button_like.setChecked(true);
                            }
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "Error document not exists", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChatActivity.this, "Error read document", Toast.LENGTH_LONG).show();
                }
            }
        });

        //when the button changes state

        toggle_button_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = toggle_button_like.isChecked();
                if (isChecked && !fList.contains(chatroom_id)) {
                    // The toggle is enabled
                    numberOfLikes++;
                    fList.add(chatroom_id);
                    userRf.update("favoriteList", fList);
                } else {
                    // The toggle is disabled
                    if(fList.contains(chatroom_id)){
                        fList.remove(chatroom_id);
                        numberOfLikes --;
                        userRf.update("favoriteList", fList);
                    }
                }
            }
        });

        receiveMessage();

    }

    public void sendMessage(Message message) {
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
                Toast.makeText(ChatActivity.this, "fail to send message", Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void retriveMessage() {
//        CollectionReference messageRef = db.collection("Chatroom").document(chatroom_id).
//                collection("Messages");
//        messageRef.orderBy("timestamp", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        String content = (String) doc.getData().get(Message.CONTENT);
//                        String date = (String) doc.getData().get(Message.DATE);
//                        String timestamp = (String) doc.getData().get(Message.TIMESTAMP);
//                        String url = (String) doc.getData().get(Message.URL);
//                        String user_id = (String) doc.getData().get(Message.USER_ID);
//                        String username = (String) doc.getData().get(Message.USERNAME);
//                        Message tmpMessage = new Message(content, username, url, user_id, date);
//                        tmpMessage.setTimestamp(timestamp);
//                        messageArrayList.add(tmpMessage);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });
//
//    }

    public void receiveMessage() {
        CollectionReference messageRef = db.collection("Chatroom").document(chatroom_id).
                collection("Messages");
        messageRef.orderBy("timestamp", Query.Direction.ASCENDING);
        messageRef.addSnapshotListener(ChatActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            String content = (String) dc.getDocument().getData().get(Message.CONTENT);
                            String date = (String) dc.getDocument().getData().get(Message.DATE);
                            String timestamp = (String) dc.getDocument().getData().get(Message.TIMESTAMP);
                            String url = (String) dc.getDocument().getData().get(Message.URL);
                            String user_id = (String) dc.getDocument().getData().get(Message.USER_ID);
                            String username = (String) dc.getDocument().getData().get(Message.USERNAME);
                            Message tmpMessage = new Message(content, username, url, user_id, date);
                            tmpMessage.setTimestamp(timestamp);
                            messageArrayList.add(tmpMessage);
                            adapter.notifyDataSetChanged();
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

    public void setChatroom_id_for_testing()
    {
        chatroom_id = "7olaE7ARKCpNafKZsq85";
    }


}
