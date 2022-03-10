package com.example.churchmanagementsystem.model;

import java.util.ArrayList;

public class User {

    private String uname;
    private String pass;
    private String type;
    private ArrayList<String> scripture;
    private ArrayList<String> prayer;
    private String branch;

    public User(String uname, String pass, String type, ArrayList<String> scripture, ArrayList<String> prayer, String branch) {
        this.uname = uname;
        this.pass = pass;
        this.type = type;
        this.scripture = scripture;
        this.prayer = prayer;
        this.branch = branch;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getScripture() {
        return scripture;
    }

    public void setScripture(ArrayList<String> scripture) {
        this.scripture = scripture;
    }

    public ArrayList<String> getPrayer() {
        return prayer;
    }

    public void setPrayer(ArrayList<String> prayer) {
        this.prayer = prayer;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
