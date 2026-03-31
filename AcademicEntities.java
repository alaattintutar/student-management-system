import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

// Abstract class for common academic entity attributes
public interface AcademicEntities {
    // Getter method signatures
    String getCode();
    String getName();
    String getDescription();
}

abstract class AcademicEntity implements AcademicEntities {

    private String code;        // Unique code of the entity
    private String name;        // Name of the entity
    private String description; // Description (can also refer to department in some contexts)

    public AcademicEntity(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    // Getter methods
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getDescription() {
        return description;
    }
}

// Class representing a department
class Departments extends AcademicEntity {
    private String head;                           // Head of department
    private ArrayList<String> programList = new ArrayList<>(); // List of program codes under the department

    public Departments(String code, String name, String description, String head) {
        super(code, name, description);
        this.head = head;
    }

    public ArrayList<String> getProgramList() {
        return programList;
    }

    // Adds a program to the department's program list
    public void addProgram(Programs p) {
        programList.add(p.getCode());
    }

    @Override
    public String toString() {
        StudentManagementSystem sms = new StudentManagementSystem(); // Potential use for system context
        return "Department Code: " + getCode() + "\nName: " + getName() +
                "\nHead: " + head;
    }
}

// Class representing an academic program
class Programs extends AcademicEntity {
    private String department;                 // Department code to which the program belongs
    private String degreeLevel;               // Degree level (e.g., Bachelor's, Master's)
    private String totalCredits;              // Total credits required to complete the program
    private ArrayList<String> courseList = new ArrayList<>(); // List of course codes

    public Programs(String code, String name, String description,
                    String department, String degreeLevel, String totalCredits) {
        super(code, name, description);
        this.degreeLevel = degreeLevel;
        this.department = department;
        this.totalCredits = totalCredits;
    }

    public String getDepartment() {
        return department;
    }

    // Adds a course to the program
    public void addCourse(Courses course){
        courseList.add(course.getCode());
    }

    public ArrayList<String> getCourseList() {
        return courseList;
    }

    @Override
    public String toString() {
        return  "Program Code: " + getCode() + "\nName: " + getName() +
                "\nDepartment: " + getDepartment() + "\nDegree Level: " + degreeLevel +
                "\nRequired Credits: " + totalCredits + "\nCourses: "  +
                "{" + String.join(",", getCourseList()) + "}";
    }
}

// Class representing a course
class Courses extends AcademicEntity {
    private int credits;                          // Number of credits for the course
    private String semester;                      // Semester in which the course is offered
    private String programCode;                   // Program code the course belongs to
    private String instructor;                    // Instructor name
    private TreeMap<Integer, People> enrolledStudents = new TreeMap<>(); // Enrolled students
    private ArrayList<String> grades = new ArrayList<>();                // List of grades
    private TreeMap<String, Integer> gradeDist = new TreeMap<>();        // Grade distribution

    public Courses(String code, String name, String description,
                   int credits, String semester, String programCode) {
        super(code, name, description);
        this.credits = credits;
        this.semester = semester;
        this.programCode = programCode;
    }

    // Returns instructor's name or default message
    public String getInstructor() {
        if (instructor == null) {
            return "Not assigned";
        }
        return instructor;
    }

    public int getCredits() {
        return credits;
    }

    // Adds a student to the enrolled student list
    public void addEnrolledStudent(People student) {
        enrolledStudents.put(student.getId(), student);
    }

    // Assigns an instructor
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    // Adds a grade to the list
    public void addGrade(String grade) {
        grades.add(grade);
    }

    // Creates grade distribution and calculates average
    public String gradeDistribution() {
        for (String grade : grades) {
            if (gradeDist.containsKey(grade)) {
                gradeDist.put(grade, gradeDist.get(grade) + 1);
            }
            else {
                gradeDist.put(grade, 1);
            }
        }
        return gradesWriter() + gradeCalculator();
    }

    // Calculates average grade based on GPA scale
    public String gradeCalculator() {
        double totalGrade = 0;
        double avgGrade;
        for (String grade : grades) {
            switch (grade) {
                case "A1":
                    totalGrade += 4;
                    break;
                case "A2":
                    totalGrade += 3.5;
                    break;
                case "B1":
                    totalGrade += 3.0;
                    break;
                case "B2":
                    totalGrade += 2.5;
                    break;
                case "C1":
                    totalGrade += 2.0;
                    break;
                case "C2":
                    totalGrade += 1.5;
                    break;
                case "D1":
                    totalGrade += 1.0;
                    break;
                case "D2":
                    totalGrade += 0.5;
                    break;
                case "F3":
                    totalGrade += 0.0;
                    break;
            }
        }
        if (totalGrade == 0) {
            return "Average Grade: 0.00\n";
        }
        avgGrade = (totalGrade / grades.size());

        return "Average Grade: " + String.format("%.2f", avgGrade) + "\n";
    }

    // Generates course report including instructor, students, and grade stats
    public String reportWriter(){
        return "\nInstructor: " + getInstructor() + studentWriter() + gradeDistribution();
    }

    // Returns a list of enrolled students
    public String studentWriter(){
        StringBuilder students = new StringBuilder();
        for(People p : enrolledStudents.values()){
            students.append("- " + p.getName() + " (ID: " + p.getId() + ")\n");
        }
        return "\n\nEnrolled Students:\n" + students;
    }

    // Returns a formatted string showing grade distribution
    public String gradesWriter() {
        StringBuilder grades = new StringBuilder();
        for (HashMap.Entry<String, Integer> entry : gradeDist.entrySet()) {
            grades.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
        return  "\nGrade Distribution:\n" + grades + "\n";
    }

    @Override
    public String toString() {
        return  "Course Code: " + getCode() + "\nName: " + getName() +
                "\nDepartment: " + getDescription() + "\nCredits: " + credits +
                "\nSemester: " + semester;
    }
}
