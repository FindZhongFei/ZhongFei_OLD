package com.fzhongfei.findzhongfei_final.model;

import java.io.Serializable;

public class ChatUser implements Serializable {

    private String token, name, email, profileUrl;

    public ChatUser() {
    }

    public ChatUser(String token, String name, String email) {
        this.token = token;
        this.name = name;
        this.email = email;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
