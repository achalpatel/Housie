package com.example.housie;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.SearchPeopleAdapter;
import com.example.model.UserFriends;
import com.example.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchPeopleActivity extends AppCompatActivity {
    private static final String TAG = "Achal-SearchPeople";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private List<String> userList;
    private UserFriends currentUserFriends;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpeople);
        recyclerView = findViewById(R.id.recycler_searchpeople);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        userList = new ArrayList<>();
        final SearchPeopleAdapter searchPeopleAdapter = new SearchPeopleAdapter(userList);
        recyclerView.setAdapter(searchPeopleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        mDatabase.child("userFriends").child(currentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentUserFriends = snapshot.getValue(UserFriends.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: Cannot fetch currentUserFriends"+error);
                    }
                });


        Query getPeopleQuery = mDatabase.child("userProfile").limitToFirst(10);
        getPeopleQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, UserProfile>> t = new GenericTypeIndicator<Map<String, UserProfile>>() {};
                Map<String, UserProfile> mapOfProfile = snapshot.getValue(t);
                mapOfProfile.remove(currentUser.getUid());
                Set<String> tempProfileSet = new HashSet<>(mapOfProfile.keySet());
                Set<String> friends = new HashSet<>(currentUserFriends.getFriendsList());
                Set<String> requestReceived = new HashSet<>(currentUserFriends.getFriendRequestReceived());
                Set<String> requestSent = new HashSet<>(currentUserFriends.getFriendRequestSent());
                tempProfileSet.removeAll(friends);
                tempProfileSet.removeAll(requestReceived);
                tempProfileSet.removeAll(requestSent);
                userList.addAll(tempProfileSet);
                searchPeopleAdapter.notifyDataSetChanged();
                Log.d(TAG, "onDataChange: People Updated");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: No People found");
            }
        });
    }
}
