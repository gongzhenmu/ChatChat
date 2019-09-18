package com.example.chatchat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import model.Chatroom;

public class MainPageActivity extends AppCompatActivity {
    Dialog createChatDialog;
    private FirebaseFirestore db;
    private EditText chatnameInput;
    private EditText descriptionInput;
    private Spinner categorySpinner;

    private String chatname = "";
    private String description = "";
    private String category = "";
    private Chatroom newChatRoom;
    private Button createButton;
    private String date = Calendar.getInstance().getTime().toString();

    private Button exploreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        createChatDialog = new Dialog(this);
        exploreButton = (Button)findViewById(R.id.main_button_explore);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
        db = FirebaseFirestore.getInstance();
    }

    public void showCreateChat(View v) {

        createChatDialog.setContentView(R.layout.popup_create_chat);
        chatnameInput = (EditText) createChatDialog.findViewById(R.id.createchat_name_text);
        descriptionInput = (EditText) createChatDialog.findViewById(R.id.createchat_des_text);
        categorySpinner = (Spinner) createChatDialog.findViewById(R.id.createchat_category_spinner);
        createButton = (Button) createChatDialog.findViewById(R.id.createchat_button_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatname = chatnameInput.getText().toString();
                description = chatnameInput.getText().toString();
                category = categorySpinner.getSelectedItem().toString();
                newChatRoom = new Chatroom(chatname, category, "Shikang", date);
                newChatRoom.setDescription(description);
                db.collection("Chatroom").add(newChatRoom.getChatroom()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        createChatDialog.dismiss();
                        Toast.makeText(MainPageActivity.this, "created", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainPageActivity.this, "fail to create an account", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        createChatDialog.show();

    }

}

