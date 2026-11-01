
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

    public int getId(int id) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setId(String name) {
        this.name = name;
    }

    public String getEmail(String email) {
        return email;
    }

    public void setEmail(String email) {
        this.name = name;
    }
    public String getMajor() {return major;}

    public void setMajor(String major) {
        this.major = major;
    }
    public Map<String, Double> getGrades() { return grades; }
    public void addGrade(String courseCode, double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        grades.put(courseCode, score);
    }
    public abstract double calculateGPA();
    public abstract String getStudentType();
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