import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static StudentDAO studentDAO = new StudentDAO();
    private static CourseDAO courseDAO = new CourseDAO();
    private static EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public static void main(String[] args) {
        System.out.println("=== University Management Console ===");
        boolean running = true;

        while (running) {
            printMenu();
            try {
                int choice = readInt("Choose an option");

                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: listStudents(); break;
                    case 3: removeStudent(); break;
                    case 4: addCourse(); break;
                    case 5: listCourses(); break;
                    case 6: removeCourse(); break;
                    case 7: enrollStudent(); break;
                    case 8: updateGrade(); break;
                    case 9: viewStudentsWithCourses(); break;
                    case 10: showGpa(); break;
                    case 0: running = false; break;
                    default: System.out.println("Unknown option. Try again.");
                }   // end of switch

            } catch (InvalidInputException e) {
                System.out.println("Invalid input: " + e.getMessage());
            } catch (DataAccessException e) {
                System.out.println("Database problem: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }   // end of while

        System.out.println("Goodbye!");
    }   // end of main

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add student");
        System.out.println("2. List students");
        System.out.println("3. Remove student");
        System.out.println("4. Add course");
        System.out.println("5. List courses");
        System.out.println("6. Remove course");
        System.out.println("7. Enroll student in course");
        System.out.println("8. Update grade");
        System.out.println("9. View students with their courses (JOIN)");
        System.out.println("10. Show student GPA");
        System.out.println("0. Exit");
    }

    private static void addStudent() {
        String name = Validator.notEmpty(readText("Name"), "Name");
        String email = Validator.validEmail(readText("Email"));
        String major = Validator.notEmpty(readText("Major"), "Major");
        String type = readText("Type (U = undergraduate, G = graduate)").trim().toUpperCase();

        Student student;
        if (type.equals("U")) {
            student = new UndergraduateStudent(0, name, email, major);
        } else if (type.equals("G")) {
            student = new GraduateStudent(0, name, email, major);
        } else {
            throw new InvalidInputException("Type must be U or G.");
        }
        studentDAO.insert(student);
        System.out.println("Student added.");
    }

    private static void listStudents() {
        List<Student> students = studentDAO.findAll();
        if (students.isEmpty()) {
            System.out.println("No students yet.");
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void removeStudent() {
        int id = readInt("Student id to remove");
        studentDAO.delete(id);
        System.out.println("Student removed.");
    }

    private static void addCourse() {
        String code = Validator.notEmpty(readText("Course code (e.g. CS101)"), "Code").toUpperCase();
        String title = Validator.notEmpty(readText("Title"), "Title");
        int credits = readInt("Credits (1-10)");
        Validator.inRange(credits, 1, 10, "Credits");
        String instructorName = readText("Instructor name (leave empty if none)").trim();

        Course course = new Course(0, code, title, credits);
        if (!instructorName.isEmpty()) {
            course.setInstructor(new Instructor(0, instructorName, "", ""));
        }
        courseDAO.insert(course);
        System.out.println("Course added.");
    }

    private static void listCourses() {
        List<Course> courses = courseDAO.findAll();
        if (courses.isEmpty()) {
            System.out.println("No courses yet.");
        }
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private static void removeCourse() {
        int id = readInt("Course id to remove");
        courseDAO.delete(id);
        System.out.println("Course removed.");
    }

    private static void enrollStudent() {
        int studentId = readInt("Student id");
        int courseId = readInt("Course id");
        enrollmentDAO.enroll(studentId, courseId);
        System.out.println("Student enrolled.");
    }

    private static void updateGrade() {
        int studentId = readInt("Student id");
        int courseId = readInt("Course id");
        double grade = readDouble("Grade (0-100)");
        Validator.inRange(grade, 0, 100, "Grade");
        enrollmentDAO.updateGrade(studentId, courseId, grade);
        System.out.println("Grade updated.");
    }

    private static void viewStudentsWithCourses() {
        List<String[]> rows = enrollmentDAO.getStudentsWithCourses();
        System.out.printf("%-4s %-20s %-8s %-25s %s%n", "ID", "STUDENT", "COURSE", "TITLE", "GRADE");
        for (String[] r : rows) {
            System.out.printf("%-4s %-20s %-8s %-25s %s%n", r[0], r[1], r[2], r[3], r[4]);
        }
    }

    private static void showGpa() {
        int id = readInt("Student id");
        Student student = studentDAO.findById(id);
        if (student == null) {
            throw new InvalidInputException("No student with id " + id);
        }
        Map<String, Double> grades = enrollmentDAO.getGradesForStudent(id);
        for (String code : grades.keySet()) {
            student.addGrade(code, grades.get(code));
        }
        System.out.println(student.getName() + " (" + student.getStudentType() + ") GPA = "
                + student.calculateGPA());
    }

    private static String readText(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    private static int readInt(String message) {
        String text = readText(message).trim();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + text + "' is not a whole number.");
        }
    }

    private static double readDouble(String message) {
        String text = readText(message).trim();
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + text + "' is not a number.");
        }
    }
}