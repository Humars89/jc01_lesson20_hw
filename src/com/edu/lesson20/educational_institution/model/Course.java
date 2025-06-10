package com.edu.lesson20.educational_institution.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private final String courseName;
    private Teacher teacher;
    private Administrator administrator;
    private final List<Student> students = new ArrayList<>();

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public List<Student> getStudents() {
        return students;
    }
}
