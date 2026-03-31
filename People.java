import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

// Abstract class representing a person (either student or academic member)
abstract class People {
    private final int id;
    private final String name;
    private final String email;

    public People(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Abstract methods to be implemented by subclasses
    public abstract void addEnrolledCourses(Courses enrolledCourses);
    public abstract void removeEnrolledCourses(Courses enrolledCourses, String grade);
    public abstract void addGrades(Courses course, String grades);
    public abstract String reportWriter();
}

// Class representing a student
class Student extends People {
    private final String major;
    private TreeMap<String, Courses> enrolledCourses  = new TreeMap<>();
    private TreeSet<String> completedCourses  = new TreeSet<>();
    private HashMap<Courses, String> gradesMap= new HashMap<>();
    private int completedCredits;
    private double gpa;

    public Student(int id, String name, String email, String major) {
        super(id, name, email);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    // Adds a course to the enrolled list
    @Override
    public void addEnrolledCourses(Courses enrolledCourses) {
        this.enrolledCourses.put(enrolledCourses.getCode(), enrolledCourses);
    }

    // Removes a course from enrolled list and marks it as completed
    @Override
    public void removeEnrolledCourses(Courses courses, String grade) {
        this.enrolledCourses.remove(courses.getCode());
        this.completedCredits += courses.getCredits();
        this.completedCourses.add("- " + courses.getName() + " (" + courses.getCode() + "): " + grade + "\n");
    }

    // Returns a formatted list of enrolled courses
    public StringBuilder getEnrolledCourses() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nEnrolled Courses:\n");
        for (String code : this.enrolledCourses.keySet()) {
            Courses courses = this.enrolledCourses.get(code);
            sb.append("- " + courses.getName() + " (" + courses.getCode() + ")\n");
        }
        return sb;
    }

    // Returns a formatted list of completed courses
    public StringBuilder getCompletedCourses() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Completed Courses:\n");
        for (String courses : completedCourses) {
            stringBuilder.append(courses);
        }
        return stringBuilder;
    }

    // Adds a grade for a specific course
    @Override
    public void addGrades(Courses course, String grades) {
        gradesMap.put(course, grades);
    }

    // Calculates GPA based on the grades and course credits
    public String gradeCalculator() {
        double totalGrade = 0;
        for (Courses course : gradesMap.keySet()) {
            switch (gradesMap.get(course)) {
                case "A1":
                    totalGrade += (4 * course.getCredits());
                    break;
                case "A2":
                    totalGrade += (3.5 * course.getCredits());
                    break;
                case "B1":
                    totalGrade += (3.0  * course.getCredits());
                    break;
                case "B2":
                    totalGrade += (2.5  * course.getCredits());
                    break;
                case "C1":
                    totalGrade += (2.0  * course.getCredits());
                    break;
                case "C2":
                    totalGrade += (1.5 * course.getCredits());
                    break;
                case "D1":
                    totalGrade += (1.0 * course.getCredits());
                    break;
                case "D2":
                    totalGrade += (0.5 * course.getCredits());
                    break;
                case "F3":
                    totalGrade += 0.0;
                    break;
            }
        }
        if (totalGrade == 0) {
            return "\nGPA: 0.00";
        }
        gpa = (totalGrade / completedCredits);

        return "\nGPA: " + String.format("%.2f", gpa);
    }

    // Generates the report including enrolled courses, completed courses and GPA
    @Override
    public String reportWriter() {
        return "\n" + getEnrolledCourses().toString() + "\n" + getCompletedCourses().toString() + gradeCalculator();
    }

    // String representation of a student
    @Override
    public String toString() {
        return "Student ID: " + getId() + "\nName: " + getName() +
                "\nEmail: " + getEmail() + "\nMajor: " + getMajor() + "\nStatus: Active";
    }
}

// Class representing an academic member
class AcademicMember extends People {
    private final String department;

    public AcademicMember(int id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }

    // Academic members do not enroll in courses
    @Override
    public void  addEnrolledCourses(Courses enrolledCourses) {
    }

    // Academic members do not remove courses
    @Override
    public void removeEnrolledCourses(Courses enrolledCourses, String grade) {
    }

    // Academic members do not receive grades
    @Override
    public void addGrades(Courses course, String grades) {}

    // Report for academic member
    @Override
    public String reportWriter() {
        return "Academic Member ID: " + getId();
    }

    public String getDepartment() {
        return department;
    }

    // String representation of an academic member
    @Override
    public String toString() {
        return "Faculty ID: " + getId() + "\nName: " + getName() +
                "\nEmail: " + getEmail() + "\nDepartment: " + getDepartment();
    }

}
