package com.example.ankitraj.firebasedemo;

public class Person {
    String name;
    String email;
    String password;

    public Person(String name, String email, String password) {
        this.name=name;
        this.email=email;
        this.password=password;
    }
    Person()
    {

    }

    public Person(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
