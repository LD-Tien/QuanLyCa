package com.ldt.quanlyca;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String userPassword;
    private String userFullName;
    private String email;

    public User() {
    }

    public User(String userName, String userPassword, String userFullName, String email) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userFullName = userFullName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
