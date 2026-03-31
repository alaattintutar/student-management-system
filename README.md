<div align="center">
  
# 🎓 Student Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/OOP-Principles-blue?style=for-the-badge)
![Error Handling](https://img.shields.io/badge/Custom-Exceptions-red?style=for-the-badge)

*A robust, object-oriented university data management application built in Java.*

</div>

## 📌 Overview
The **Student Management System** is a high-performance backend simulation designed to handle complex relationships between academic entities. It dynamically manages thousands of students, faculty members, courses, and departments using `HashMap` indexing for **O(1)** retrieval speeds. The software is strictly built on modern **Object-Oriented Programming (OOP)** principles such as Abstraction, Encapsulation, and Polymorphism.

## ✨ Core Features
- 👥 **Advanced Identity Management:** Seamlessly scales between `Student` and `AcademicMember` roles via inheritance.
- 🏛️ **Academic Entities:** Registers, assigns, and maps `Departments`, `Programs`, and `Courses`. 
- 💯 **Sophisticated Grading:** Processes grades natively (e.g., A1, B2, F3) and allocates course credits smoothly.
- 🛡️ **Custom Error Handling:** Implements tailored exceptions (`MyExceptions.java`) like `InvalidError` and `NonExistedError` to prevent runtime crashes during faulty file I/O operations.
- 📝 **Automated Reporting:** Generates clean, centered, and structurally formatted ASCII reports for courses and students.

## ⚙️ Technical Architecture
- **Language:** Java 8+
- **Data Structures:** `HashMap` (for rapid ID matching), `TreeMap` (for automated alphabetical sorting of academic units).
- **Design Pattern:** Modular `FileManager` decoupling file I/O operations from core business logic (`StudentManagementSystem.java`).

## 🚀 Getting Started
Run the simulation by providing the data `.txt` files in the exact order:
```bash
# Compile the project
javac *.java

# Execute with arguments
java Main <people.txt> <departments.txt> <programs.txt> <courses.txt> <assignments.txt> <grades.txt> <output.txt>
```

> **Note:** The `output.txt` will automatically generate beautifully formatted system reports!
