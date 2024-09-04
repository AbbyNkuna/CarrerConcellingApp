package com.example.carrerconcellingapp.Model;

public class Booking {
    private String Date,Course,UserID,Link,Approval,Time;

    public Booking() {
    }

    public Booking(String date, String course, String userID, String link, String approval, String time) {
        Date = date;
        Course = course;
        UserID = userID;
        Link = link;
        Approval = approval;
        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getApproval() {
        return Approval;
    }

    public void setApproval(String approval) {
        Approval = approval;
    }
}
