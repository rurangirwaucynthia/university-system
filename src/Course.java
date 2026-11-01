import java.util.HashSet;
import java.util.Set;

public class Course {
    private int id;
    private String code;
    private String title;
    private int credits;
    private Instructor instructor;
    private Set<Student> students = new HashSet<>();
    public Course(int id, String code, String title, int credits) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.credits = credits;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public Set<Student> getStudents() { return students; }


    public boolean enrollStudent(Student student) {
        return students.add(student);
    }

    @Override
    public String toString() {
        String teacher = (instructor == null) ? "Not assigned" : instructor.getName();
        return "Course [id=" + id + ", code=" + code + ", title=" + title
                + ", credits=" + credits + ", instructor=" + teacher + "]";
    }
}
