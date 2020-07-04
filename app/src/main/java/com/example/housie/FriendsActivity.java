package com.example.housie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.example.adapter.FriendsAdapter;
import com.example.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FriendsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private TextView textView;
    private TextView statusTextView;
    private static final String TAG = "Achal-FriendsActivity";
    private HashMap<String, HashMap> users;
    private RecyclerView recyclerView;
    private List<UserProfile> userIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewId);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users/userProfile");
        userIdList = new ArrayList<>();


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object userObj = snapshot.getValue();
                users = (HashMap<String, HashMap>) userObj;
                if(users!=null){
                    for(String userId : users.keySet()){
                        UserProfile userProfile = new UserProfile(userId);
                        if(users.containsKey(userId) && users.get(userId)!=null){
                            userProfile.mapToUserProfile(users.get(userId));
                            userIdList.add(userProfile);
                        }
                        Log.d(TAG, "onDataChange: userProfile "+userProfile.getUserId());
                    }
                }
                FriendsAdapter friendsAdapter = new FriendsAdapter(userIdList);
                recyclerView.setAdapter(friendsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}