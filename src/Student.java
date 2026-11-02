
import java.util.HashMap;
import java.util.Map;

public abstract class Student {

    private int id;
    private String name;
    private String email;
    private String major;
    private Map<String, Double> grades = new HashMap<>();


    public Student(int id, String name, String email, String major) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.major = major;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMajor() {
        return major;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void addGrade(String courseCode, double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        grades.put(courseCode, score);
    }


    public abstract double calculateGPA();

    public abstract String getStudentType();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return getStudentType() + " [id=" + id + ", name=" + name
                + ", email=" + email + ", major=" + major + "]";
    }
}