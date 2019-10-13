package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatchat.ChatActivity;
import com.example.chatchat.ChatroomListActivity;
import com.example.chatchat.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import model.Chatroom;

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ChatroomHolder>{


    private ArrayList<Chatroom> chatrooms;
    private Context context;
    public ChatRoomListAdapter(ArrayList<Chatroom> chat, Context context)
    {
        chatrooms = chat;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatroomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Adapter", "onCreated: called");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_chatroom, parent, false);
        ChatroomHolder chatroomHolder = new ChatroomHolder(v);
        return chatroomHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ChatroomHolder holder, int position) {
        Chatroom chatroom = chatrooms.get(position);
        String chatname = chatroom.getName();
        holder.roomname.setText(chatname);
        holder.roomname.setClickable(true);
        holder.roomname.setOnClickListener(new RoomClickListener(chatroom, context));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return chatrooms.size();
    }

    public static class ChatroomHolder extends RecyclerView.ViewHolder
    {
        TextView roomname;
        CardView cv;
        View mView;
        public ChatroomHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            cv = (CardView) mView.findViewById(R.id.cardview_chatroom);
            roomname = (TextView)mView.findViewById(R.id.cardview_chatname);
        }
    }

    private class RoomClickListener implements View.OnClickListener {

        Chatroom chat;
        Context context;

        public RoomClickListener(Chatroom chat, Context context)
        {
            this.chat = chat;
            this.context = context;
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "joining the room",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("Chatroom_ID", chat.getChatId());
            intent.putExtra("Chatroom_NAME", chat.getName());
            context.startActivity(intent);
        }
    }
}
