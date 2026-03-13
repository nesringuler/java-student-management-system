package com.example.java_proje;

import com.example.java_proje.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    // Arayüzdeki giriş kutularını ve butonları buraya bağlıyoruz.
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button toggleModeButton;
    @FXML private Button registerButton;

    // Hangi modda olduğumuzu tutan değişken. Varsayılan olarak akademisyen modu kapalı.
    private boolean isStudentMode = false;

    @FXML
    public void initialize() {
        updateUI(); // Ekran ilk açıldığında doğru tasarımı yüklüyoruz.
    }

    // Sağ üstteki mod değiştirme butonuna basınca çalışır.
    @FXML
    protected void onToggleModeClick() {
        isStudentMode = !isStudentMode;
        updateUI();
    }

    // Seçilen moda göre ekrandaki yazıların ve butonların durumunu güncelliyoruz.
    private void updateUI() {
        if (isStudentMode) {
            // Öğrenci modundaysa yapılacak ayarlar
            toggleModeButton.setText("Akademisyen Girişi Yap");
            usernameField.setPromptText("Ad Soyad (Örn: Ahmet Yılmaz)");
            passwordField.setPromptText("Öğrenci Numarası");
            loginButton.setText("Öğrenci Girişi");
            if(registerButton != null) registerButton.setVisible(false); // Öğrenci kayıt olamaz buton gizlenir.
        } else {
            // Akademisyen modundaysa yapılacak ayarlar
            toggleModeButton.setText("Öğrenci Girişi Yap");
            usernameField.setPromptText("Kurumsal E-posta");
            passwordField.setPromptText("Şifre");
            loginButton.setText("Giriş Yap");
            if(registerButton != null) registerButton.setVisible(true);
        }
        usernameField.clear();
        passwordField.clear();
    }

    // Giriş yap butonuna basıldığında çalışan ana kontrol fonksiyonumuz.
    @FXML
    protected void onLoginButtonClick() {
        String input1 = usernameField.getText().trim();
        String input2 = passwordField.getText().trim();

        // Boş alan kontrolü yapıyoruz.
        if (input1.isEmpty() || input2.isEmpty()) {
            mesajGoster("Eksik Bilgi", "Lütfen tüm alanları doldurun.");
            return;
        }

        if (isStudentMode) {
            // Öğrenci girişiyse listeden kontrol et.
            if (DataManager.validateStudentLogin(input1, input2)) {
                openDashboardForStudent(input2, input1.toUpperCase());
            } else {
                mesajGoster("Giriş Hatası", "İsim veya Numara hatalı! Listede olduğunuzdan emin olun.");
            }
        } else {
            // Hoca girişiyse veritabanından kontrol et.
            User girisYapanKullanici = DataManager.validateUser(input1, input2);
            if (girisYapanKullanici != null) {
                openDashboardForAcademic(girisYapanKullanici);
            } else {
                mesajGoster("Giriş Hatası", "E-posta veya şifre hatalı!");
            }
        }
    }

    // Öğrenci paneli açılırken bilgileri diğer sayfaya aktarıyoruz.
    private void openDashboardForStudent(String studentNo, String fullName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            DashboardController controller = fxmlLoader.getController();
            controller.setStudentMode(studentNo, fullName); // Öğrenci modunu aktifleştir.
            switchScene(scene, "Öğrenci Bilgi Sistemi");
        } catch (Exception e) {
            e.printStackTrace(); // Konsola yaz
            mesajGoster("Sistem Hatası", "Panel dosyası yüklenemedi!\nHata detayı: " + e.getMessage());
        }
    }

    // Akademisyen paneli açılırken hoca bilgilerini diğer sayfaya gönderiyoruz.
    private void openDashboardForAcademic(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            DashboardController controller = fxmlLoader.getController();
            controller.setKullaniciBilgileri(user); // Hoca bilgilerini aktar.
            switchScene(scene, "Akademisyen Paneli");
        } catch (Exception e) {
            e.printStackTrace();
            mesajGoster("Sistem Hatası", "Panel dosyası yüklenemedi!\nHata detayı: " + e.getMessage());
        }
    }

    // Eski pencereyi kapatıp yeni pencereyi tam ekran açan yardımcı fonksiyon.
    private void switchScene(Scene scene, String title) {
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        currentStage.close();
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle(title);
        dashboardStage.setScene(scene);
        dashboardStage.setMaximized(true);
        dashboardStage.show();
    }

    // Kayıt Ol linkine tıklayınca kayıt sayfasına yönlendirir.
    @FXML
    protected void onRegisterLinkClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setMaximized(true);
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Kullanıcıya hata mesajlarını gösteren kutucuk.
    private void mesajGoster(String baslik, String mesaj) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}