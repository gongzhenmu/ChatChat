package com.example.chatchat;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText mEmail;
    private Button mSendButton;
    private Button mResendButton;
    FirebaseAuth mAuth;
    private boolean exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mEmail = findViewById(R.id.reset_password_email_editText);
        mSendButton = findViewById(R.id.reset_password_send_button);
        mResendButton = findViewById(R.id.reset_password_resend_button);
        exist = true;
        mAuth = FirebaseAuth.getInstance();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs(mEmail, mAuth)) {
                    // Send the user a password reset email
                        mAuth.sendPasswordResetEmail(mEmail.getText().toString());
                        mResendButton.setVisibility(View.VISIBLE);
                }
            }
        });
        mResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mAuth.sendPasswordResetEmail(mEmail.getText().toString());
            }
        });
    }

    /**
     *
     * @param mEmail EditText field for the username
     * @param auth Firebase auth
     * @return a boolean true if inputs are valid of false if not
     */
    private boolean validateInputs(EditText mEmail, FirebaseAuth auth) {
            return validateEmail(mEmail, auth);
    }

    /**
     *
     * @param mEmail EditText field for email confirmation
     * @param auth Firebase auth
     * @return a boolean true if email exists, false if it doesn't
     */
    private boolean validateEmail(final EditText mEmail, FirebaseAuth auth) {
        //Try to create a dummy account using user input email and check if adding is possible without exceptions
        auth.fetchSignInMethodsForEmail(mEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().getSignInMethods().isEmpty()) {
                                exist = false;
                            } else {
                                exist = true;
                            }
                        }
                    }
                });
        return exist;
    }
}
