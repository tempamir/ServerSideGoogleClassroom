package com.example.googleclassroom;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class ClientHandler extends Thread {

    static ArrayList<ClientHandler> clients = new ArrayList<>();

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public ClientHandler(Socket socket){
        System.out.println("socket");
        this.socket = socket;
        //clients.add(this);
    }

    @Override
    public void run() {
        try {
//            System.out.println("client run");
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
//            System.out.println("here here");
            String[] str = (String[])in.readObject();
            System.out.println(str[0]);
//            for (int i = 0; i < b.length; i++) {
//                System.out.println(b[i]);
//            }

            if (str[0].equals("sign_in")){
                boolean sign = false;
                int i;
                System.out.println("sign in");
                for (i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[1]) && User.users.get(i).password.equals(str[2])){
                        sign = true;
                        System.out.println("user signed in");
                        break;
                    }
                }
                out.writeBoolean(sign);
                out.flush();
                if (sign){
                    out.writeObject(User.users.get(i));
                    out.flush();
                }
            }
            else if (str[0].equals("username_check")){
                boolean b = false;

                System.out.println("username finding");

                for (int i = 0; i < User.users.size(); i++) {
                    if(User.users.get(i).username.equals(str[1])){
                        b = true;
                    }
                }

                out.writeBoolean(b);
                out.flush();

            }
            else if (str[0].equals("register")){

                boolean b = true;

                System.out.println("registration");

                for (int i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[1])){
                        b = false;
                    }
                }

                if (!str[2].equals(str[3])){
                    b = false;
                }

                out.writeBoolean(b);
                out.flush();

                if (b) {

                    byte[] picture = (byte[]) in.readObject();
                    User tempUser = new User(str[1] , str[2] , picture);
                    User.users.add(tempUser);
                    User.save();
                    out.writeObject(User.users.get(User.users.size()-1));
                    out.flush();

                    System.out.println("hello");
                    for (int i = 0; i < picture.length; i++) {
                        System.out.print(picture[i] + "  ");
                    }
                    System.out.println("bye");

                }

            }

            else if (str[0].equals("register_username_check")){

                boolean b = true;

                for (int i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[1])){
                        b = false;
                        break;
                    }
                }

                out.writeBoolean(b);
                out.flush();

            }

            else if (str[0].equals("refresh_main_page")){

                int i;
                for ( i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[1])){
                        break;
                    }
                }

                out.writeObject(User.users.get(i));
                out.flush();

            }

            else if (str[0].equals("refresh_classes")){

                int i;
                for ( i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[1])){
                        break;
                    }
                }

                int j;
                for (j = 0; j < Class.classes.size(); j++) {
                    if (Class.classes.get(j).name.equals(str[2])){
                        break;
                    }
                }

                out.writeObject(User.users.get(i));
                out.flush();
                out.writeObject(Class.classes.get(j));
                out.flush();

            }

            else if (str[0].equals("createclass_name_check")){

                boolean b = true;

                for (int i = 0; i < Class.classes.size(); i++) {
                    if (Class.classes.get(i).name.equals(str[1])){
                        b = false;
                        break;
                    }
                }

                out.writeBoolean(b);
                out.flush();

            }

            else if (str[0].equals("createclass_roomnumber_check")){

                boolean b = true;

                for (int i = 0; i < Class.classes.size(); i++) {
                    if (Class.classes.get(i).roomNumber.equals(str[1])){
                        b = false;
                        break;
                    }
                }

                out.writeBoolean(b);
                out.flush();

            }

            else if (str[0].equals("create_class")){

                boolean b = true;

                for (int i = 0; i < Class.classes.size(); i++) {
                    if (str[1].equals(Class.classes.get(i).name) || str[3].equals(Class.classes.get(i).roomNumber)){
                        b = false;
                    }
                }

                out.writeBoolean(b);
                out.flush();

                if (b){
                    int j;
                    for (j = 0; j < User.users.size(); j++) {
                        if (User.users.get(j).username.equals(str[4])){
                            System.out.println("user found");
                            break;
                        }
                    }
                    System.out.println(j);
                    String ID = UUID.randomUUID().toString();
                    ID  = ID.substring(0,6);
                    System.out.println(ID);
                    int last = Class.classes.size();
                    Class thisClass = new Class(str[1] , str[2] , str[3] , ID , last);
                    thisClass.topics.add(new Topic("no topic"));
                    Class.classes.add(thisClass);
                    System.out.println(Class.classes.get(last).name);
                    System.out.println(last);
                    Class.classes.get(last).teachers.add(User.users.get(j));
                    thisClass.teachers.add(User.users.get(j));
                    User.users.get(j).classes.add(thisClass);
                    out.writeObject(thisClass);
                    out.flush();
                    out.writeObject(User.users.get(j));
                    out.flush();

                    User.save();
                    Class.save();

                }

            }

            else if (str[0].equals("join_class")){

                int check = 0;
                int i;

                for (i = 0; i < Class.classes.size(); i++) {
                    if (Class.classes.get(i).id.equals(str[1])){
                        check = 1;
                        if (Class.classes.get(i).teachers.get(0).username.equals(str[2])){
                            check = 2;
                        }
                        for (int k = 0; k < Class.classes.get(i).students.size(); k++) {
                            if (Class.classes.get(i).students.get(k).username.equals(str[2])){
                                check = 3;
                            }
                        }
                        break;
                    }
                }

                out.writeInt(check);
                out.flush();



                if (check == 1) {
                    int j;
                    for (j = 0; j < User.users.size(); j++) {
                        if (User.users.get(j).username.equals(str[2])){
                            break;
                        }
                    }
                    Class.classes.get(i).students.add(User.users.get(j));
                    User.users.get(j).classes.add(Class.classes.get(i));

                    out.writeObject(Class.classes.get(i));
                    out.flush();
                    out.writeObject(User.users.get(j));
                    out.flush();

                    User.save();
                    Class.save();

                }

            }

            else if (str[0].equals("student_remove")){

                int i , j;
                for (i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[1])){
                        break;
                    }
                }
                for ( j = 0; j < Class.classes.size(); j++) {
                    if (Class.classes.get(j).name.equals(str[2])){
                        break;
                    }
                }
                int a;
                for (a = 0; a < User.users.get(i).classes.size(); a++) {
                    if (User.users.get(i).classes.get(a).name.equals(str[2])){
                        break;
                    }
                }
                int b;
                for (b = 0; b < Class.classes.get(j).students.size(); b++) {
                    if (Class.classes.get(j).students.get(b).username.equals(str[1])){
                        System.out.println("hey");
                        break;
                    }
                }

                System.out.println(a);
                System.out.println(b);

                User.users.get(i).classes.remove(a);
                Class.classes.get(j).students.remove(b);

                out.writeObject(User.users.get(i));
                out.flush();
                User.save();

                out.writeObject(Class.classes.get(j));
                out.flush();
                Class.save();

                for(int k = 0; k < Class.classes.get(j).students.size(); k++){
                    System.out.println(Class.classes.get(j).students.get(k).username);
                }

            }

            else if (str[0].equals("teacher_remove")){

                int i , j;
                for (i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[1])){
                        break;
                    }
                }
                for ( j = 0; j < Class.classes.size(); j++) {
                    if (Class.classes.get(j).name.equals(str[2])){
                        break;
                    }
                }

                int a;
                for (a = 0; a < User.users.get(i).classes.size(); a++) {
                    if (User.users.get(i).classes.get(a).name.equals(str[2])){
                        break;
                    }
                }
                int b;
                for (b = 0; b < Class.classes.get(j).teachers.size(); b++) {
                    if (Class.classes.get(j).teachers.get(b).username.equals(str[1])){
                        System.out.println("hey");
                        break;
                    }
                }

                System.out.println(a);
                System.out.println(b);


                User.users.get(i).classes.remove(a);
                Class.classes.get(j).teachers.remove(b);

                out.writeObject(User.users.get(i));
                out.flush();
                User.save();

                out.writeObject(Class.classes.get(j));
                out.flush();
                Class.save();

            }

            else if (str[0].equals("student_leave")){

                int i , j;
                for (i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[1])){
                        break;
                    }
                }
                for ( j = 0; j < Class.classes.size(); j++) {
                    if (Class.classes.get(j).name.equals(str[2])){
                        break;
                    }
                }
                int a;
                for (a = 0; a < User.users.get(i).classes.size(); a++) {
                    if (User.users.get(i).classes.get(a).name.equals(str[2])){
                        break;
                    }
                }
                int b;
                for (b = 0; b < Class.classes.get(j).students.size(); b++) {
                    if (Class.classes.get(j).students.get(b).username.equals(str[1])){
                        System.out.println("hey");
                        break;
                    }
                }

                System.out.println(a);
                System.out.println(b);

                User.users.get(i).classes.remove(a);
                Class.classes.get(j).students.remove(b);

                out.writeObject(User.users.get(i));
                out.flush();
                User.save();
                Class.save();

                for(int k = 0; k < Class.classes.get(j).students.size(); k++){
                    System.out.println(Class.classes.get(j).students.get(k).username);
                }

            }

            else if (str[0].equals("add_topic")){
                System.out.println("add_topic is here");

                int i;
                for (i = 0; i < User.users.size(); i++) {
                    if (User.users.get(i).username.equals(str[2])){
                        System.out.println("user found");
                        break;
                    }
                }
                int j;
                for (j = 0; j < Class.classes.size(); j++) {
                    if (Class.classes.get(j).name.equals(str[3])){
                        System.out.println("class found");
                        System.out.println(j);
                        break;
                    }
                }

                int a;
                for (a = 0; a < User.users.get(i).classes.size(); a++) {
                    if (User.users.get(i).classes.get(a).name.equals(str[3])){
                        System.out.println(a);
                        break;
                    }
                }

                Topic tempTopic = new Topic(str[1]);
                if (User.users.get(i).classes.get(a).topics.size()==0)
                    User.users.get(i).classes.get(a).topics.add(new Topic("no topic"));
                User.users.get(i).classes.get(a).topics.add(tempTopic);
                for (int k = 0; k < User.users.get(i).classes.get(a).topics.size(); k++) {
                    System.out.println(User.users.get(i).classes.get(a).topics.get(k).topicname);
                }

            }





            in.close();
            out.close();
            socket.close();

        }
        catch (IOException e2){
            e2.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
