package com.example.foobook_android;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    private String username;
    private String password;
    private String displayName;
    private String profilePicturePath;


    @PrimaryKey(autoGenerate = true)
    private int id;
    public User(String username, String password, String displayName, String profilePicturePath) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePicturePath = profilePicturePath;
    }

    public User(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePicturePath = "drawable/defaultpic.png";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
