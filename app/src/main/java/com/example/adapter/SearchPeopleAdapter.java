package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housie.R;
import com.example.model.UserProfile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

public class SearchPeopleAdapter extends RecyclerView.Adapter<SearchPeopleAdapter.ViewHolder> {
    private static final String TAG = "Achal-SearchPeopleAdapter";
    private List<UserProfile> userResult;
    private DatabaseReference mDatabase;

    public SearchPeopleAdapter(List<UserProfile> userResult){
        this.userResult = userResult;
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
        TextView nameTextView = holder.nameView;
        Button requestButton = holder.btnRequest;
        UserProfile user = userResult.get(position);
        nameTextView.setText(user.name);

    }

    @Override
    public int getItemCount() {
        return userResult.size();
    }


}
