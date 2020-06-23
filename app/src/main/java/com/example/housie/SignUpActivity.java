package com.example.housie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private static final String TAG = "SignUpActivity";
    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameText = findViewById(R.id.id_name);
        emailText = findViewById(R.id.id_email);
        passwordText = findViewById(R.id.id_password);
        Button submit_btn = findViewById(R.id.btn_submit);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Toast.makeText(this, "Already Signed in", Toast.LENGTH_SHORT).show();
            String email = currentUser.getEmail();
            emailText.setText(email);
            if(currentUser.getDisplayName()!=null){
                nameText.setText(currentUser.getDisplayName());
            }
        }
        Log.d(TAG, "onStart: " + currentUser);
        




        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitClick();
            }
        });

    }

    protected void submitClick(){
        if(currentUser!=null && nameText.getText().toString()!=null){
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nameText.getText().toString())
                    .build();
            currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Name Updated", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete: Name Updated to "+currentUser.getDisplayName());
                        finish();
                    }
                }
            });
        }
        if(currentUser==null){
            mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: User Signed Up successfully");
                                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "onComplete: Sign up failed", task.getException());
                                Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


}
