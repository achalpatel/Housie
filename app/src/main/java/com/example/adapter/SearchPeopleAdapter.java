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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class SearchPeopleAdapter extends RecyclerView.Adapter<SearchPeopleAdapter.ViewHolder> {
    private static final String TAG = "Achal-SearchPeopleAda";
    private List<String> userResult;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private String userId;
    private UserFriends currentUserFriends;
    private UserFriends positionUserFriends;

    public SearchPeopleAdapter(List<String> userResult){
        this.userResult = userResult;
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public Button btnRequest;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nameView = itemView.findViewById(R.id.user_text);
            btnRequest = itemView.findViewById(R.id.btn_sendRequest);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View searchView = inflater.inflate(R.layout.fragment_sendrequest, parent, false);
        return new ViewHolder(searchView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TextView nameTextView = holder.nameView;
        final Button requestButton = holder.btnRequest;
        userId = userResult.get(position);
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTextView.setText(snapshot.getValue(UserFriends.class).userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(currentUserFriends.checkFriendReqSent(userId)){
            requestButton.setEnabled(false);
            requestButton.setText("Sent");
        }
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestButton.setEnabled(false);
                mDatabase.child(currentUser.getUid()).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        currentUserFriends = currentData.getValue(UserFriends.class);
                        if(currentUserFriends==null){
                            return Transaction.success(currentData);
                        }
                        currentUserFriends.addToFriendReqSent(userId);
                        currentData.setValue(currentUserFriends);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        Log.d(TAG, "onComplete: FriendreqSent");
                        mDatabase.child(userId).runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                positionUserFriends = currentData.getValue(UserFriends.class);
                                if(positionUserFriends==null){
                                    return Transaction.success(currentData);
                                }
                                positionUserFriends.addToFriendReqReceived(currentUser.getUid());
                                currentData.setValue(positionUserFriends);
                                return Transaction.success(currentData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                Log.d(TAG, "onComplete: FriendReqRec");
                                requestButton.setText("Sent");
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userResult.size();
    }

}
