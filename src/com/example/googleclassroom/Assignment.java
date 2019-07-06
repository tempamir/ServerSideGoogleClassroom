package com.example.googleclassroom;

import java.io.Serializable;

public class Assignment implements Serializable {

    private static final long serialVersionUID = 93074495852L;

    String name;
    String description;
    String point;
    String time;
    String date;
    byte[] attach;
    public Assignment(String name, String description, String time, String date, String point,byte[] pic) {
        this.name = name;
        this.description = description;
        this.point = point;
        this.time = time;
        this.date = date;
        this.attach = pic;
    }

}
