package com.example.pentasteachersportal.Model;

public class AttendanceModel {
    private String year,date, username;

    public AttendanceModel() {
    }

    public AttendanceModel(String year, String date, String username) {
        this.year = year;
        this.date = date;
        this.username = username;
    }

    public String getyear() {
        return year;
    }

    public void setyear(String year) {
        this.year = year;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }
}
