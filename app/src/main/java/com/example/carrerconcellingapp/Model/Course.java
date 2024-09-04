package com.example.carrerconcellingapp.Model;

public class Course {
    private String Code,Name,Video,Subject1,Subject2,Mark1,Mark2,Aps,Infomation,Faculty,University;

    public Course() {
    }

    public Course(String code, String name, String video, String subject1, String subject2, String mark1, String mark2, String aps, String infomation, String faculty, String university) {
        Code = code;
        Name = name;
        Video = video;
        Subject1 = subject1;
        Subject2 = subject2;
        Mark1 = mark1;
        Mark2 = mark2;
        Aps = aps;
        Infomation = infomation;
        Faculty = faculty;
        University = university;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getSubject1() {
        return Subject1;
    }

    public void setSubject1(String subject1) {
        Subject1 = subject1;
    }

    public String getSubject2() {
        return Subject2;
    }

    public void setSubject2(String subject2) {
        Subject2 = subject2;
    }

    public String getMark1() {
        return Mark1;
    }

    public void setMark1(String mark1) {
        Mark1 = mark1;
    }

    public String getMark2() {
        return Mark2;
    }

    public void setMark2(String mark2) {
        Mark2 = mark2;
    }

    public String getAps() {
        return Aps;
    }

    public void setAps(String aps) {
        Aps = aps;
    }

    public String getInfomation() {
        return Infomation;
    }

    public void setInfomation(String infomation) {
        Infomation = infomation;
    }
}
