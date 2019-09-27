package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private Button btnChangeUid, btnLogout, btnChangePassword;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    private String imgDir = "https://firebasestorage.googleapis.com/v0/b/cs408-project.appspot.com/o/";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnChangeUid = (Button) findViewById(R.id.profile_changeUID);

        btnChangePassword = (Button) findViewById(R.id.profile_changePassword);
        btnLogout = (Button) findViewById(R.id.profile_logout);
        imgProfilePic = (ImageView) findViewById(R.id.profile_image);
        txtName = (TextView) findViewById(R.id.profile_userName);
        txtEmail = (TextView) findViewById(R.id.profile_email);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot myDoc = task.getResult();
                    if (myDoc.getString("imgurl") != null && myDoc.getString("imgurl").equals("")) {
                        Picasso.get().load(imgDir + "default-avatar.png?alt=media&token=af33a9b5-b4f4-4d44-8c1b-671caf2181c6").fit().into(imgProfilePic);
                    } else {
                        Picasso.get().load(imgDir + myDoc.getString("imgurl")).fit().into(imgProfilePic);
                    }

                    Toast.makeText(ProfileActivity.this, myDoc.getString("imgurl"), Toast.LENGTH_LONG).show();
                    txtName.setText(myDoc.getString("chatName"));
                    txtEmail.setText(myDoc.getString("userEmail"));

                }
            }
        });
        imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageUpload.class);
                startActivity(intent);

            }
        });
        btnChangeUid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeUserNameActivity.class);
                startActivity(intent);

            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);


            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();

    }
}
