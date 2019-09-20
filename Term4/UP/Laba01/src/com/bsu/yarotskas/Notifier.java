package com.bsu.yarotskas;

import java.util.Set;

public class Notifier {

    private Set<? extends Notifable> students;

    public Notifier(Set<? extends Notifable> students) {
        this.students = students;
    }

    public void doNotifyAll(String message){
        for(Notifable student : students)
            student.notify(message);
    }
}
