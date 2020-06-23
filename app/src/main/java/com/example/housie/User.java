package com.example.housie;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {
    public String name;
    public String email;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

}
