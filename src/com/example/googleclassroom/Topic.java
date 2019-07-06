package com.example.googleclassroom;

import java.io.Serializable;
import java.util.ArrayList;

public class Topic implements Serializable {

    private static final long serialVersionUID = 93074495853L;

    String topicname;
    ArrayList<Assignment> assignments;
    public Topic(String topicname) {
        this.topicname = topicname;
        this.assignments = new ArrayList<Assignment>();
    }

    @Override
    public boolean equals(Object obj) {
        return topicname.equals(((Topic)obj).topicname);
    }
}
