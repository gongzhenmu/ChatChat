package com.example.chatchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import model.Chatroom;
import model.User;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.Calendar;

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

        createChatDialog = new Dialog(this);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

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
}
