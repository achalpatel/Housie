package com.example.housie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.UserFriends;
import com.example.model.UserGames;
import com.example.model.UserProfile;
import com.example.model.UserRooms;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private static final String TAG = "Achal-Signup";
    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;
    private Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameText = findViewById(R.id.id_name);
        emailText = findViewById(R.id.id_email);
        passwordText = findViewById(R.id.id_password);
        submit_btn = findViewById(R.id.btn_submit);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        currentUser = mAuth.getCurrentUser();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    submitClick();
                }
            }
        });

    }

    protected void submitClick() {
        if (nameText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Name", Toast.LENGTH_SHORT).show();
        } else if (emailText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Email", Toast.LENGTH_SHORT).show();
        } else if (passwordText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Password", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: User Signed Up successfully");
                                Toast.makeText(SignUpActivity.this, "Sign-up Success", Toast.LENGTH_SHORT).show();
                                currentUser = mAuth.getCurrentUser();
                                updateProfile();
                                finish();
                            } else {
                                Log.e(TAG, "onComplete: Sign up failed", task.getException());
                                Toast.makeText(SignUpActivity.this, "Sign-up Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void updateProfile() {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameText.getText().toString())
                .build();
        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: Name: " + currentUser.getDisplayName());
                    String uId = currentUser.getUid();
                    String userName = nameText.getText().toString();
                    UserProfile userProfile = new UserProfile(uId, userName, emailText.getText().toString());
                    UserFriends userFriends = new UserFriends(uId, userName);
                    UserRooms userRooms = new UserRooms(uId, userName);
                    UserGames userGames = new UserGames(uId);
                    mDatabase.child("userProfile").child(uId).setValue(userProfile);
                    mDatabase.child("userFriends").child(uId).setValue(userFriends);
                    mDatabase.child("userRooms").child(uId).setValue(userRooms);
                    mDatabase.child("userGames").child(uId).setValue(userGames);
                    mAuth.signOut();
                }
                if (task.isCanceled()) {
                    Log.e(TAG, "onComplete: " + task.getException());
                }
            }
        });
    }

}
