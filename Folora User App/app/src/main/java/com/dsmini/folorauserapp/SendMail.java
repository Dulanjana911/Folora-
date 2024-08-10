package com.dsmini.folorauserapp;

public class SendMail {

    String title;
    String message;

    public SendMail(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
