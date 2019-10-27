package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private Button btnChange;
    private FirebaseUser currentUser;
    private EditText passwordInput;
    private EditText newPasswordInput;
    private EditText newPassAgainInput;
    private String password;
    private String newPassword;
    private String newPassAgain;
    private FirebaseAuth firebaseAuth;
    private int newPassLenLimit = 20;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        newPassAgainInput = (EditText) findViewById(R.id.txtNewPassAgain);
        passwordInput = (EditText) findViewById(R.id.txtPassword);
        newPasswordInput = (EditText) findViewById(R.id.txtNewPass);
        btnChange = (Button) findViewById(R.id.btnChange_passWord);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordInput.getText().toString().trim();
                newPassword = newPasswordInput.getText().toString().trim();
                newPassAgain = newPassAgainInput.getText().toString().trim();
                if(newPassword.length() == 0){
                    Toast.makeText(ChangePasswordActivity.this, "Error. Empty password not allowed", Toast.LENGTH_LONG).show();
                }
                else if (!TextUtils.isEmpty(newPasswordInput.getText()) && !TextUtils.isEmpty(passwordInput.getText())
                        && !TextUtils.isEmpty(newPasswordInput.getText())){
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(currentUser.getEmail(), password);
                    currentUser.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        currentUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ChangePasswordActivity.this, "Password updated", Toast.LENGTH_LONG).show();
                                                    launchMainPage();
                                                } else {
                                                    Toast.makeText(ChangePasswordActivity.this, "Error. Password not updated", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });
    }

    private void launchMainPage() {
        Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}