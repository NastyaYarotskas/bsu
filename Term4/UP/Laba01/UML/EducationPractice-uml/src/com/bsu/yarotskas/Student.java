package com.bsu.yarotskas;

import java.util.Objects;

public class Student extends Person implements Notifable{

    private String login;
    private String email;

    public Student(String name, String login, String email) {////add name
        super(name);
        this.login = login;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void notify(String massege) {
        System.out.println(this + "message: " + massege);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(login, student.login) &&
                Objects.equals(email, student.email);
    }
}
