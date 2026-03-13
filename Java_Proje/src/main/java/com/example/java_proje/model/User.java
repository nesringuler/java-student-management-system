package com.example.java_proje.model;

// Kullanıcının kimlik, giriş ve akademik bilgilerini tutan model sınıfımız.
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String title;

    private String faculty;
    private String department;

    // Kullanıcı oluşturulurken verileri atayan yapıcı metot. Null kontrolü yaparak boş değerleri yönetiyoruz.
    public User(String email, String password, String firstName, String lastName, String title, String faculty, String department) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        // Eğer fakülte veya bölüm bilgisi girilmediyse hata almamak için null yerine boş metin atıyoruz.
        this.faculty = (faculty == null) ? "" : faculty;
        this.department = (department == null) ? "" : department;
    }

    // Verilere erişim sağlayan getter metotları.
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getTitle() { return title; }


    public String getFaculty() { return faculty; }
    public String getDepartment() { return department; }

    // Unvan, isim ve soyismi birleştirerek tam adı veren yardımcı metot.
    public String getFullName() {
        return title + " " + firstName + " " + lastName;
    }
}