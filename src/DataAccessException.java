import java.sql.SQLException;

public class DataAccessException extends RuntimeException {

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public static DataAccessException from(String action, SQLException e) {
        String code = e.getSQLState();

        if ("23505".equals(code)) {
            return new DataAccessException(action + " failed: this value already exists.", e);
        }
        if ("23503".equals(code)) {
            return new DataAccessException(action + " failed: the student or course does not exist.", e);
        }
        return new DataAccessException(action + " failed: " + e.getMessage(), e);
    }
}