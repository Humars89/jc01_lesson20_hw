package com.edu.lesson20.educational_institution.model;

public class Student extends Person {
    private final String group;
    private final double averageGrade;

    public Student(String fullName, String group, double averageGrade) {
        super(fullName);
        this.group = group;
        this.averageGrade = averageGrade;
    }

    public String getGroup() {
        return group;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void participate() {
        System.out.println("Студент " + getFullName() + " участвует в занятии.");
    }

    @Override
    public void performDuty() {
        participate();
    }
}
