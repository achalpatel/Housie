package com.example.housie;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.SearchPeopleAdapter;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchPeopleActivity extends AppCompatActivity {
    private static final String TAG = "Achal-SearchPeople";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private List<UserProfile> userProfiles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpeople);
        recyclerView = findViewById(R.id.recycler_searchpeople);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        userProfiles = new ArrayList<>();
        final SearchPeopleAdapter searchPeopleAdapter = new SearchPeopleAdapter(userProfiles);
        recyclerView.setAdapter(searchPeopleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        Query getPeopleQuery = mDatabase.child("userProfile").limitToFirst(10);
        getPeopleQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, UserProfile>> t = new GenericTypeIndicator<Map<String, UserProfile>>() {};
                Map<String, UserProfile> mapOfProfile = snapshot.getValue(t);
                mapOfProfile.remove(currentUser.getUid());
                List<UserProfile> tempProfileList = new ArrayList<>(mapOfProfile.values());
                userProfiles.addAll(tempProfileList);
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
