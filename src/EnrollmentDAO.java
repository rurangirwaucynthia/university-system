import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnrollmentDAO {

    // Enroll a student in a course
    public void enroll(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.executeUpdate();

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new DataAccessException("This student is already enrolled in this course.", e);
            }
            throw DataAccessException.from("Enrolling", e);
        }
    }

    // Update the grade of an enrollment
    public void updateGrade(int studentId, int courseId, double grade) {
        String sql = "UPDATE enrollments SET grade = ? WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, grade);
            ps.setInt(2, studentId);
            ps.setInt(3, courseId);
            int rowsChanged = ps.executeUpdate();
            if (rowsChanged == 0) {
                throw new DataAccessException("That student is not enrolled in that course.", null);
            }
        } catch (SQLException e) {
            throw DataAccessException.from("Updating grade", e);
        }
    }

    // Grades of one student: course code -> grade (used for GPA)
    public Map<String, Double> getGradesForStudent(int studentId) {
        Map<String, Double> grades = new LinkedHashMap<>();
        String sql = "SELECT c.code, e.grade FROM enrollments e "
                + "JOIN courses c ON c.id = e.course_id "
                + "WHERE e.student_id = ? AND e.grade IS NOT NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    grades.put(rs.getString("code"), rs.getDouble("grade"));
                }
            }
        } catch (SQLException e) {
            throw DataAccessException.from("Loading grades", e);
        }
        return grades;
    }

    // JOIN query: each row = {id, student name, course code, course title, grade}
    public List<String[]> getStudentsWithCourses() {
        List<String[]> rows = new ArrayList<>();
        String sql = "SELECT s.id, s.name, c.code, c.title, e.grade "
                + "FROM students s "
                + "LEFT JOIN enrollments e ON e.student_id = s.id "
                + "LEFT JOIN courses c ON c.id = e.course_id "
                + "ORDER BY s.name, c.code";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String code = rs.getString("code");
                String title = rs.getString("title");
                String grade = rs.getString("grade");
                rows.add(new String[] {
                        String.valueOf(rs.getInt("id")),
                        rs.getString("name"),
                        code == null ? "-" : code,
                        title == null ? "(no courses)" : title,
                        grade == null ? "-" : grade
                });
            }
        } catch (SQLException e) {
            throw DataAccessException.from("Listing enrollments", e);
        }
        return rows;
    }
}
//end