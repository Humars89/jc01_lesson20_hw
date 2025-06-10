package com.edu.lesson20.educational_institution.service;

import com.edu.lesson20.educational_institution.model.*;

public class CourseService {
    private final Course course;

    public CourseService(String courseName) {
        this.course = new Course(courseName);
    }

    public void assignTeacher(Teacher teacher) {
        course.setTeacher(teacher);
    }

    public void assignAdministrator(Administrator administrator) {
        course.setAdministrator(administrator);
    }

    public boolean addStudent(Student student) {
        for (Student s : course.getStudents()) {
            if (s.getFullName().equals(student.getFullName())) {
                return false;
            }
        }
        course.addStudent(student);
        return true;
    }

    public Course getCourse() {
        return course;
    }

    public void conductSession() {
        System.out.println("Занятие началось: " + course.getCourseName());
        if (course.getAdministrator() != null) {
            course.getAdministrator().performDuty();
        }
        if (course.getTeacher() != null) {
            course.getTeacher().performDuty();
        }
        for (Student student : course.getStudents()) {
            student.performDuty();
        }
    }
}
