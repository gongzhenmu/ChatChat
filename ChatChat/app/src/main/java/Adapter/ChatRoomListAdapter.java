package Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatchat.CategoryActivity;
import com.example.chatchat.R;

import java.lang.reflect.Array;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_chatroom, parent, false);
        ChatroomHolder chatroomHolder = new ChatroomHolder(v);
        return chatroomHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatroomHolder holder, int position) {
        String chatname = chatrooms.get(position).getName();
        holder.roomname.setText(chatname);
        holder.roomname.setClickable(true);
        holder.roomname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "joining the room",Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return 0;
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
}
