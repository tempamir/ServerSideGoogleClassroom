package com.example.googleclassroom;

//import androidx.annotation.Nullable;

import java.io.*;
import java.util.ArrayList;

public class Class implements Serializable {
    private static final long serialVersionUID = 93074495851L;
    String id;
    int index;
    String name;
    String description;
    String[] notification;
    String roomNumber;
    ArrayList<User> teachers;
    ArrayList<User> students;
    ArrayList<Topic> topics;


    public Class(String name, String description, String roomNumber , String id , int index) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.teachers = new ArrayList<User>();
        this.students =  new ArrayList<User>();;
        this.topics = new ArrayList<Topic>();
        this.index = index;
    }


    public void setTeachers(ArrayList<User> teachers) {
        this.teachers = teachers;
    }

    public void setNotification(String[] notification) {
        this.notification = notification;
    }

    public String[] getNotification() {
        return notification;
    }

    static ArrayList<Class> classes = new ArrayList<>();

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public int getStudentsSize() {
        return this.students.size();
    }
    public boolean findTeacher(User myuser)
    {
        return this.teachers.contains(myuser);
    }

//    @Override
//    public boolean equals(@Nullable Object obj) {
//        return this.id.equals(((Class)obj).id);
//    }

    public ArrayList<User> getTeachers() {
        return teachers;
    }

    //reading
    static {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("classes.ser"))) {
            classes = (ArrayList<Class>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            classes = new ArrayList<>();
        }
    }

    //writing
    static  void save() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("classes.ser"))) {
            outputStream.writeObject(classes);
            System.out.println("wrote");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
