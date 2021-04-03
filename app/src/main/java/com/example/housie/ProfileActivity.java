package com.example.housie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private static final String TAG = "Achal-Profile";
    private EditText nameText;
    private EditText emailText;
    private Button update_btn;
    private Button back_btn;
    private UserProfile userProfile;
    private DatabaseReference userFriendsDatabase;
    private DatabaseReference userRoomsDatabase;
    private DatabaseReference userProfileDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameText = findViewById(R.id.id_name);
        emailText = findViewById(R.id.id_email);
        update_btn = findViewById(R.id.btn_update);
        back_btn = findViewById(R.id.btn_back);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (currentUser != null) {
            userProfileDatabase = mDatabase.child("users").child("userProfile").child(currentUser.getUid());
            userProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userProfile = snapshot.getValue(UserProfile.class);
                    Log.d(TAG, "onDataChange: name:" + userProfile.getName());
                    Log.d(TAG, "onDataChange: email:" + userProfile.getEmail());
                    nameText.setText(userProfile.getName());
                    emailText.setText(userProfile.getEmail());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "onCancelled: Could not find User", error.toException());
                }
            });
        }
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClick();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void updateClick() {
        if (nameText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Name", Toast.LENGTH_SHORT).show();
        } else if (emailText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Email", Toast.LENGTH_SHORT).show();
        } else {
            this.updateProfile();
        }
    }

    public void updateProfile() {
        userFriendsDatabase = mDatabase.child("users").child("userFriends").child(currentUser.getUid());
        userRoomsDatabase = mDatabase.child("users").child("userRooms").child(currentUser.getUid());
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameText.getText().toString())
                .build();
        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Name Updated", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: Name updated to" + nameText.getText().toString());
                    userProfile.setName(nameText.getText().toString());
                    userProfile.setEmail(emailText.getText().toString());
                    userFriendsDatabase.child("userName").setValue(nameText.getText().toString());
                    userRoomsDatabase.child("userName").setValue(nameText.getText().toString());
                    userProfileDatabase.setValue(userProfile);
                    finish();
                }
            }
        });
    }
}
