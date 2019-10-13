package com.example.chatchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Adapter.ChatRoomListAdapter;
import model.Chatroom;

public class UserProfileActivity extends AppCompatActivity {
    private ImageView imgAvator;
    private TextView txtName;
    private TextView txtEmail;
    private String uid ;
    private String imgDir = "https://firebasestorage.googleapis.com/v0/b/cs408-project.appspot.com/o/";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<String> favorite;
    private RecyclerView rv;
    private ChatRoomListAdapter adapter;
    private ArrayList<Chatroom> chatrooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        rv = (RecyclerView)findViewById(R.id.UserProfile_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(UserProfileActivity.this);
        rv.setLayoutManager(llm);
        chatrooms = new ArrayList<>();
        adapter = new ChatRoomListAdapter(chatrooms, UserProfileActivity.this);
        rv.setAdapter(adapter);


        imgAvator = (ImageView)findViewById(R.id.userProfile_image);
        txtName = (TextView)findViewById(R.id.userProfile_Name);
        txtEmail = (TextView)findViewById(R.id.userProfile_email);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    private void showFavorite(){
        uid = getIntent().getExtras().getString("UID");
        db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot myDoc = task.getResult();
                    if (myDoc.getString("imgurl") != null && myDoc.getString("imgurl").equals("")) {
                        Picasso.get().load(imgDir + "default-avatar.png?alt=media&token=af33a9b5-b4f4-4d44-8c1b-671caf2181c6").fit().into(imgAvator);
                    } else {
                        Picasso.get().load(imgDir + myDoc.getString("imgurl")).fit().into(imgAvator);
                    }

                    txtName.setText(myDoc.getString("chatName"));
                    txtEmail.setText(myDoc.getString("userEmail"));
                    favorite = (List<String>) myDoc.get("favoriteList");
                    if(favorite !=null){
                        chatrooms.clear();
                        for(int i = 0; i<favorite.size();i++){
                            db.collection("Chatroom").document(favorite.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        DocumentSnapshot chatroomDoc = task.getResult();
                                        String category = (String)chatroomDoc.getData().get(Chatroom.CATEGORY);
                                        String chatName = (String)chatroomDoc.getData().get(Chatroom.CHAT_NAME);
                                        String description = (String)chatroomDoc.getData().get(Chatroom.DESCRIPTION);
                                        String likes = (String)chatroomDoc.getData().get(Chatroom.LIKES);
                                        String creater_name = (String)chatroomDoc.getData().get(Chatroom.CREATER);
                                        String date = (String)chatroomDoc.getData().get(Chatroom.DATE);
                                        String chat_id = (String)chatroomDoc.getData().get(Chatroom.CHAT_ID);
                                        Chatroom tempChat = new Chatroom(chatName, category, creater_name, date);
                                        tempChat.setChatId(chat_id);
                                        tempChat.setLikes(likes);
                                        tempChat.setDate(description);
                                        chatrooms.add(tempChat);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });

                        }


                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onRestart()
    {  // After a pause OR at startup
        super.onRestart();
        //chatrooms.clear();
        showFavorite();

    }

    @Override
    public void onBackPressed() {
        //onSupportNavigateUp();
        finish();
        super.onBackPressed();
    }
}
