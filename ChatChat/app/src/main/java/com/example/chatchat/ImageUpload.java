package com.example.chatchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class ImageUpload extends AppCompatActivity {
    private Button btnChoose,btnUpload;
    private ImageView imgProfile;
    private Uri filePath;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference myRef;

    private ProgressBar myBar;

    private final int CHOOSE_IMAGE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        mAuth =FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        myRef = storage.getReference();
        btnChoose = (Button)findViewById(R.id.ImageUpload_Choose);
        btnUpload = (Button)findViewById(R.id.ImageUpload_Upload);
        imgProfile = (ImageView) findViewById(R.id.ImageUpload_Image);
        myBar = findViewById(R.id.ImageUpload_Progressbar);
        myBar.setVisibility(View.INVISIBLE);



        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


    }






    private void chooseImage(){
        //choose images from gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,CHOOSE_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK){
            if(data.getData()!=null){
                filePath=data.getData();
                try{
                    Picasso.get().load(filePath).fit().centerCrop().into(imgProfile);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        }
    }

    private void uploadImage(){
        if(filePath!=null){
            myBar.setVisibility(View.VISIBLE);
            final StorageReference ref = myRef.child(mAuth.getUid()+"/profilePic");
            final UploadTask uploadTask = ref.putFile(filePath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myBar.setVisibility(View.VISIBLE);
                    Task<Uri> downloadUriTask = ref.getDownloadUrl();
                    while (!downloadUriTask.isSuccessful());
                    Uri downloadUri = downloadUriTask.getResult();
                    System.out.println(downloadUriTask.toString());
                    if (downloadUri.toString() !=null) {
                        String imgurl = downloadUri.toString().substring(downloadUri.toString().lastIndexOf('/') + 1);
                        Map<String, Object> map = new HashMap<>();
                        map.put("imgurl", imgurl);
                        db.collection("Users").document(mAuth.getUid()).collection("images").document(imgurl).set(map);

                    }
                    myBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ImageUpload.this, "Uploaded" , Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ImageUpload.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                }
            });


            }

        else{
            Toast.makeText(ImageUpload.this, "Please select an image", Toast.LENGTH_SHORT).show();

        }



    }



    @Override
    public void onBackPressed(){
        onSupportNavigateUp();

    }
}
