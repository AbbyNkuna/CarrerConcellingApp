package com.example.carrerconcellingapp.Model;

public class User {
    private String Name,Surname,Contact;

    public User() {
    }

    public User(String name, String surname, String contact) {
        Name = name;
        Surname = surname;
        Contact = contact;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }
}
