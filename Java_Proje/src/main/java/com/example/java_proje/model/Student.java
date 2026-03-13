package com.example.java_proje.model;

// Öğrenci numarasını ve adını tutan model sınıfımız.
public class Student {
    private String studentNo;
    private String fullName;

    // Öğrenci nesnesi oluşturulurken bilgileri atayan yapıcı metot.
    public Student(String studentNo, String fullName) {
        this.studentNo = studentNo;
        this.fullName = fullName;
    }

    // Verileri okumak ve güncellemek için getter ve setter metotları.
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    // Nesnenin string olarak (örneğin listede) nasıl görüneceğini belirtiyoruz.
    @Override
    public String toString() {
        return studentNo + " - " + fullName;
    }
}