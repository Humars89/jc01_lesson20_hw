package com.edu.lesson20.educational_institution.controller;

import com.edu.lesson20.educational_institution.model.Course;
import com.edu.lesson20.educational_institution.model.Student;
import com.edu.lesson20.educational_institution.service.CourseService;
import com.edu.lesson20.educational_institution.repository.CourseRepository;

public class CourseController {
    private final CourseService service;
    private final CourseRepository repository;

    public CourseController(CourseService service, CourseRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    public void assignTeacher(String fullName) {
        service.assignTeacher(new com.edu.lesson20.educational_institution.model.Teacher(fullName));
    }

    public void assignAdministrator(String fullName) {
        service.assignAdministrator(new com.edu.lesson20.educational_institution.model.Administrator(fullName));
    }

    public boolean addStudent(String fullName, String group, double avgGrade) {
        return service.addStudent(new Student(fullName, group, avgGrade));
    }

    public void conductSession() {
        service.conductSession();
    }

    public Course getCourse() {
        return service.getCourse();
    }

    public void saveCourse() {
        repository.saveCourse(getCourse());
    }
}
