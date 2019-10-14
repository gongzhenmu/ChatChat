package com.example.chatchat;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Adapter.ChatRoomListAdapter;
import model.Chatroom;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearch;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ChatRoomListAdapter adapter;
    private ArrayList<Chatroom> chatrooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        mSearch = findViewById(R.id.search_room);
        recyclerView = findViewById(R.id.activity_search_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(llm);

        db = FirebaseFirestore.getInstance();
        chatrooms = new ArrayList<>();
        adapter = new ChatRoomListAdapter(chatrooms, SearchActivity.this);
        recyclerView.setAdapter(adapter);

        Query chatroomQuery = db.collection("Chatroom").whereEqualTo("chatName", mSearch.getText().toString());
        Log.d("ChatroomList", "getting query from db");
        chatroomQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    Log.d("ChatroomList", "getting rooms from db");

                    for(QueryDocumentSnapshot chatroomDoc : task.getResult())
                    {
                        Log.d("ChatroomList", "getting single room attributes from db");
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
                else
                {
                    Log.d("ChatroomList", "No room found");
                }
                adapter.notifyDataSetChanged();
            }
        });

        adapter.notifyDataSetChanged();
        Log.d("ChatroomList", "number of rooms getting from db is " + chatrooms.size());
    }
}

