package com.amayadream.jpa.model;

import javax.persistence.*;

/**
 * @author :  Amayadream
 * @date :  2017.03.14 20:16
 */
@Entity
@Table(name = "tab_user")
public class User {

    @Id
    private int userId;

    private String profile;

    public User() {
    }

    public User(int userId, String profile) {
        this.userId = userId;
        this.profile = profile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
