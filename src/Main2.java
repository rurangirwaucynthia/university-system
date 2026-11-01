
public class Main2 {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();

        Student s = new UndergraduateStudent(0, "Test User", "test@uni.rw", "Maths");
        studentDAO.insert(s);
        System.out.println("1. Student inserted");

        System.out.println("2. All students:");
        for (Student st : studentDAO.findAll()) {
            System.out.println("   " + st);
        }
        System.out.println("3. All courses:");
        courseDAO.findAll().forEach(c -> System.out.println("   " + c));

        for (Student st : studentDAO.findAll()) {
            if (st.getEmail().equals("test@uni.rw")) {
                st.setMajor("Physics");
                studentDAO.update(st);
                System.out.println("4. Student updated");
                studentDAO.delete(st.getId());
                System.out.println("5. Student deleted");
            }
        }
    }
}

