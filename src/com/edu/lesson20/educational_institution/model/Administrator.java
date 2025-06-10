package com.edu.lesson20.educational_institution.model;

public class Administrator extends Person {

    public Administrator(String fullName) {
        super(fullName);
    }

    public void support() {
        System.out.println("Администратор " + getFullName() + " обеспечивает процесс.");
    }

    @Override
    public void performDuty() {
        support();
    }
}
