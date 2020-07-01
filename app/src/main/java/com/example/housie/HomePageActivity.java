package com.example.housie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

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
    private boolean status;
    private UserProfile userProfileLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = true;
        setContentView(R.layout.activity_home_page);
        profile_btn = findViewById(R.id.btn_profile);
        friends_btn = findViewById(R.id.btn_friends);
        joinroom_btn = findViewById(R.id.btn_join);
        createroom_btn = findViewById(R.id.btn_create);
        signout_btn = findViewById(R.id.btn_signout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //User eventListener
//        mDatabase.child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                userProfileLocal = snapshot.getValue(UserProfile.class);
////                Log.d(TAG, "onDataChange: userLocal: "+userProfileLocal.getName());
//                onLoginUpdate();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e(TAG, "onCancelled: Database error: ", error.toException());
//            }
//        });


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
                mAuth.signOut();
//                userProfileLocal.setIsActive(false);
//                Map<String, Object> updatechild = new HashMap<>();
//                updatechild.put("isActive",false);
//                mDatabase.child("users").child(currentUser.getUid()).updateChildren(updatechild);
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void onLoginUpdate(){
        Map<String,Object> setStatus = new HashMap<>();
        setStatus.put("isActive",true);
        mDatabase.child("users").child(userProfileLocal.getUserId()).updateChildren(setStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    userProfileLocal.setIsActive(true);
                    Log.d(TAG, "onComplete: Active:"+userProfileLocal.getIsActive());
                }else{
                    Log.d(TAG, "onComplete: status change failed");
                }
            }
        });
    }


}
