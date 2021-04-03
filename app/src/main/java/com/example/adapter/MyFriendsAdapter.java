package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housie.R;
import com.example.model.UserFriends;
import com.example.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder> {
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private List<String> mUsers;
    private static final String TAG = "Achal-FriendsAdapater";

    public MyFriendsAdapter(List<String> users) {
        this.mUsers = users;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView statusView;
        public Button btnRemoveFriend;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewId);
            statusView = itemView.findViewById(R.id.statusId);
            btnRemoveFriend = itemView.findViewById(R.id.removeBtn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View friendsView = inflater.inflate(R.layout.friends_layout, parent, false);
        return new ViewHolder(friendsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String userId = mUsers.get(position);
        final TextView nameTextView = holder.textView;
        final TextView statusTextView = holder.statusView;
        final Button removeFriendButton = holder.btnRemoveFriend;
        mDatabase.child("userProfile").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                nameTextView.setText(userProfile.name);
                String status  = userProfile.isActive ? "Online" : "Offline";
                statusTextView.setText(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: Cancelled");
            }
        });

        removeFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFriendButton.setEnabled(false);
                mDatabase.child("userFriends/"+currentUser.getUid()).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        UserFriends currentUserFriends = currentData.getValue(UserFriends.class);
                        if(currentUserFriends==null){
                            return Transaction.success(currentData);
                        }
                        currentUserFriends.removeFriend(userId);
                        currentData.setValue(currentUserFriends);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        mDatabase.child("userFriends/"+userId).runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                UserFriends positionUserFriends = currentData.getValue(UserFriends.class);
                                if(positionUserFriends==null){
                                    return Transaction.success(currentData);
                                }
                                positionUserFriends.removeFriend(currentUser.getUid());
                                currentData.setValue(positionUserFriends);
                                return Transaction.success(currentData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                Log.d(TAG, "onComplete: Friend Removed"+userId+":"+committed);
                                removeFriendButton.setText("Removed");
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
