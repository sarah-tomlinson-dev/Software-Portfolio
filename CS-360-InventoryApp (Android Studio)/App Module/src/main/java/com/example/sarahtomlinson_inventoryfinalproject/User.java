package com.example.sarahtomlinson_inventoryfinalproject;

public class User {

    int id;
    String user_name;
    String user_pass;

    public User() {
        super();
    }

    public User(int i, String name, String password) {
        super();
        this.id = i;
        this.user_name = name;
        this.user_pass = password;
    }

    // constructor
    public User(String name, String pass){
        this.user_name = name;
        this.user_pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String name) {
        this.user_name = name;
    }

    public String getUserPass() {
        return user_pass;
    }

    public void setUserPass(String pass) {
        this.user_pass = pass;
    }
}