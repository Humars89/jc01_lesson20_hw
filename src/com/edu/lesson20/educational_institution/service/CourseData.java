package com.edu.lesson20.educational_institution.service;

import com.edu.lesson20.educational_institution.model.Administrator;
import com.edu.lesson20.educational_institution.model.Student;
import com.edu.lesson20.educational_institution.model.Teacher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CourseData {
    private Teacher teacher;
    private Administrator administrator;
    private final Set<Student> students = new HashSet<>();

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public boolean addStudent(Student student) {
        return students.add(student);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }
}
