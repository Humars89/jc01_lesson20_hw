package com.edu.lesson20.educational_institution.main;

import com.edu.lesson20.educational_institution.controller.CourseController;
import com.edu.lesson20.educational_institution.repository.CourseRepository;
import com.edu.lesson20.educational_institution.service.CourseService;
import com.edu.lesson20.educational_institution.ui.CourseConsoleUI;

public class Main {
    public static void main(String[] args) {
        CourseService service = new CourseService("Java Programming");
        CourseRepository repository = new CourseRepository();
        CourseController controller = new CourseController(service, repository);
        CourseConsoleUI ui = new CourseConsoleUI(controller);

        ui.start();
    }
}
