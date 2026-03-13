package com.example.java_proje.model;

// Dersin tüm temel bilgilerini tutan model sınıfımız.
public class Course {
    private String code;
    private String name;
    private String day;
    private String time;
    private String classroom;
    private String instructorEmail;

    // Nesne oluşturulurken bilgileri atayan yapıcı metot.
    public Course(String code, String name, String day, String time, String classroom, String instructorEmail) {
        this.code = code;
        this.name = name;
        this.day = day;
        this.time = time;
        this.classroom = classroom;
        this.instructorEmail = instructorEmail;
    }

    // Verilere ulaşmak için kullandığımız getter metotları.
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDay() { return day; }
    public String getTime() { return time; }
    public String getClassroom() { return classroom; }
    public String getInstructorEmail() { return instructorEmail; }
}