package com.example.housie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class FriendsActivity extends AppCompatActivity {
    private Button btnMyFriends;
    private Button btnFriendRequests;
    private Button btnSearchPeople;
    private static final String TAG = "Achal-FriendsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        btnMyFriends = findViewById(R.id.btn_myFriends);
        btnFriendRequests = findViewById(R.id.btn_friendRequests);
        btnSearchPeople = findViewById(R.id.btn_searchPeople);

        btnMyFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, MyFriendsActivity.class);
                startActivity(intent);
            }
        });

        btnSearchPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, SearchPeopleActivity.class);
                startActivity(intent);
            }
        });

        btnFriendRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(FriendsActivity.this, );
//                startActivity(intent);
            }
        });
    }
}
