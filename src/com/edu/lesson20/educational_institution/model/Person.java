package com.edu.lesson20.educational_institution.model;

public abstract class Person {
    private final String fullName;

    public Person(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public abstract void performDuty();
}
