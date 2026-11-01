import java.util.Map;

public class GraduateStudent extends Student {

    public GraduateStudent(int id, String name, String email, String major) {
        super(id, name, email, major);
    }
    @Override
    public double calculateGPA() {
        Map<String, Double> grades = getGrades();
        if (grades.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        for (double score : grades.values()) {
            if (score >= 93) total += 4.0;
            else if (score >= 85) total += 3.0;
            else if (score >= 77) total += 2.0;
            else if (score >= 70) total += 1.0;
        }
        return total / grades.size();
    }

    @Override
    public String getStudentType() {
        return "GRADUATE";
    }
}

