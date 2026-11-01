import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main1 {
    public static void main(String[] args) {
        Student alice = new UndergraduateStudent(1, "Alice", "alice@uni.rw", "Computer Science");
        Student bob = new GraduateStudent(2, "Bob", "bob@uni.rw", "Software Engineering");
        Instructor kamanzi = new Instructor(1, "Dr. Kamanzi", "kamanzi@uni.rw", "Computing");
        Course ds = new Course(1, "CS201", "Data Structures", 4);

        List<Student> allStudents = new ArrayList<>();
        allStudents.add(alice);
        allStudents.add(bob);

        Map<Instructor, List<Course>> assignments = new HashMap<>();
        assignments.put(kamanzi, new ArrayList<>());
        assignments.get(kamanzi).add(ds);
        ds.setInstructor(kamanzi);

        System.out.println("Enroll Alice: " + ds.enrollStudent(alice));          // true
        System.out.println("Enroll Alice again: " + ds.enrollStudent(alice));    // false
        System.out.println("Enroll Bob: " + ds.enrollStudent(bob));              // true

        alice.addGrade("CS201", 91);
        bob.addGrade("CS201", 91);
        for (Student s : allStudents) {
            System.out.println(s + " -> GPA = " + s.calculateGPA());
        }

        System.out.println(ds);
        System.out.println("Number of students in " + ds.getCode() + ": " + ds.getStudents().size());
        for (Instructor i : assignments.keySet()) {
            System.out.println(i.getName() + " teaches: " + assignments.get(i));
        }
    }

    }
