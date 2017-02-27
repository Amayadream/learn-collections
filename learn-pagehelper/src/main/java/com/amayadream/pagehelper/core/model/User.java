package com.amayadream.pagehelper.core.model;

import org.springframework.stereotype.Repository;

/**
 * @author :  Amayadream
 * @date :  2017.02.23 17:52
 */
@Repository
public class User {

    private String userId;
    private String realName;

    public User() {
    }

    public User(String userId, String realName) {
        this.userId = userId;
        this.realName = realName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
