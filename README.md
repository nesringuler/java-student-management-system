# Java Student Management System

## Project Overview

This project is a **Java-based desktop course tracking automation system** developed as part of the *Object-Oriented Programming* course.

The system allows academic processes such as **course management, grade entry, attendance tracking, and schedule viewing** to be managed through a single desktop interface.

The application supports **two user roles**:

* **Instructor**
* **Student**

Instructors can manage courses, enter grades, and track attendance, while students can view their grades, attendance records, and course schedules.

---

## Technologies Used

* Java
* JavaFX
* JSON
* Gson Library
* Maven
* MVC Architecture

---

## System Architecture

The project follows the **MVC (Model-View-Controller)** design pattern.

### Model

Represents the data structures of the system.

Examples:

* User
* Student
* Course
* StudentGrade
* Attendance

### View

The user interface of the system built with **JavaFX (FXML + CSS)**.

Examples:

* login-view.fxml
* dashboard-view.fxml

### Controller

Handles application logic and user interactions.

Examples:

* LoginController
* DashboardController

---

## Features

### User Authentication

* Instructor login with email and password
* Student login using student number and name

### Course Management

* Add or remove courses
* Define course information such as:

  * Course code
  * Course name
  * Day and time
  * Classroom

### Grade Management

* Enter grades for:

  * Midterm
  * Final
  * Project
  * Homework
* Automatic weighted grade calculation

### Attendance Tracking

* Weekly attendance tracking for students
* Supports **14-week academic schedule**

### Student Dashboard

Students can view:

* Their grades
* Attendance records
* Course schedule

### Instructor Dashboard

Instructors can:

* Add courses
* Enter grades
* Manage attendance

---

## Data Storage

The system uses **JSON files for data persistence**, eliminating the need for an external database.

Example data files:

* users.json
* courses.json
* attendance.json
* student_grades.json

The project uses the **Gson library** to serialize and deserialize Java objects into JSON format.

---

## Grade Calculation

Student grades are calculated using weighted averages:

* Midterm: 30%
* Final: 50%
* Project: 10%
* Homework: 10%

Example calculation:

Final Grade = (Midterm × 0.3) + (Final × 0.5) + (Project × 0.1) + (Homework × 0.1)

---

## How to Run

1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Build the Maven project
4. Run the main class:

```
HelloApplication.java
```

---

## Future Improvements

Possible improvements for the system:

* Integration with a database (MySQL / PostgreSQL)
* Exam schedule reminders
* Export student transcripts as PDF
* Cloud-based data storage

---

## Authors

* Nesrin Güler
* Ahmet Emre Özümağı
* Elif Büşra Çaylan

