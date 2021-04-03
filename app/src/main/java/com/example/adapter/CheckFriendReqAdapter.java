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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class CheckFriendReqAdapter extends RecyclerView.Adapter<CheckFriendReqAdapter.ViewHolder> {
    private static final String TAG = "Achal-CheckFriendReqAda";
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private List<String> userReqResult;
    private UserFriends currentUserFriends;

    public CheckFriendReqAdapter(List<String> userReqResult) {
        this.userReqResult = userReqResult;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child("userFriends");
        mDatabase.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUserFriends = snapshot.getValue(UserFriends.class);
                Log.d(TAG, "onDataChange: currentUserFriends class initialized");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: Failed to initialize currentUserFriends");
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public Button btnAccept;
        public Button btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.user_text);
            btnAccept = itemView.findViewById(R.id.btn_acceptReq);
            btnReject = itemView.findViewById(R.id.btn_rejectReq);
        }
    }

    @NonNull
    @Override
    public CheckFriendReqAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View searchView = inflater.inflate(R.layout.fragment_checkreq, parent, false);
        return new CheckFriendReqAdapter.ViewHolder(searchView);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckFriendReqAdapter.ViewHolder holder, int position) {
        final TextView nameTextView = holder.nameView;
        final Button acceptButton = holder.btnAccept;
        final Button rejectButton = holder.btnReject;
        final String userId = userReqResult.get(position);
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFriends positionUserFriend = snapshot.getValue(UserFriends.class);
                nameTextView.setText(positionUserFriend.userName);
                Log.d(TAG, "onDataChange: PositionFriend loaded");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: PositionFriend failed to load" + error);
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptButton.setEnabled(false);
                rejectButton.setEnabled(false);

                currentUserFriends.removeFriendReqReceived(userId);
                currentUserFriends.addToFriendList(userId);

                mDatabase.child(currentUser.getUid()).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if (currentData.getValue(UserFriends.class) == null) {
                            return Transaction.success(currentData);
                        }
                        currentData.setValue(currentUserFriends);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        mDatabase.child(userId).runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                UserFriends positionUserFriend = currentData.getValue(UserFriends.class);
                                if (positionUserFriend == null) {
                                    return Transaction.success(currentData);
                                }
                                positionUserFriend.removeFriendReqSent(currentUser.getUid());
                                positionUserFriend.addToFriendList(currentUser.getUid());
                                currentData.setValue(positionUserFriend);
                                return Transaction.success(currentData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                Log.d(TAG, "onComplete: Request Acceptied");
                                acceptButton.setText("Accepted");
                                rejectButton.setText("Accepted");
                            }
                        });
                    }
                });
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptButton.setEnabled(false);
                rejectButton.setEnabled(false);

                currentUserFriends.removeFriendReqReceived(userId);

                mDatabase.child(currentUser.getUid()).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if (currentData.getValue(UserFriends.class) == null) {
                            return Transaction.success(currentData);
                        }
                        currentData.setValue(currentUserFriends);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        mDatabase.child(userId).runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                UserFriends positionUserFriend = currentData.getValue(UserFriends.class);
                                if (positionUserFriend == null) {
                                    return Transaction.success(currentData);
                                }
                                positionUserFriend.removeFriendReqSent(currentUser.getUid());
                                currentData.setValue(positionUserFriend);
                                return Transaction.success(currentData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                Log.d(TAG, "onComplete: Request Rejected");
                                rejectButton.setText("Rejected");
                                acceptButton.setText("Rejected");
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userReqResult.size();
    }
}
