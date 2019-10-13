package com.example.chatchat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import Adapter.ChatRoomListAdapter;
import model.Chatroom;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearch;
    private FirebaseAuth mAuth;
    private CollectionReference mRef;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ChatRoomListAdapter adapter;
    private ArrayList<Chatroom> chatrooms;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        mSearch = findViewById(R.id.search_room);
        recyclerView = findViewById(R.id.activity_search_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(llm);

        db = FirebaseFirestore.getInstance();
        mRef= db.collection("Chatroom").document(mAuth.getUid()).collection("Name");
        category = getIntent().getStringExtra("category");
        chatrooms = new ArrayList<>();
        adapter = new ChatRoomListAdapter(chatrooms, SearchActivity.this);
        recyclerView.setAdapter(adapter);

        Query query = mRef.orderBy("name", Query.Direction.ASCENDING).startAt(mSearch.getText().toString()).endAt(mSearch.getText().toString() + "uf8ff");
        firebaseSearch(query);
    }
    private void firebaseSearch(Query sortQuery){

        FirestoreRecyclerOptions<SearchItem> options = new FirestoreRecyclerOptions.Builder<SearchItem>().setQuery(sortQuery,SearchItem.class).build();
        adapter = new SearchAdapter(options);
        recyclerView = findViewById(R.id.Search_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        adapter.setLongClickListener(new .OnItemLongClickListener() {
            @Override
            public void onItemClick(final DocumentSnapshot documentSnapshot, final int position) {

                new AlertDialog.Builder( SearchActivity.this )
                        .setTitle( "txt" )
                        .setMessage( "Downloading Expense?" )
                        .setPositiveButton( "Download", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                File dir = new File(Environment.getDataDirectory(),documentSnapshot.get("name").toString()+"txt");

                                try{
                                    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(Environment.getDataDirectory().toString()+documentSnapshot.get("name").toString()+"txt",true));
                                    fileWriter.append("asdsada");

                                    fileWriter.close();
                                    Toast.makeText(SearchExpense.this,Environment.getDataDirectory().toString(),Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(SearchExpense.this,"Failed",Toast.LENGTH_SHORT).show();
                                }



                            }
                        })
                        .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        } )
                        .show();
            }
        });

    }

}

