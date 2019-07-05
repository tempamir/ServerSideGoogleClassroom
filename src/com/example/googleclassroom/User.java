package com.example.googleclassroom;

//import androidx.annotation.Nullable;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 9307449585L;
    String username;
    String password;
    byte[] picture;
    ArrayList<Class> classes = new ArrayList<>();
    boolean logedIn = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, byte[] picture) {
        this.username = username;
        this.password = password;
        this.picture = picture;
    }

//    @Override
//    public boolean equals(@Nullable Object obj) {
//        return this.username.equals(((User)obj).username);
//    }

    static ArrayList<User> users = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public String getClasses() {
        return Integer.toString(classes.size());
    }
    public int findClass (Class myclass)
    {
        return this.classes.indexOf(myclass);
    }

    //reading
    static {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("users.ser"))) {
            users = (ArrayList<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
        }
    }

    //writing
    static  void save() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.ser"))) {
            outputStream.writeObject(users);
            System.out.println("wrote");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}