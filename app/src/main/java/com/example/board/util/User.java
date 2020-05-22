package com.example.board.util;

public class User {
    private String user_name, user_email;
    private int id;
    User(int id, String user_name, String user_email) {
        this.id = id;
        this.user_name = user_name;
        this.user_email = user_email;
    }
    String getUsername() {
        return user_name;
    }
    String getEmail() {
        return user_email;
    }

    public int getId() {
        return id;
    }
}
