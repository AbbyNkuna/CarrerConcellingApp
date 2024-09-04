package com.example.carrerconcellingapp.Model;

public class Job {
    private String Fac,Info;

    public Job() {
    }

    public Job(String fac, String info) {
        Fac = fac;
        Info = info;
    }

    public String getFac() {
        return Fac;
    }

    public void setFac(String fac) {
        Fac = fac;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }
}
