package com.jiit.minor2.shubhamjoshi.human.modals.firebase;

public class User {
    private String email;
    private String fullName;
    public User() {}
    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return fullName;
    }
}
