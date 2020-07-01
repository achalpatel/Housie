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
    private String oriName;
    private String oriEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameText = findViewById(R.id.id_name);
        emailText = findViewById(R.id.id_email);
        update_btn = findViewById(R.id.btn_update);
        back_btn = findViewById(R.id.btn_back);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getDisplayName() != null && currentUser.getEmail() != null) {
                nameText.setText(currentUser.getDisplayName());
                emailText.setText(currentUser.getEmail());
            }
            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    UserProfile user = snapshot.child("users").child(currentUser.getUid()).getValue(User.class);
//                    if (user != null) {
//                        Log.d(TAG, "onDataChange: Username:" + user.getName());
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "onCancelled: Could not find User", error.toException());
                }
            };
            mDatabase.addValueEventListener(userListener);

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
        } else {
            this.updateProfile();
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
                    Toast.makeText(ProfileActivity.this, "Name Updated", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: Name Updated to " + currentUser.getDisplayName());
//                    User user = new User(nameText.getText().toString(), emailText.getText().toString());
//                    mDatabase.child("users").child(currentUser.getUid()).setValue(user);
                    finish();
                }
            }
        });
    }
}
