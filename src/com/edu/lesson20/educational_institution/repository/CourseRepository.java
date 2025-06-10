package com.edu.lesson20.educational_institution.repository;

import com.edu.lesson20.educational_institution.model.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class CourseRepository {
    private static final String COURSE_SEPARATOR = "---";
    private static final Path DATA_DIR = Path.of("data");
    private static final Path FILE = DATA_DIR.resolve("courses.txt");

    public CourseRepository() {
        initializeRepository();
    }

    private void initializeRepository() {
        try {
            if (!Files.exists(DATA_DIR)) {
                Files.createDirectory(DATA_DIR);
            }
            if (!Files.exists(FILE)) {
                Files.createFile(FILE);
            }
        } catch (IOException e) {
            throw new RepositoryInitializationException("Ошибка инициализации репозитория", e);
        }
    }

    public void saveCourse(Course course) {
        Objects.requireNonNull(course, "Курс не может быть null");
        
        List<Course> existingCourses = loadAllCourses();
        checkForDuplicate(course, existingCourses);

        try (BufferedWriter writer = Files.newBufferedWriter(FILE, 
                StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            
            writeCourseData(writer, course);
            writer.write(COURSE_SEPARATOR + "\n");
            
        } catch (IOException e) {
            throw new RepositoryOperationException("Ошибка сохранения курса", e);
        }
    }

    private void checkForDuplicate(Course newCourse, List<Course> existingCourses) {
        boolean duplicateExists = existingCourses.stream()
                .anyMatch(c -> c.getCourseName().equalsIgnoreCase(newCourse.getCourseName()));
        
        if (duplicateExists) {
            throw new DuplicateCourseException("Курс с именем '" + newCourse.getCourseName() + "' уже существует");
        }
    }

    private void writeCourseData(BufferedWriter writer, Course course) throws IOException {
        writer.write("Курс: " + course.getCourseName() + "\n");
        writer.write("Преподаватель: " + getPersonName(course.getTeacher()) + "\n");
        writer.write("Администратор: " + getPersonName(course.getAdministrator()) + "\n");
        
        writer.write("Студенты:\n");
        for (Student student : course.getStudents()) {
            writer.write(String.format("- %s, группа: %s, балл: %.1f\n",
                    student.getFullName(),
                    student.getGroup(),
                    student.getAverageGrade()));
        }
    }

    private String getPersonName(Person person) {
        return person != null ? person.getFullName() : "не назначен";
    }

    public List<Course> getAllCourses() {
        return loadAllCourses();
    }

    private List<Course> loadAllCourses() {
        try {
            if (Files.size(FILE) == 0) {
                return Collections.emptyList();
            }

            List<String> lines = Files.readAllLines(FILE);
            return parseCourses(lines);
        } catch (IOException e) {
            throw new RepositoryOperationException("Ошибка загрузки курсов", e);
        }
    }

    private List<Course> parseCourses(List<String> lines) {
        List<Course> courses = new ArrayList<>();
        Course currentCourse = null;
        boolean processingStudents = false;

        for (String line : lines) {
            line = line.trim();
            
            if (line.isEmpty() || line.equals(COURSE_SEPARATOR)) {
                if (currentCourse != null) {
                    courses.add(currentCourse);
                    currentCourse = null;
                }
                processingStudents = false;
                continue;
            }

            if (line.startsWith("Курс: ")) {
                currentCourse = new Course(line.substring("Курс: ".length()));
            } 
            else if (line.startsWith("Преподаватель: ")) {
                if (currentCourse != null && !line.endsWith("не назначен")) {
                    currentCourse.setTeacher(new Teacher(line.substring("Преподаватель: ".length())));
                }
            }
            else if (line.startsWith("Администратор: ")) {
                if (currentCourse != null && !line.endsWith("не назначен")) {
                    currentCourse.setAdministrator(new Administrator(line.substring("Администратор: ".length())));
                }
            }
            else if (line.equals("Студенты:")) {
                processingStudents = true;
            }
            else if (processingStudents && line.startsWith("- ")) {
                if (currentCourse != null) {
                    currentCourse.addStudent(parseStudent(line));
                }
            }
        }

        if (currentCourse != null) {
            courses.add(currentCourse);
        }

        return courses;
    }

    private Student parseStudent(String line) {
        String studentData = line.substring(2);
        String[] parts = studentData.split(", ");
        
        return new Student(
            parts[0],
            parts[1].substring("группа: ".length()),
            Double.parseDouble(parts[2].substring("балл: ".length()))
        );
    }

    public void updateCourse(Course updatedCourse) {
        Objects.requireNonNull(updatedCourse, "Обновляемый курс не может быть null");
        
        List<Course> courses = loadAllCourses();
        removeCourse(updatedCourse.getCourseName(), courses);
        courses.add(updatedCourse);
        
        saveAllCourses(courses);
    }

    public void deleteCourse(String courseName) {
        List<Course> courses = loadAllCourses();
        removeCourse(courseName, courses);
        saveAllCourses(courses);
    }

    private void removeCourse(String courseName, List<Course> courses) {
        courses.removeIf(c -> c.getCourseName().equalsIgnoreCase(courseName));
    }

    private void saveAllCourses(List<Course> courses) {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE, 
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            
            for (Course course : courses) {
                writeCourseData(writer, course);
                writer.write(COURSE_SEPARATOR + "\n");
            }
            
        } catch (IOException e) {
            throw new RepositoryOperationException("Ошибка сохранения всех курсов", e);
        }
    }

    
    public static class RepositoryInitializationException extends RuntimeException {
        public RepositoryInitializationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class RepositoryOperationException extends RuntimeException {
        public RepositoryOperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DuplicateCourseException extends RuntimeException {
        public DuplicateCourseException(String message) {
            super(message);
        }
    }
}