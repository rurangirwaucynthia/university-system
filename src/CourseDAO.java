import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {


    public void insert(Course course) {
        String sql = "INSERT INTO courses(code, title, credits, instructor_name) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getCode());
            ps.setString(2, course.getTitle());
            ps.setInt(3, course.getCredits());
            if (course.getInstructor() == null) {
                ps.setString(4, null);
            } else {
                ps.setString(4, course.getInstructor().getName());
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            throw DataAccessException.from("Adding course", e);
        }
    }

    // READ one (returns null if not found)
    public Course findById(int id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowToCourse(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw DataAccessException.from("Finding course", e);
        }
    }

    // all
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY code";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                courses.add(rowToCourse(rs));
            }
        } catch (SQLException e) {
            throw DataAccessException.from("Listing courses", e);
        }
        return courses;
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DataAccessException("No course with id " + id, null);
            }
        } catch (SQLException e) {
            throw DataAccessException.from("Deleting course", e);
        }
    }

    private Course rowToCourse(ResultSet rs) throws SQLException {
        Course course = new Course(rs.getInt("id"), rs.getString("code"),
                rs.getString("title"), rs.getInt("credits"));

        String instructorName = rs.getString("instructor_name");
        if (instructorName != null) {
            course.setInstructor(new Instructor(0, instructorName, "", ""));
        }
        return course;
    }
}