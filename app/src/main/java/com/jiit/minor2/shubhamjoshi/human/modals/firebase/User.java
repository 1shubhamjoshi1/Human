package com.jiit.minor2.shubhamjoshi.human.modals.firebase;

public class User {
    private String email;
    private String fullName;
    private String profileUrl;
    public User() {}

    public User(String fullName, String email, String profileUrl) {
        this.fullName = fullName;
        this.email = email;
        this.profileUrl = profileUrl;

    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return fullName;
    }
}
