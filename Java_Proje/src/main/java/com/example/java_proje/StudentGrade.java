package com.example.java_proje.model;

// Öğrenci bilgilerini ve o öğrencinin aldığı notları tutan model sınıfımız.
public class StudentGrade {
    private String studentNo;
    private String firstName;
    private String lastName;
    private String courseCode; // Bu notlar hangi derse ait onu belirtiyoruz.

    // Notları sayı yerine yazı olarak tutuyoruz çünkü not girilmemişse boş kalmasını istiyoruz.
    private String midterm;
    private String finalExam;
    private String homework;
    private String project;

    // Nesne oluşturulurken öğrenci ve not bilgilerini atayan yapıcı metot.
    public StudentGrade(String studentNo, String firstName, String lastName, String courseCode, String midterm, String finalExam, String homework, String project) {
        this.studentNo = studentNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseCode = courseCode;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.homework = homework;
        this.project = project;
    }

    // Tabloda verileri göstermek ve değiştirmek için bu metotlara ihtiyacımız var.
    public String getStudentNo() { return studentNo; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCourseCode() { return courseCode; }

    public String getMidterm() { return midterm; }
    public void setMidterm(String midterm) { this.midterm = midterm; }

    public String getFinalExam() { return finalExam; }
    public void setFinalExam(String finalExam) { this.finalExam = finalExam; }

    public String getHomework() { return homework; }
    public void setHomework(String homework) { this.homework = homework; }

    public String getProject() { return project; }
    public void setProject(String project) { this.project = project; }

    // Tabloda isim ve soyisimi bitişik göstermek için kullandığımız yardımcı metot.
    public String getFullName() { return firstName + " " + lastName; }
}