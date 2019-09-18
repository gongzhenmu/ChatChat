package com.example.chatchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import static android.content.ContentValues.TAG;

public class ChangePasswordActivity extends AppCompatActivity {
    private Button btnChange;
    private FirebaseUser currentUser;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText newPasswordInput;
    private String email;
    private String password;
    private String newPassword;
    private FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        emailInput = (EditText) findViewById(R.id.email);
        passwordInput = (EditText) findViewById(R.id.password);
        newPasswordInput = (EditText) findViewById(R.id.newPass);
        btnChange = (Button) findViewById(R.id.change_passWord);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString().trim();
                password = passwordInput.getText().toString().trim();
                newPassword = newPasswordInput.getText().toString().trim();
                if (!TextUtils.isEmpty(emailInput.getText()) && !TextUtils.isEmpty(passwordInput.getText())
                        && !TextUtils.isEmpty(newPasswordInput.getText())){
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(email, password);
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
                                                    Toast.makeText(ChangePasswordActivity.this, "Error password not updated", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "Error auth failed");
                                    }
                                }
                            });
                }
            }
        });
    }

    private void launchMainPage() {
        Intent intent = new Intent(ChangePasswordActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }
}
