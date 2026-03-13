package com.example.java_proje;

import com.example.java_proje.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    // Tasarım dosyasındaki unvan kutusunu ve metin alanlarını buraya bağlıyoruz.
    @FXML private ComboBox<String> titleComboBox;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;

    // Sayfa ilk yüklendiğinde çalışır ve unvan kutusunu akademik seçeneklerle doldurur.
    @FXML
    public void initialize() {
        titleComboBox.getItems().addAll("Arş. Gör.", "Öğr. Gör.", "Dr. Öğr. Üyesi", "Doç. Dr.", "Prof. Dr.", "Bölüm Bşk.", "Dekan");
    }

    // Kayıt ol butonuna basıldığında çalışan ana fonksiyonumuz.
    @FXML
    protected void onRegisterButtonClick() {
        // Formdaki verileri değişkenlere alıyoruz.
        String unvan = titleComboBox.getValue();
        String ad = firstNameField.getText();
        String soyad = lastNameField.getText();
        String email = emailField.getText();
        String sifre = passwordField.getText();
        String sifreTekrar = confirmPasswordField.getText();

        // Herhangi bir alan boş bırakılmış mı diye kontrol ediyoruz.
        if (unvan == null || ad.isEmpty() || soyad.isEmpty() || email.isEmpty() || sifre.isEmpty()) {
            mesajGoster("Eksik Bilgi", "Lütfen unvan dahil tüm alanları doldurun!", Alert.AlertType.WARNING);
            return;
        }

        // Girilen e-posta adresi sistemde zaten kayıtlı mı diye veritabanına bakıyoruz.
        if (DataManager.emailVarMi(email)) {
            mesajGoster("Kayıt Hatası", "Bu e-posta adresi zaten kullanılıyor!", Alert.AlertType.ERROR);
            return;
        }

        // Girilen şifreler birbiriyle uyuşuyor mu kontrol ediyoruz.
        if (!sifre.equals(sifreTekrar)) {
            mesajGoster("Hata", "Girdiğiniz şifreler uyuşmuyor!", Alert.AlertType.ERROR);
            return;
        }

        // Tüm kontrollerden geçerse yeni kullanıcı nesnesini oluşturup dosyaya kaydediyoruz.
        User yeniKullanici = new User(email, sifre, ad, soyad, unvan, "", "");
        DataManager.saveUser(yeniKullanici);

        mesajGoster("Başarılı", "Kayıt tamamlandı! Giriş yapabilirsiniz.", Alert.AlertType.INFORMATION);
        girisEkraninaDon();
    }

    // Kullanıcı kayıt olmaktan vazgeçerse giriş ekranına geri dönmesini sağlıyoruz.
    @FXML
    protected void onBackToLoginClick() {
        girisEkraninaDon();
    }

    // Ekran değiştirme kodlarını tekrar yazmamak için ortak bir fonksiyon kullandık.
    private void girisEkraninaDon() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setMaximized(false);
            currentStage.setMaximized(true);
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Kullanıcıya bilgi veya hata mesajı göstermek için alert oluşturuyoruz.
    private void mesajGoster(String baslik, String mesaj, Alert.AlertType tur) {
        Alert alert = new Alert(tur);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}