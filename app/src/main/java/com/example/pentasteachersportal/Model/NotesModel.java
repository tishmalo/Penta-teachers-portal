package com.example.pentasteachersportal.Model;

public class NotesModel {

    private String document, code, name;

    public NotesModel() {
    }

    public NotesModel(String document, String code, String name) {
        this.document = document;
        this.code = code;
        this.name = name;
    }

    public String getdocument() {
        return document;
    }

    public void setdocument(String document) {
        this.document = document;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

}
