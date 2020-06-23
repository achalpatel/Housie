package com.example.housie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private static final String TAG = "SignUpActivity";
    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;
    private Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameText = findViewById(R.id.id_name);
        emailText = findViewById(R.id.id_email);
        passwordText = findViewById(R.id.id_password);
        submit_btn = findViewById(R.id.btn_submit);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Toast.makeText(this, "Signed:" + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            emailText.setText(currentUser.getEmail());
            passwordText.setVisibility(View.GONE);
            if (currentUser.getDisplayName() != null) {
                nameText.setText(currentUser.getDisplayName());
            }
            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.child("users").child(currentUser.getUid()).getValue(User.class);
                    if(user!=null){
                        Log.d(TAG, "onDataChange: Username:" + user.getName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "onCancelled: Could not find User", error.toException());
                }
            };
            mDatabase.addValueEventListener(userListener);

        }
        Log.d(TAG, "onStart: " + currentUser);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitClick();
            }
        });

    }

    protected void submitClick() {
        if (nameText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Name", Toast.LENGTH_SHORT).show();
        } else if (currentUser != null) {
            this.updateProfile();
        } else {
            mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: User Signed Up successfully");
                                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                updateProfile();
                            } else {
                                Log.e(TAG, "onComplete: Sign up failed", task.getException());
                                Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void updateProfile() {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameText.getText().toString())
                .build();
        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Name Updated", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: Name Updated to " + currentUser.getDisplayName());
                    User user = new User(nameText.getText().toString(), emailText.getText().toString());
                    mDatabase.child("users").child(currentUser.getUid()).setValue(user);
                    finish();
                }
            }
        });
    }

}
