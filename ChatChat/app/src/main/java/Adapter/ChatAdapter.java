package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import model.Message;



public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    public static final int MESSAGE_LEFT = 0;
    public static final int MESSAGE_RIGHT = 1;

    private Context context;
    private ArrayList<Message> messageArrayList;
    private FirebaseUser firebaseUser;

    public ChatAdapter(ArrayList<Message> messageArrayList, Context context)
    {
        this.context = context;
        this.messageArrayList = messageArrayList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }


    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType == MESSAGE_LEFT)
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_dialog_left, parent, false);
        }
        else if(viewType == MESSAGE_RIGHT)
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_dialog_right, parent, false);
        }
        MessageViewHolder messageViewHolder = new MessageViewHolder(v);
        return messageViewHolder;
    }


    @NonNull

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        String message_sender = messageArrayList.get(position).getUserId();
        String currentUser_uid = firebaseUser.getUid();
        if(currentUser_uid.equals(message_sender))
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
        public ImageView profile_picture;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message_message);
            profile_picture = itemView.findViewById(R.id.message_photo);
        }
    }
}
