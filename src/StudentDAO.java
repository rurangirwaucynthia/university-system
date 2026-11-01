import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE: add a student
    public void insert(Student s) {
        String sql = "INSERT INTO students(name, email, major, student_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getMajor());
            ps.setString(4, s.getStudentType());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw DataAccessException.from("Adding student", e);
        }
    }

    // READ: find one student (returns null if not found)
    public Student findById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowToStudent(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw DataAccessException.from("Finding student", e);
        }
    }

    // READ: list all students
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(rowToStudent(rs));
            }

        } catch (SQLException e) {
            throw DataAccessException.from("Listing students", e);
        }
        return students;
    }

    // UPDATE: change name, email and major
    public void update(Student s) {
        String sql = "UPDATE students SET name = ?, email = ?, major = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getMajor());
            ps.setInt(4, s.getId());

            int rowsChanged = ps.executeUpdate();
            if (rowsChanged == 0) {
                throw new DataAccessException("No student with id " + s.getId(), null);
            }

        } catch (SQLException e) {
            throw DataAccessException.from("Updating student", e);
        }
    }

    // DELETE: remove a student
    public void delete(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DataAccessException("No student with id " + id, null);
            }

        } catch (SQLException e) {
            throw DataAccessException.from("Deleting student", e);
        }
    }

    // Helper: turns one database row into a Student object
    private Student rowToStudent(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String major = rs.getString("major");
        String type = rs.getString("student_type");

        if (type.equals("GRADUATE")) {
            return new GraduateStudent(id, name, email, major);
        } else {
            return new UndergraduateStudent(id, name, email, major);
        }
    }
}