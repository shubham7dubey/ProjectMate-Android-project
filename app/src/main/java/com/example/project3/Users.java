package com.example.project3;

public class Users {

    private  String id,Fname,Dept,Roll,Course,Email,Mno;
    public  Users(){

    }

    public Users(String id, String fname, String dept, String roll, String course, String email, String mno) {
        this.id = id;
        Fname = fname;
        Dept = dept;
        Roll = roll;
        Course = course;
        Email = email;
        Mno = mno;
    }

    public String getId() {
        return id;
    }

    public String getFname() {
        return Fname;
    }

    public String getDept() {
        return Dept;
    }

    public String getRoll() {
        return Roll;
    }

    public String getCourse() {
        return Course;
    }

    public String getEmail() {
        return Email;
    }

    public String getMno() {
        return Mno;
    }
}
