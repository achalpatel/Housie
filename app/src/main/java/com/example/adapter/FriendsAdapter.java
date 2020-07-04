package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housie.R;
import com.example.model.UserProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private List<UserProfile> mUsers;

    public FriendsAdapter(List<UserProfile> users) {
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
        ViewHolder viewHolder = new ViewHolder(friendsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserProfile user = mUsers.get(position);
        TextView nameTextView = holder.textView;
        TextView statusTextView = holder.statusView;
        String userName = user.getName();
        String status = user.getIsActive().toString();
        nameTextView.setText(userName);
        statusTextView.setText(status);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


}
