package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.ChatRoomListAdapter;
import model.Chatroom;

public class ProfileActivity extends AppCompatActivity {

    private Button btnChangeUid, btnChangePassword,btnOtherUser;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
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
        setContentView(R.layout.activity_profile);
        rv = (RecyclerView)findViewById(R.id.profile_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(ProfileActivity.this);
        rv.setLayoutManager(llm);
        chatrooms = new ArrayList<Chatroom>();
        adapter = new ChatRoomListAdapter(chatrooms, ProfileActivity.this);
        rv.setAdapter(adapter);

        imgProfilePic = (ImageView) findViewById(R.id.profile_image);
        txtName = (TextView) findViewById(R.id.profile_userName);
        txtEmail = (TextView) findViewById(R.id.profile_email);
       // btnOtherUser=(Button) findViewById(R.id.profile_otheruser);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot myDoc = task.getResult();
                    if (myDoc.getString("imgurl") != null && myDoc.getString("imgurl").equals("")) {
                        Picasso.get().load(imgDir + "default-avatar.png?alt=media&token=af33a9b5-b4f4-4d44-8c1b-671caf2181c6").fit().into(imgProfilePic);
                    } else {
                        Picasso.get().load(imgDir + myDoc.getString("imgurl")).fit().into(imgProfilePic);
                    }

                    txtName.setText(myDoc.getString("chatName"));
                    txtEmail.setText(myDoc.getString("userEmail"));
                    favorite = (List<String>) myDoc.get("favoriteList");
                    for(int i = 0; i<favorite.size();i++){
                        System.out.println(favorite.get(i));
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
                    adapter.notifyDataSetChanged();

                }
            }
        });



        imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUpload();

            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_logout:
                logout();
                return true;
            case R.id.settings_username:
                changeUid();
                return true;
            case R.id.settings_password:
                changePassword();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    private void logout(){
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void imageUpload(){
        Intent intent = new Intent(getApplicationContext(), ImageUpload.class);
        startActivity(intent);
    }
    private void changeUid(){
        Intent intent = new Intent(getApplicationContext(), ChangeUserNameActivity.class);
        startActivity(intent);
    }
    private void changePassword(){
        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        onSupportNavigateUp();

    }
}
