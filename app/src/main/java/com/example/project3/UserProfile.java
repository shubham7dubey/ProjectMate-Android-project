package com.example.project3;
public class UserProfile {


    public  String fname,course,department,mobile,roll;

    public UserProfile(){

    }
    public UserProfile(String fname,String course,String department,String mobile,String roll){
        this.fname=fname;
        this.course  =course ;
        this.department  =department ;
        this.mobile  =mobile ;
        this.roll=roll;
    }

    public String getfname() {
        return fname;
    }

    public void setfname(String fname) {
        this.fname = fname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
