package com.edu.lesson20.educational_institution.ui;

import com.edu.lesson20.educational_institution.controller.CourseController;
import com.edu.lesson20.educational_institution.model.Student;
import com.edu.lesson20.educational_institution.model.Course;

import java.util.Scanner;

public class CourseConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final CourseController controller;

    public CourseConsoleUI(CourseController controller) {
        this.controller = controller;
    }

    public void start() {
        displayWelcome();
        setupCourse();
        runMainMenu();
    }

    private void displayWelcome() {
        System.out.println("=== СИСТЕМА УПРАВЛЕНИЯ КУРСОМ ===");
    }

    private void setupCourse() {
        System.out.println("Настройка курса:");
        System.out.print("Введите имя преподавателя: ");
        String teacherName = scanner.nextLine().trim();
        controller.assignTeacher(teacherName);

        System.out.print("Введите имя администратора: ");
        String adminName = scanner.nextLine().trim();
        controller.assignAdministrator(adminName);

        System.out.println("Курс настроен.\n");
    }

    private void runMainMenu() {
        while (true) {
            printMainMenu();
            int choice = readIntInput("Выберите опцию: ", 1, 5);
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> conductSession();
                case 3 -> generateReport();
                case 4 -> saveCourse();
                case 5 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Добавить студента");
        System.out.println("2. Провести занятие");
        System.out.println("3. Показать отчет по студентам");
        System.out.println("4. Сохранить курс в файл");
        System.out.println("5. Выход");
    }

    private void addStudent() {
        System.out.print("Введите имя студента: ");
        String name = scanner.nextLine().trim();
        System.out.print("Введите группу студента: ");
        String group = scanner.nextLine().trim();
        double avgGrade = readDoubleInput("Введите средний балл студента: ");

        boolean added = controller.addStudent(name, group, avgGrade);
        if (added) {
            System.out.println("Студент успешно добавлен.");
        } else {
            System.out.println("Студент уже есть в списке или введены неверные данные.");
        }
    }

    private void conductSession() {
        System.out.println("Проведение занятия...");
        controller.conductSession();
    }

    private void generateReport() {
        Course course = controller.getCourse();
        System.out.println("\n=== Отчет по курсу ===");
        System.out.println("Преподаватель: " + (course.getTeacher() != null ? course.getTeacher().getFullName() : "не назначен"));
        System.out.println("Администратор: " + (course.getAdministrator() != null ? course.getAdministrator().getFullName() : "не назначен"));
        System.out.println("Студенты:");
        if (course.getStudents().isEmpty()) {
            System.out.println("  Нет студентов в курсе.");
        } else {
            course.getStudents().stream()
                .sorted((s1, s2) -> Double.compare(s2.getAverageGrade(), s1.getAverageGrade()))
                .forEach(student -> System.out.printf("  %s, Группа: %s, Средний балл: %.2f%n",
                        student.getFullName(), student.getGroup(), student.getAverageGrade()));
            double courseAvg = course.getStudents().stream()
                    .mapToDouble(Student::getAverageGrade)
                    .average()
                    .orElse(0.0);
            System.out.printf("Средний балл по курсу: %.2f%n", courseAvg);
        }
    }

    private void saveCourse() {
        controller.saveCourse();
        System.out.println("Данные курса сохранены в файл.");
    }

    private int readIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value < min || value > max) {
                    System.out.printf("Введите число от %d до %d.%n", min, max);
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите корректное число.");
            }
        }
    }

    private double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите корректное число с плавающей точкой.");
            }
        }
    }
}
