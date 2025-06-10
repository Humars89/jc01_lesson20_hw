package com.edu.lesson20.educational_institution.model;

public class Teacher extends Person {

    public Teacher(String fullName) {
        super(fullName);
    }

    public void teach() {
        System.out.println("Преподаватель " + getFullName() + " проводит занятие.");
    }

    @Override
    public void performDuty() {
        teach();
    }
}
