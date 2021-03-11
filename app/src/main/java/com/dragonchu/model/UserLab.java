package com.dragonchu.model;

public class UserLab {
    private static UserLab user = new UserLab();
    private String username;
    private String email;
    private UserLab(){

    }
    public static UserLab get(){
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
