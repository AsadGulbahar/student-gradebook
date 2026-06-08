# Student Gradebook

## Overview

Student Gradebook is a console-based Java application that demonstrates the use of Object-Oriented Programming (OOP) principles and Java Collection Framework classes.

The application allows instructors to manage students, record grades, track assessment queues, view course information, and generate reports.

This project was developed as part of a hands-on lab focused on learning Java collections and data structures.

---

## Features

### Student Management

* Add new students
* Remove existing students
* Search students by ID
* View all students

### Grade Management

* Add grades for students
* View grades by subject
* Calculate overall student averages
* Display the top-performing student
* Validate grade input (0–100)

### Course Management

* Store unique course codes using a HashSet
* Display students by course code
* Associate courses with teachers

### Assessment Queue

* Add students to an assessment waiting list
* Process students in First-In-First-Out (FIFO) order using a Queue

### Recent Actions

* Track application activity using a Deque (Stack behaviour)
* View recent actions in reverse chronological order

### Reporting

* Generate a summary report including:

  * Total number of students
  * Students per course
  * Average grades per subject
  * Assessment queue information
  * Most recent action

---

## Technologies Used

* Java 26
* IntelliJ IDEA
* Java Collections Framework
* Git
* GitHub

---

## Collection Classes Demonstrated

| Collection         | Purpose                   |
| ------------------ | ------------------------- |
| ArrayList          | Store students            |
| HashMap            | Store grades by subject   |
| HashSet            | Store unique course codes |
| Queue (LinkedList) | Assessment waiting list   |
| Deque (ArrayDeque) | Recent actions stack      |

---

## OOP Concepts Demonstrated

### Encapsulation

Private fields with public getter methods are used throughout the application.

### Abstraction

Methods such as `getFullName()` and `getOverallAverage()` hide implementation details.

### Classes and Objects

The project uses multiple classes including:

* Student
* Teacher
* Course
* StudentGradebook

### Constructors

Constructors are used to initialise objects with required data.

---

## Project Structure

```text
StudentGradebook
│
├── src
│   └── StudentGradebook.java
│
├── README.md
└── .gitignore
```

---

## Example Subjects

The application supports the following subjects:

* Programming
* Databases
* Testing
* DevOps

---

## Example Course Codes

* CSD101
* JAV101
* SDET202
* DEVOPS301

---

## How to Run

1. Open the project in IntelliJ IDEA.
2. Open `StudentGradebook.java`.
3. Run the `main()` method.
4. Follow the on-screen menu options.

---

## Example Menu

```text
===== Student Gradebook App =====

1. View All Students
2. Search Student
3. Add Grade
4. View Grades
5. View Course Codes
6. Add Student to Assessment Queue
7. Process Assessment Queue
8. View Recent Actions
9. Add Student
10. Remove Student
11. Display Top Performing Student
12. Display Students by Course Code
13. Generate Report
14. Exit
```

---

## Author

Asad Gulbahar

BSc (Hons) Computer Science
Queen Mary University of London
