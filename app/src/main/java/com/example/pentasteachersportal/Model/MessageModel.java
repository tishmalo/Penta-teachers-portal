package com.example.pentasteachersportal.Model;

public class MessageModel {

    private String sender, receiver, message, email;

    public MessageModel() {
    }

    public MessageModel(String sender, String receiver, String message, String email) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.email = email;
    }

    public String getsender() {
        return sender;
    }

    public void setsender(String sender) {
        this.sender = sender;
    }

    public String getreceiver() {
        return receiver;
    }

    public void setreceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
}
