package com.example.pentasteachersportal.Model;

public class UnitsModel {

    private String code, userid,username;

    public UnitsModel() {

    }

    public UnitsModel(String code, String userid, String username) {
        this.code = code;
        this.userid = userid;
        this.username = username;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userId) {
        this.userid = userId;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }
}
