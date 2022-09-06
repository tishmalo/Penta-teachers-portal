package com.example.pentasteachersportal.Model;

public class PostsModel {

    private String photo,title,description;

    public PostsModel() {
    }

    public PostsModel(String photo, String title, String description) {
        this.photo = photo;
        this.title = title;
        this.description = description;
    }

    public String getphoto() {
        return photo;
    }

    public void setphoto(String photo) {
        this.photo = photo;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }
}
