package com.example.latihancrudfirebase.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String email;
    private String nomor;
    private String key;

    public User() {
    }

    public User(String username, String email, String nomor) {
        this.username = username;
        this.email = email;
        this.nomor = nomor;
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



    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String toString(){
        return " " + username + "\n" +
                " " + email + "\n" +
                " "+nomor;
    }
}
