package com.example.chatchat;

import androidx.appcompat.app.AppCompatActivity;
import model.Chatroom;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    Chatroom chatroom;
    TextView titleText;
    ImageButton button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatroom = (Chatroom) getIntent().getSerializableExtra("Chatroom");
        titleText = (TextView)findViewById(R.id.activity_chat_title);
        titleText.setText(chatroom.getName());
        button_send = (ImageButton)findViewById(R.id.activity_chat_button_send);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "sending successful", Toast.LENGTH_LONG).show();
                titleText.setText("");
            }
        });

    }
}
