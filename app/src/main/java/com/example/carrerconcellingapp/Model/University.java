package com.example.carrerconcellingapp.Model;

public class University {
    private String Abb,Name,MinReq;

    public University() {
    }

    public University(String abb, String name, String minReq) {
        Abb = abb;
        Name = name;
        MinReq = minReq;
    }

    public String getAbb() {
        return Abb;
    }

    public void setAbb(String abb) {
        Abb = abb;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMinReq() {
        return MinReq;
    }

    public void setMinReq(String minReq) {
        MinReq = minReq;
    }
}
