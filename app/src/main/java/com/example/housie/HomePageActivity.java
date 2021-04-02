package com.example.housie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private Button profile_btn;
    private Button friends_btn;
    private Button joinroom_btn;
    private Button createroom_btn;
    private Button signout_btn;
    private static final String TAG = "Achal-Homepage";
    private UserProfile userProfileLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        profile_btn = findViewById(R.id.btn_profile);
        friends_btn = findViewById(R.id.btn_friends);
        joinroom_btn = findViewById(R.id.btn_join);
        createroom_btn = findViewById(R.id.btn_create);
        signout_btn = findViewById(R.id.btn_signout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("users/userProfile").child(currentUser.getUid());
        }
        //User eventListener
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfileLocal = snapshot.getValue(UserProfile.class);
                onLoginUpdate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: Database error: ", error.toException());
            }
        });


        //Profile Button Event Listener
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        //Sign out Button Event Listener
        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("isActive").setValue(false);
                mAuth.signOut();
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        friends_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, FriendsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onLoginUpdate() {
        if (userProfileLocal != null) {
            userProfileLocal.setIsActive(true);
            mDatabase.setValue(userProfileLocal);
        }
    }
}
