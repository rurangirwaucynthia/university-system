import java.util.ArrayList;
import java.util.List;

public class Instructor {
    private int id;
    private String name;
    private String email;
    private String department;
    private List<Course> courses = new ArrayList<>();

    public Instructor(int id, String name, String email, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<Course> getCourses() { return courses; }
    public void addCourse(Course course) { courses.add(course); }

    @Override
    public String toString() {
        return "Instructor [id=" + id + ", name=" + name + ", email=" + email
                + ", department=" + department + "]";
    }
}
