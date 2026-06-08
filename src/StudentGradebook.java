import java.util.*;

class Student {
    // Encapsulation used via private fields
    private String studentId;
    private String firstName;
    private String lastName;
    private String courseCode;
    // HashMap stores grades by subject (key-value structure)
    private HashMap<String, ArrayList<Integer>> grades;

    // Instance fields/methods belong to a specific object e.g. Student, while static fields/methods belong to the class itself e.g. StudentGradebook

    // Constructor
    public Student(String studentId, String firstName, String lastName, String courseCode) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseCode = courseCode;
        this.grades = new HashMap<>();
    }

    // Encapsulation: controlled access through public getter methods
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public HashMap<String, ArrayList<Integer>> getGrades() { return grades; }

    public void addGrade(String subject, int grade) {
        grades.putIfAbsent(subject, new ArrayList<>());
        grades.get(subject).add(grade);
    }

    public double getOverallAverage() {
        if (grades.isEmpty()) return 0.0;
        double total = 0;
        int count = 0;
        for (ArrayList<Integer> subjectGrades : grades.values()) {
            for (int g : subjectGrades) {
                total += g;
                count++;
            }
        }
        return count == 0 ? 0.0 : total / count;
    }
}

public class StudentGradebook {

    // Ordered list of students
    static ArrayList<Student> students = new ArrayList<>();
    // Unique course codes, a HashSet automatically handles duplicates by removing them
    static HashSet<String> courseCodes = new HashSet<>();
    // A Queue follows FIFO — the first student added is the first to be processed.
    // This mirrors a real waiting list. add() joins the back of the queue, poll() removes from the front:
    static Queue<Student> assessmentQueue = new LinkedList<>();
    // recent actions stack - a Deque allows us to push new actions onto the top and pop them off in reverse order, perfect for tracking recent activity:
    static Deque<String> recentActions = new ArrayDeque<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        boolean running = true;

        // Application loop
        while (running) {
            System.out.println("\n===== Student Gradebook App =====");
            System.out.println("1.  View All Students");
            System.out.println("2.  Search Student");
            System.out.println("3.  Add Grade");
            System.out.println("4.  View Grades");
            System.out.println("5.  View Course Codes");
            System.out.println("6.  Add Student to Assessment Queue");
            System.out.println("7.  Process Assessment Queue");
            System.out.println("8.  View Recent Actions");
            System.out.println("9.  Add Student");
            System.out.println("10. Remove Student");
            System.out.println("11. Display Top Performing Student");
            System.out.println("12. Display Students by Course Code");
            System.out.println("13. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":  viewAllStudents();           break;
                case "2":  searchStudent();             break;
                case "3":  addGrade();                  break;
                case "4":  viewGrades();                break;
                case "5":  viewCourseCodes();           break;
                case "6":  addToAssessmentQueue();      break;
                case "7":  processAssessmentQueue();    break;
                case "8":  viewRecentActions();         break;
                case "9":  addStudent();                break;  // Exercise 1
                case "10": removeStudent();             break;  // Exercise 2
                case "11": displayTopStudent();         break;  // Exercise 4
                case "12": displayStudentsByCourse();   break;  // Exercise 5
                case "13": running = false;             break;
                default:   System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Goodbye!");
    }

    // ─── Seed Data ────────────────────────────────────────────────────────────

    static void seedData() {
        students.add(new Student("ST001", "Sarah",   "Ahmed",  "CSD101"));
        students.add(new Student("ST002", "James",   "Brown",  "JAV101"));
        students.add(new Student("ST003", "Priya",   "Patel",  "SDET202"));
        students.add(new Student("ST004", "Michael", "Smith",  "DEVOPS301"));
        students.add(new Student("ST005", "Aisha",   "Khan",   "CSD101"));

        for (Student s : students) {
            courseCodes.add(s.getCourseCode());
        }

        recentActions.push("Seeded initial student data");
    }

    // ─── Core Features ────────────────────────────────────────────────────────

    static void viewAllStudents() {
        students.sort(Comparator.comparing(Student::getLastName));
        System.out.println("\nAll Students:");
        for (Student s : students) {
            System.out.println(s.getStudentId() + " - " + s.getFullName() + " - " + s.getCourseCode());
        }
    }

    static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }

    static void searchStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student s = findStudentById(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println(s.getStudentId() + " - " + s.getFullName() + " - " + s.getCourseCode());
    }

    static void addGrade() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student s = findStudentById(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();

        // Exercise 3: Validate grade input (0–100)
        int grade = -1;
        while (grade < 0 || grade > 100) {
            System.out.print("Enter grade (0–100): ");
            // Exception handling
            try {
                grade = Integer.parseInt(scanner.nextLine());
                if (grade < 0 || grade > 100) {
                    System.out.println("Grade must be between 0 and 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        s.addGrade(subject, grade);
        recentActions.push("Added grade " + grade + " for " + s.getStudentId() + " in " + subject);
        System.out.println("Grade added successfully.");
    }

    static void viewGrades() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student s = findStudentById(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        HashMap<String, ArrayList<Integer>> grades = s.getGrades();
        if (grades.isEmpty()) {
            System.out.println("No grades found for " + s.getFullName() + ".");
            return;
        }

        System.out.println("\nGrades for " + s.getFullName() + ":");
        for (Map.Entry<String, ArrayList<Integer>> entry : grades.entrySet()) {
            double total = 0;
            for (int g : entry.getValue()) total += g;
            double avg = total / entry.getValue().size();
            System.out.printf("  %-15s %s  |  Average: %.2f%n",
                    entry.getKey() + ":", entry.getValue(), avg);
        }
        System.out.printf("  Overall average: %.2f%n", s.getOverallAverage());
    }

    static void viewCourseCodes() {
        System.out.println("\nUnique Course Codes:");
        for (String code : courseCodes) {
            System.out.println("  " + code);
        }
    }

    static void addToAssessmentQueue() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student s = findStudentById(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        assessmentQueue.add(s);
        recentActions.push("Added " + s.getStudentId() + " to assessment queue");
        System.out.println(s.getFullName() + " added to assessment queue.");
    }

    static void processAssessmentQueue() {
        if (assessmentQueue.isEmpty()) {
            System.out.println("Assessment queue is empty.");
            return;
        }
        Student s = assessmentQueue.poll();
        recentActions.push("Processed assessment for " + s.getStudentId());
        System.out.println("Processing assessment for: " + s.getFullName());

        if (!assessmentQueue.isEmpty()) {
            System.out.println("Next in queue: " + ((LinkedList<Student>) assessmentQueue).peek().getFullName());
        } else {
            System.out.println("Queue is now empty.");
        }
    }

    static void viewRecentActions() {
        System.out.println("\nRecent Actions (most recent first):");
        int i = 1;
        for (String action : recentActions) {
            System.out.println("  " + i++ + ". " + action);
        }
    }

    // ─── Exercise 1: Add Student ──────────────────────────────────────────────

    static void addStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();

        if (findStudentById(id) != null) {
            System.out.println("A student with that ID already exists.");
            return;
        }

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();

        Student newStudent = new Student(id, firstName, lastName, courseCode);
        students.add(newStudent);
        courseCodes.add(courseCode);
        recentActions.push("Added student " + id);
        System.out.println("Student " + newStudent.getFullName() + " added successfully.");
    }

    // ─── Exercise 2: Remove Student ───────────────────────────────────────────

    static void removeStudent() {
        System.out.print("Enter student ID to remove: ");
        String id = scanner.nextLine();
        Student s = findStudentById(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        students.remove(s);
        recentActions.push("Removed student " + id);
        System.out.println(s.getFullName() + " removed successfully.");
    }

    // ─── Exercise 4: Top Performing Student ──────────────────────────────────

    static void displayTopStudent() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        Student top = null;
        double topAvg = -1;

        for (Student s : students) {
            double avg = s.getOverallAverage();
            if (avg > topAvg) {
                topAvg = avg;
                top = s;
            }
        }

        if (top == null || topAvg == 0.0) {
            System.out.println("No grade data available yet.");
            return;
        }

        System.out.printf("%nTop Performing Student:%n  %s - %s  |  Overall Average: %.2f%n",
                top.getStudentId(), top.getFullName(), topAvg);
    }

    // ─── Exercise 5: Students by Course Code ─────────────────────────────────

    static void displayStudentsByCourse() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine().trim().toUpperCase();

        System.out.println("\nStudents on " + code + ":");
        boolean found = false;
        for (Student s : students) {
            if (s.getCourseCode().equalsIgnoreCase(code)) {
                System.out.println("  " + s.getStudentId() + " - " + s.getFullName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("  No students found for course code: " + code);
        }
    }
}

/**
 * ============================================================
 * Concept               | Implementation
 * ============================================================
 * Encapsulation         | private fields + getters in Student
 * Abstraction           | getFullName(), getOverallAverage()
 * Classes & Objects     | Student class, new Student(...)
 * ArrayList             | Student list - ordered, iterable
 * HashMap               | Grades by subject - key-value lookup
 * HashSet               | Course codes - no duplicates
 * Queue (FIFO)          | Assessment waiting list
 * Deque / Stack (LIFO)  | Recent actions log
 * Exception Handling    | Grade input validation
 * Null Checking         | Guard clauses in every search
 * Static vs Instance    | App-level state vs student-level data
 * ============================================================
 */