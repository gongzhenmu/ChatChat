package com.example.chatchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {
    private ImageView imgAvator;
    private TextView txtName;
    private TextView txtEmail;
    private String uid ;
    private String imgDir = "https://firebasestorage.googleapis.com/v0/b/cs408-project.appspot.com/o/";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        imgAvator = (ImageView)findViewById(R.id.userProfile_image);
        txtName = (TextView)findViewById(R.id.userProfile_Name);
        txtEmail = (TextView)findViewById(R.id.userProfile_email);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        uid =getIntent().getExtras().getString("UID");
        db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot myDoc = task.getResult();
                    if (myDoc.getString("imgurl") != null && myDoc.getString("imgurl").equals("")) {
                        Picasso.get().load(imgDir + "default-avatar.png?alt=media&token=af33a9b5-b4f4-4d44-8c1b-671caf2181c6").fit().into(imgAvator);
                    } else {
                        Picasso.get().load(imgDir + myDoc.getString("imgurl")).fit().into(imgAvator);
                    }

                    Toast.makeText(UserProfileActivity.this, myDoc.getString("imgurl"), Toast.LENGTH_LONG).show();
                    txtName.setText(myDoc.getString("chatName"));
                    txtEmail.setText(myDoc.getString("userEmail"));

                }
            }
        });




    }
    @Override
    public void onBackPressed() {
        //onSupportNavigateUp();
        finish();
        super.onBackPressed();
    }
}
