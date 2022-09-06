package com.example.pentasteachersportal.Model;

public class StudentModel {
    private String code, username;

    public StudentModel() {
    }

    public StudentModel(String code, String username) {
        this.code = code;
        this.username = username;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }
}
