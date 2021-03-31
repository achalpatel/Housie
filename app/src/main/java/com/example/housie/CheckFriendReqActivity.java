package com.example.housie;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.CheckFriendReqAdapter;
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
import java.util.List;
import java.util.Map;

public class CheckFriendReqActivity extends AppCompatActivity {
    private static final String TAG = "Achal-CheckFriendReq";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private List<String> userReqResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendreq);
        recyclerView = findViewById(R.id.recycler_friendreq);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        userReqResult = new ArrayList<>();
        final CheckFriendReqAdapter checkFriendReqAdapter = new CheckFriendReqAdapter(userReqResult);
        recyclerView.setAdapter(checkFriendReqAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        Query getPeopleQuery = mDatabase.child("userFriends").child(currentUser.getUid())
                .child("friendRequestReceived");
        getPeopleQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                List<String> userRequests = snapshot.getValue(t);
                if(userRequests!=null){
                    userReqResult.addAll(userRequests);
                    checkFriendReqAdapter.notifyDataSetChanged();
                    Log.d(TAG, "onDataChange: UserReqResults Added");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: UserReqResults Error"+error);
            }
        });
    }
}
