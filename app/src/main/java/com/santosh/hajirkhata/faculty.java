package com.santosh.hajirkhata;
import com.google.firebase.firestore.Exclude;

public class faculty {

    public String fname;
    public String lanme;
    public String username;
    public String password;
    public faculty(){}
    public faculty(String fname,String lname,String username,String password){
        this.fname=fname;
        this.lanme=lanme;
        this.username=username;
        this.password=password;

    }

    public String getFname() {
        return fname;
    }

    public String getLanme() {
        return lanme;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

