package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatchat.ChatActivity;
import com.example.chatchat.R;
import com.example.chatchat.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import model.Chatroom;
import model.Message;



public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    public static final int MESSAGE_LEFT = 0;
    public static final int MESSAGE_RIGHT = 1;

    private Context context;
    private ArrayList<Message> messageArrayList;
    private String user_uid;
    private String imgDir = "https://firebasestorage.googleapis.com/v0/b/cs408-project.appspot.com/o/";

    public ChatAdapter(ArrayList<Message> messageArrayList, String uid, Context context)
    {
        this.context = context;
        this.messageArrayList = messageArrayList;
        user_uid = uid;

    }


    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MESSAGE_LEFT)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_dialog_left, parent, false);
            return new MessageViewHolder(v);
        }
        else if(viewType == MESSAGE_RIGHT)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_dialog_right, parent, false);
            return new MessageViewHolder(v);
        }

        return null;
    }


    @NonNull

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageArrayList.get(position);
        holder.message.setText(message.getContent());
        holder.date.setText(message.getDate());
        holder.username.setText(message.getUsername());
        holder.profile_picture.setOnClickListener(new ImgClickListener(message.getUserId(),context));

        //TODO
        /*
        * Add clickListener for ImageView and display profile picture
        * for holder.profile_picture
        * */

        if(message.getUrl().length()==0){
            Picasso.get().load(imgDir + "default-avatar.png?alt=media&token=af33a9b5-b4f4-4d44-8c1b-671caf2181c6").fit().into(holder.profile_picture);
        }else{
            Picasso.get().load(imgDir + message.getUrl()).fit().into(holder.profile_picture);
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }



    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        String message_sender = messageArrayList.get(position).getUserId();

        if(user_uid.equals(message_sender))
        {
            return MESSAGE_RIGHT;
        }
        else
        {
            return MESSAGE_LEFT;
        }

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public TextView date;
        public TextView username;
        public ImageView profile_picture;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message_message);
            date = itemView.findViewById(R.id.message_date);
            profile_picture = itemView.findViewById(R.id.message_photo);
            username = itemView.findViewById(R.id.message_username);
        }
    }

    private class ImgClickListener implements View.OnClickListener {

        String uid;
        Context context;

        public ImgClickListener(String uid, Context context)
        {
            this.uid = uid;
            this.context = context;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UserProfileActivity.class);
            intent.putExtra("UID", uid);
            context.startActivity(intent);
        }
    }
}
