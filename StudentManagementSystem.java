import java.util.*;

public class StudentManagementSystem {

    // Maps to store students, academic members, departments, programs, and courses
    private final HashMap<Integer, People> studentMap = new HashMap<>();
    private final HashMap<Integer, People> aMemberMap = new HashMap<>();
    private final HashMap<String, Departments> departmentMap = new HashMap<>();
    private final HashMap<String, Programs> programMap = new HashMap<>();
    private final HashMap<String, Courses> courseMap = new HashMap<>();

    // Getter for academic members map
    public HashMap<Integer, People> getaMemberMap() {
        return aMemberMap;
    }

    // Getter for students map
    public HashMap<Integer, People> getStudentMap() {
        return studentMap;
    }

    // Returns sorted departments map
    public Map<String, Departments> getDepartmentMap() {
        Map<String, Departments> sortedMap = new TreeMap<>(departmentMap);
        return sortedMap;
    }

    // Returns sorted programs map
    public Map<String, Programs> getProgramMap() {
        Map<String, Programs> sortedMap = new TreeMap<>(programMap);
        return sortedMap;
    }

    // Returns sorted courses map
    public Map<String, Courses> getCourseMap() {
        Map<String, Courses> sortedMap = new TreeMap<>(courseMap);
        return sortedMap;
    }

    // FileManager instance to handle file operations
    FileManager fileManager = new FileManager();

    // Output list to store logs or messages
    List<String> output = new ArrayList<>();

    // Reads people data from a file and populates student or academic member maps
    public void peopleManager(String peopleFile){
        output.add("Reading Person Information");
        for (String[] i : fileManager.readTxt(peopleFile)) {
            try {
                int id = Integer.parseInt(i[1]);
                String name = i[2];
                String email = i[3];
                String program = i[4];
                People person;
                switch (i[0]) {
                    case "S":
                        person = new Student(id, name, email, program);
                        studentMap.put(id, person);
                        break;
                    case "F":
                        person = new AcademicMember(id, name, email, program);
                        aMemberMap.put(id, person);
                        break;
                    default:
                        throw new MyExceptions.InvalidError("Invalid Person Type");
                }
            }
            catch (MyExceptions.InvalidError e) {
                output.add(e.getMessage());
            }
        }
    }

    // Reads department, program, or course data and stores them in respective maps
    public void entityManager(String entityFile, String entityName) {
        output.add("Reading " + entityName);
        for (String[] i : fileManager.readTxt(entityFile)) {
            try {
                String code = i[0];
                String name = i[1];
                String description = i[2];
                switch (entityName) {
                    case "Departments":
                        int head = Integer.parseInt(i[3]);
                        if (aMemberMap.containsKey(head)) {
                            Departments department = new Departments(code, name, description, aMemberMap.get(head).getName());
                            departmentMap.put(name, department);
                        }
                        else {
                            Departments department = new Departments(code, name, description, "Not assigned");
                            departmentMap.put(name, department);
                            throw new MyExceptions.NonExistedError("Academic Member Not Found with ID " + head);
                        }
                        break;
                    case "Programs":
                        Programs program = new Programs(code, name, description, i[3], i[4], i[5]);
                        programMap.put(code, program);
                        break;
                    case "Courses":
                        int credits = Integer.parseInt(i[3]);
                        if (programMap.containsKey(i[5])) {
                            Courses course = new Courses(code, name, description, credits, i[4], i[5]);
                            programMap.get(i[5]).addCourse(course);
                            courseMap.put(code, course);
                        }
                        else {
                            throw new MyExceptions.NonExistedError("Program " + i[5] + " Not Found");
                        }
                        break;
                    default:
                        throw new MyExceptions.InvalidError("Invalid Academic Entity Type");
                }
            }
            catch (MyExceptions.NonExistedError e) {
                output.add(e.getMessage());
            }
            catch (MyExceptions.InvalidError e) {
                output.add(e.getMessage());
            }
        }
    }

    // Assigns students and academic members to courses
    public void assignmentManager(String assignmentFile) {
        output.add("Reading Course Assignments");
        for (String[] i : fileManager.readTxt(assignmentFile)) {
            try {
                int id = Integer.parseInt(i[1]);
                String courseName = i[2];
                switch (i[0]) {
                    case "S":
                        if (!studentMap.containsKey(id)) {
                            throw new MyExceptions.NonExistedError("Student Not Found with ID " + id);
                        } else if (!courseMap.containsKey(courseName)) {
                            throw new MyExceptions.NonExistedError("Course " + courseName + " Not Found");
                        } else {
                            studentMap.get(id).addEnrolledCourses(courseMap.get(courseName));
                            courseMap.get(courseName).addEnrolledStudent(studentMap.get(id));
                        }
                        break;
                    case "F":
                        if (!aMemberMap.containsKey(id)) {
                            throw new MyExceptions.NonExistedError("Academic Member Not Found with ID " + id);
                        } else if (!courseMap.containsKey(courseName)) {
                            throw new MyExceptions.NonExistedError("Course " + courseName + " Not Found");
                        } else {
                            courseMap.get(courseName).setInstructor(aMemberMap.get(id).getName());
                        }
                        break;
                }
            }
            catch (MyExceptions.NonExistedError e) {
                output.add(e.getMessage());
            }
        }
    }

    // Processes student grades and updates course and student information accordingly
    public void gradeManager(String gradeFile) {
        List<String> grades = Arrays.asList(
                "A1", "A2", "B1", "B2", "C1", "C2", "D1", "D2", "F3");
        output.add("Reading Grades");
        for (String[] i : fileManager.readTxt(gradeFile)) {
            int id = Integer.parseInt(i[1]);
            try {
                if (!studentMap.containsKey(id)) {
                    throw new MyExceptions.NonExistedError("Student Not Found with ID " + id);
                } else if (!courseMap.containsKey(i[2])) {
                    throw new MyExceptions.NonExistedError("Course " + i[2] + " Not Found");
                } else if (!grades.contains(i[0])) {
                        throw new MyExceptions.InvalidError("The grade " + i[0] + " is not valid");
                } else {
                    studentMap.get(id).removeEnrolledCourses(courseMap.get(courseMap.get(i[2]).getCode()), i[0]);
                    studentMap.get(id).addGrades(courseMap.get(i[2]), i[0]);
                    courseMap.get(i[2]).addGrade(i[0]);
                }
            }
            catch (MyExceptions.NonExistedError e) {
                output.add(e.getMessage());
            }
            catch (MyExceptions.InvalidError e) {
                output.add(e.getMessage());
            }
        }
    }

    // Centers the given string in a 40-character wide line
    public static String centerFormat(String text) {
        int len = text.length();
        int start = (40 - len) / 2;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < start; i++) {
            result.append(" ");
        }
        return result + text;
    }

    // Prints information from a HashMap with a title
    public void informationManager(HashMap hashMap, String type) {
        output.add("----------------------------------------");
        output.add(centerFormat(type));
        output.add("----------------------------------------");
        for (Object i : hashMap.values()) {
            output.add(i.toString());
            output.add("");
        }
        output.add("----------------------------------------\n");
    }

    // Prints information from a Map with a title
    public void informationManager(Map map, String type) {
        output.add("----------------------------------------");
        output.add(centerFormat(type));
        output.add("----------------------------------------");
        for (Object i : map.values()) {
            output.add(i.toString());
            output.add("");
        }
        output.add("----------------------------------------\n");
    }

    // Generates report for each course
    public void reportManager(Map<String, Courses> courseMap, String type) {
        output.add("----------------------------------------");
        output.add(centerFormat(type + " REPORTS"));
        for (Courses i : courseMap.values()) {
            output.add("----------------------------------------\n");
            output.add(i.toString());
            output.add(i.reportWriter());
        }
        output.add("\n----------------------------------------\n");
    }

    // Generates report for each student
    public void reportManager(HashMap<Integer, People> students, String type) {
        output.add("----------------------------------------");
        output.add(centerFormat(type + " REPORTS"));
        output.add("----------------------------------------");
        for (People i : students.values()) {
            output.add(i.toString());
            output.add(i.reportWriter());
            output.add("----------------------------------------\n");
        }
    }
}
