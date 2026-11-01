public class Validator {

    public static String notEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    public static String validEmail(String email) {
        String e = notEmpty(email, "Email");
        if (!e.contains("@") || !e.contains(".") || e.startsWith("@")) {
            throw new InvalidInputException("'" + e + "' is not a valid email.");
        }
        return e;
    }

    public static void inRange(double value, double min, double max, String fieldName) {
        if (value < min || value > max) {
            throw new InvalidInputException(fieldName + " must be between " + min + " and " + max + ".");
        }
    }
}
//work
