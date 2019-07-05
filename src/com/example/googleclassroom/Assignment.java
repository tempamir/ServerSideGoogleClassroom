package com.example.googleclassroom;

import java.io.Serializable;

public class Assignment  implements Serializable {

    private static final long serialVersionUID = 93074495852L;

    String name;
    String description;
    int point;
    String time;
    String date;
    Topic thisTopic;

    public Assignment(String name, String description, int point, String time, String date, Topic thisTopic) {
        this.name = name;
        this.description = description;
        this.point = point;
        this.time = time;
        this.date = date;
        this.thisTopic = thisTopic;
    }

}
