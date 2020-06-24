package com.example.housie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

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


        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
