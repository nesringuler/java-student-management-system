package com.example.java_proje.model;

public class Attendance {

    // Yoklama verilerini (hangi öğrenci, hangi ders, hangi hafta) tutacak olan model sınıfımız.
    private String courseCode;
    private String studentNo;
    private int weekNumber; // Hangi hafta olduğunu sayı olarak tutuyoruz (Örn: 1, 2... 14).
    private boolean isPresent; // Geldi mi gelmedi mi bilgisini tutuyoruz (true ise Var, false ise Yok).

    // Nesne oluşturulurken gerekli tüm yoklama bilgilerini ilk başta set eden kurucu metodumuz.
    public Attendance(String courseCode, String studentNo, int weekNumber, boolean isPresent) {
        this.courseCode = courseCode;
        this.studentNo = studentNo;
        this.weekNumber = weekNumber;
        this.isPresent = isPresent;
    }

    // Bu metotlar sayesinde private olan verilere dışarıdan güvenli bir şekilde ulaşıyoruz (Getter).
    public String getCourseCode() { return courseCode; }
    public String getStudentNo() { return studentNo; }
    public int getWeekNumber() { return weekNumber; }
    public boolean isPresent() { return isPresent; }

    // Sonradan yoklama durumunu değiştirmek istersek (mesela yanlış girildiyse) bu metodu kullanıyoruz.
    public void setPresent(boolean present) { isPresent = present; }
}