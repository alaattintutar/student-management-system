public class Main {
    public static void main(String[] args) {
        // Reading file paths from command-line arguments
        String personFile  = args[0];
        String departmentFile  = args[1];
        String programFile  = args[2];
        String courseFile  = args[3];
        String assignmentFile  = args[4];
        String gradesFile  = args[5];
        String outputFile  = args[6];

        // Initialize file manager to handle output writing
        FileManager fileManager = new FileManager();

        // Create main system manager for handling data and operations
        StudentManagementSystem manager = new StudentManagementSystem();

        // Load people data (students and academic members)
        manager.peopleManager(personFile);

        // Load department entities
        manager.entityManager(departmentFile, "Departments");

        // Load program entities
        manager.entityManager(programFile, "Programs");

        // Load course entities
        manager.entityManager(courseFile, "Courses");

        // Process assignments
        manager.assignmentManager(assignmentFile);

        // Process grades
        manager.gradeManager(gradesFile);

        // Write information about academic members
        manager.informationManager(manager.getaMemberMap(), "ACADEMIC MEMBERS");

        // Write information about students
        manager.informationManager(manager.getStudentMap(), "STUDENTS");

        // Write information about departments
        manager.informationManager(manager.getDepartmentMap(), "DEPARTMENTS");

        // Write information about programs
        manager.informationManager(manager.getProgramMap(), "PROGRAMS");

        // Write information about courses
        manager.informationManager(manager.getCourseMap(), "COURSES");

        // Generate course reports
        manager.reportManager(manager.getCourseMap(),  "COURSE");

        // Generate student reports
        manager.reportManager(manager.getStudentMap(), "STUDENT");

        // Write all gathered output to the specified output file
        fileManager.writeToFile(outputFile, manager.output);
    }
}
