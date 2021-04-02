package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housie.R;
import com.example.model.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private DatabaseReference mDatabase;
    private List<String> mUsers;
    private static final String TAG = "Achal-FriendsAdapater";

    public FriendsAdapter(List<String> users) {
        this.mUsers = users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView statusView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewId);
            statusView = itemView.findViewById(R.id.statusId);
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
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child("userProfile");
        String userId = mUsers.get(position);
        final ViewHolder holderInner = holder;
        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile user = snapshot.getValue(UserProfile.class);
                updateText(holderInner, user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: Cancelled");
            }
        });
//        String status = user.getIsActive().toString();
//        nameTextView.setText(userName);
//        statusTextView.setText(status);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void updateText(ViewHolder holder, UserProfile userProfile) {
        TextView nameTextView = holder.textView;
        TextView statusTextView = holder.statusView;
        nameTextView.setText(userProfile.name);
        statusTextView.setText(userProfile.isActive.toString());
    }

}
