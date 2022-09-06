package com.example.pentasteachersportal.Model;

public class ResultsModel {

    private String opening,username,code;

    public ResultsModel() {
    }

    public ResultsModel(String opening, String username, String code) {
        this.opening = opening;
        this.username = username;
        this.code = code;
    }

    public String getopening() {
        return opening;
    }

    public void setopening(String opening) {
        this.opening = opening;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }
}
