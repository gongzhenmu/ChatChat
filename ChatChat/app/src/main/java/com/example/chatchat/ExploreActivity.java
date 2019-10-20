package com.example.chatchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.ChatRoomListAdapter;
import model.Chatroom;
import model.User;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {

    private Dialog createChatDialog;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private EditText chatnameInput;
    private EditText descriptionInput;
    private Spinner categorySpinner;

    private String username;
    private String chatname = "";
    private String description = "";
    private String category = "";
    private Chatroom newChatRoom;
    private Button createButton;
    private String date = Calendar.getInstance().getTime().toString();
    private String inputRegex = "^[a-zA-Z0-9][\\w\\s]*[.,!?]*";
    //favorite list
    private RecyclerView rv;
    private ChatRoomListAdapter adapter;
    private ArrayList<Chatroom> chatrooms;
    private List<String> favorite;
    private TextView faList;



    private Button sportsButton;
    private Button newsButton;
    private Button gamesButton;
    private Button moviesButton;
    private Button musicButton;
    private Button businessButton;
    private Button techButton;
    private Button travelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        rv = (RecyclerView)findViewById(R.id.explore_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(ExploreActivity.this,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(llm);
        chatrooms = new ArrayList<>();
        adapter = new ChatRoomListAdapter(chatrooms, ExploreActivity.this);
        rv.setAdapter(adapter);

        createChatDialog = new Dialog(this);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        faList = (TextView)findViewById(R.id.explore_favoriteList);
        sportsButton = (Button)findViewById(R.id.explore_sports);
        newsButton = (Button)findViewById(R.id.explore_news);
        gamesButton = (Button)findViewById(R.id.explore_games);
        moviesButton = (Button)findViewById(R.id.explore_movies);
        musicButton = (Button)findViewById(R.id.explore_music);
        businessButton = (Button)findViewById(R.id.explore_business);
        techButton = (Button)findViewById(R.id.explore_tech);
        travelButton = (Button)findViewById(R.id.explore_travel);

        sportsButton.setOnClickListener(new CategoryButtonListener("Sports", ExploreActivity.this));
        newsButton.setOnClickListener(new CategoryButtonListener("News", ExploreActivity.this));
        gamesButton.setOnClickListener(new CategoryButtonListener("Games", ExploreActivity.this));
        moviesButton.setOnClickListener(new CategoryButtonListener("Movies", ExploreActivity.this));
        musicButton.setOnClickListener(new CategoryButtonListener("Music", ExploreActivity.this));
        businessButton.setOnClickListener(new CategoryButtonListener("Business", ExploreActivity.this));
        techButton.setOnClickListener(new CategoryButtonListener("Tech", ExploreActivity.this));
        travelButton.setOnClickListener(new CategoryButtonListener("Travel", ExploreActivity.this));
        favoriteList();

    }

    public void showCreateChat(View v) {
        String userId = currentUser.getUid();


        DocumentReference userRef = db.collection("Users").document(userId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot userDoc = task.getResult();
                    if(userDoc.exists())
                    {
                        username = (String)userDoc.getData().get(User.CHAT_NAME);
                    }
                }
            }
        });

        createChatDialog.setContentView(R.layout.popup_create_chat);
        chatnameInput = (EditText) createChatDialog.findViewById(R.id.createchat_name_text);
        descriptionInput = (EditText) createChatDialog.findViewById(R.id.createchat_des_text);
        categorySpinner = (Spinner) createChatDialog.findViewById(R.id.createchat_category_spinner);
        createButton = (Button) createChatDialog.findViewById(R.id.createchat_button_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatname = chatnameInput.getText().toString();
                description = descriptionInput.getText().toString();
                category = categorySpinner.getSelectedItem().toString();
                if(chatname.length() == 0 || description.length() == 0)
                {
                    Toast.makeText(ExploreActivity.this, "Empty Chat name or description.", Toast.LENGTH_LONG).show();
                    //createChatDialog.dismiss();
                }
                else if(!chatname.matches(inputRegex))
                {
                    Toast.makeText(ExploreActivity.this, "Chat name contains invalid characters!", Toast.LENGTH_LONG).show();
                }
                else if(chatname.length() > 40)
                {
                    Toast.makeText(ExploreActivity.this, "Chat name too long, needs to be less than 40", Toast.LENGTH_LONG).show();
                }
                else if(category.equals("Choose a topic for your chatroom"))
                {
                    Toast.makeText(ExploreActivity.this, "Please select a category!", Toast.LENGTH_LONG).show();
                }
                else if(!description.matches(inputRegex))
                {
                    Toast.makeText(ExploreActivity.this, "Description contains invalid characters!", Toast.LENGTH_LONG).show();
                }
                else if(description.length() > 150)
                {
                    Toast.makeText(ExploreActivity.this, "Description too long, needs to less than 150", Toast.LENGTH_LONG).show();
                }
                else
                {
                    newChatRoom = new Chatroom(chatname, category, username, date);
                    newChatRoom.setDescription(description);
                    DocumentReference chatRef = db.collection("Chatroom").document();
                    String id = chatRef.getId();
                    newChatRoom.setChatId(id);
                    chatRef.set(newChatRoom.getChatroom()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            createChatDialog.dismiss();
                            Toast.makeText(ExploreActivity.this, "created", Toast.LENGTH_LONG).show();
                        }
                    }).

                            addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ExploreActivity.this, "fail to create an account", Toast.LENGTH_LONG).show();

                                }
                            });
                }

            }
        });
        createChatDialog.show();
    }

    private class CategoryButtonListener implements View.OnClickListener {
        private String category;
        private Context context;
        public CategoryButtonListener(String cate, Context context)
        {
            category = cate;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChatroomListActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        }
    }
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
            case R.id.toolbar_feedback:
                Intent intent2 = new Intent(getApplicationContext(),FeedbackActivity.class);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void favoriteList(){
        db.collection("Users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot myDoc = task.getResult();

                    favorite = (List<String>) myDoc.get("favoriteList");
                    if (favorite != null) {
                        chatrooms.clear();
                        for (int i = 0; i < favorite.size(); i++) {
                            db.collection("Chatroom").document(favorite.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot chatroomDoc = task.getResult();
                                        String category = (String) chatroomDoc.getData().get(Chatroom.CATEGORY);
                                        String chatName = (String) chatroomDoc.getData().get(Chatroom.CHAT_NAME);
                                        String description = (String) chatroomDoc.getData().get(Chatroom.DESCRIPTION);
                                        String likes = (String) chatroomDoc.getData().get(Chatroom.LIKES);
                                        String creater_name = (String) chatroomDoc.getData().get(Chatroom.CREATER);
                                        String date = (String) chatroomDoc.getData().get(Chatroom.DATE);
                                        String chat_id = (String) chatroomDoc.getData().get(Chatroom.CHAT_ID);
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

                    }else{
                        faList.setText("Welcome!");
                    }

                }
            }
        });
    }

    @Override
    public void onRestart()
    {  // After a pause OR at startup
        super.onRestart();

        favoriteList();

    }


}