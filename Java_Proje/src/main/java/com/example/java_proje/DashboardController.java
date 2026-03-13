package com.example.java_proje;

import com.example.java_proje.model.Attendance;
import com.example.java_proje.model.Course;
import com.example.java_proje.model.StudentGrade;
import com.example.java_proje.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class DashboardController {

    // --- ARAYÜZ BİLEŞENLERİ ---
    @FXML private Label welcomeLabel;
    @FXML private Label emailLabel;
    @FXML private Label facultyLabel;
    @FXML private Label lblHomeTitle;

    // Menü
    @FXML private Button btnMenuHome;
    @FXML private Button btnMenuCourses;
    @FXML private Button btnMenuGrades;
    @FXML private Button btnMenuAttendance;
    @FXML private Button btnMenuSchedule;
    @FXML private VBox sidebarContainer;
    @FXML private Label lblTitleGrades;
    @FXML private Label lblTitleAttendance;

    // İstatistikler
    @FXML private Label lblTotalCourses;
    @FXML private Label lblTotalStudents;
    @FXML private VBox avgContainer;

    // Widgetlar
    @FXML private ListView<String> todoListView;
    @FXML private TextField txtTodoInput;
    @FXML private TextArea txtNoteArea;
    @FXML private VBox todoContainer;
    @FXML private VBox notesContainer;

    // Sayfalar
    @FXML private VBox homePagePane;
    @FXML private AnchorPane coursesPagePane;
    @FXML private VBox gradesPagePane;
    @FXML private VBox attendancePagePane;
    @FXML private VBox schedulePagePane;

    // Ders İşlemleri
    @FXML private VBox coursesContainer;
    @FXML private VBox addCourseForm;
    @FXML private TextField txtCourseCode;
    @FXML private TextField txtCourseName;
    @FXML private ComboBox<String> comboDay;
    @FXML private TextField txtTime;
    @FXML private TextField txtClassroom;
    @FXML private Label lblFormTitle;
    @FXML private Button btnSaveCourse;
    @FXML private Button btnAddNewCourse;

    // Not Giriş Ekranı
    @FXML private ComboBox<String> gradesCourseCombo;
    // Burası notlar filtresinin olduğu kutu (HBox), bunu gizleyeceğiz
    @FXML private HBox gradesFilterBox;
    @FXML private TableView<StudentGrade> gradesTable;
    @FXML private Button btnSaveGrades;

    // Devamsızlık Ekranı
    @FXML private ComboBox<String> attendanceCourseCombo;
    @FXML private TableView<Object> attendanceTable;
    @FXML private Button btnSaveAttendance;

    // Ders Programı
    @FXML private VBox colPazartesi, colSali, colCarsamba, colPersembe, colCuma, colCumartesi, colPazar;

    // Veriler
    private User currentUser;
    private boolean isStudentMode = false;
    private String currentStudentNumber;
    private String currentStudentName;

    private ObservableList<StudentGrade> currentGradesList;
    private ObservableList<Object> attendanceDataList;
    private ObservableList<String> todoListItems;
    private Course editingCourse = null;

    @FXML
    public void initialize() {
        try {
            if (comboDay != null) comboDay.getItems().addAll("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar");

            // Başlangıçta tabloları boş kuralım
            setupGradesTable();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- HOCA GİRİŞİ ---
    public void setKullaniciBilgileri(User user) {
        this.currentUser = user;
        this.isStudentMode = false;

        applyDesign(false);
        enableAcademicFeatures(true);
        updateProfileUI();

        setupGradesTable(); // Hoca tablosunu kur
        setupAttendanceTableForInstructor(); // Hoca devamsızlık tablosunu kur

        loadCourses();
        loadCoursesIntoCombo();
        showHomePage();
    }

    // --- ÖĞRENCİ GİRİŞİ ---
    public void setStudentMode(String studentNo, String fullName) {
        this.isStudentMode = true;
        this.currentStudentNumber = studentNo.trim();
        this.currentStudentName = fullName;
        this.currentUser = null;

        applyDesign(true);
        enableAcademicFeatures(false);
        updateProfileUI();

        setupGradesTable(); // Öğrenci tablosunu (Ders Adı görünecek şekilde) kur
        setupAttendanceTableForStudent(); // Öğrenci devamsızlık tablosunu kur

        loadCourses();
        loadCoursesIntoCombo();

        // Öğrenci sayısını gizle
        if(lblTotalStudents != null) lblTotalStudents.setText("-");
        if(avgContainer != null) avgContainer.getChildren().clear();
        showHomePage();
    }

    private void applyDesign(boolean isStudent) {
        if (isStudent) {
            if (sidebarContainer != null) sidebarContainer.setStyle("-fx-background-color: #00695c;");
            if (btnMenuGrades != null) btnMenuGrades.setText("📄  Notlarım");
            if (btnMenuAttendance != null) btnMenuAttendance.setText("📅  Devamsızlık Durumum");
            if (lblTitleGrades != null) lblTitleGrades.setText("Not Dökümü (Transkript)");
            if (lblTitleAttendance != null) lblTitleAttendance.setText("Devamsızlık Çizelgem");
            if (lblHomeTitle != null) lblHomeTitle.setStyle("-fx-text-fill: #00695c; -fx-font-weight: bold; -fx-font-size: 28px;");
        } else {
            if (sidebarContainer != null) sidebarContainer.setStyle("-fx-background-color: #002855;");
            if (btnMenuGrades != null) btnMenuGrades.setText("📝  Not Girişi");
            if (btnMenuAttendance != null) btnMenuAttendance.setText("🚫  Devamsızlık Takibi");
            if (lblTitleGrades != null) lblTitleGrades.setText("Not Girişi");
            if (lblTitleAttendance != null) lblTitleAttendance.setText("Devamsızlık Takibi");
            if (lblHomeTitle != null) lblHomeTitle.setStyle("-fx-text-fill: #002855; -fx-font-weight: bold; -fx-font-size: 28px;");
        }
    }

    private void enableAcademicFeatures(boolean enable) {
        if(addCourseForm != null) addCourseForm.setVisible(false);
        if(btnAddNewCourse != null) btnAddNewCourse.setVisible(enable);
        if(btnSaveGrades != null) btnSaveGrades.setVisible(enable);
        if(btnSaveAttendance != null) btnSaveAttendance.setVisible(enable);

        if(todoContainer != null) todoContainer.setVisible(true);
        if(notesContainer != null) notesContainer.setVisible(true);

        if(gradesTable != null) gradesTable.setEditable(enable);
        if(attendanceTable != null) attendanceTable.setEditable(true);
    }

    // --- DERS KAYDETME ---
    @FXML private void saveNewCourse() {
        String code = txtCourseCode.getText().trim();
        String name = txtCourseName.getText().trim();
        String day = comboDay.getValue();
        String time = txtTime.getText().trim();
        String classroom = txtClassroom.getText().trim();

        if (code.isEmpty() || name.isEmpty() || day == null || time.isEmpty() || classroom.isEmpty()) {
            showAlert("Eksik Bilgi", "Lütfen tüm alanları doldurun.", Alert.AlertType.WARNING);
            return;
        }

        String timeRegex = "\\d{2}[.:]\\d{2}\\s*-\\s*\\d{2}[.:]\\d{2}";
        if (!Pattern.matches(timeRegex, time)) {
            showAlert("Hatalı Saat Formatı", "Lütfen saati şu formatta giriniz:\nÖrn: 09.00-12.00", Alert.AlertType.ERROR);
            return;
        }

        Course newCourse = new Course(code, name, day, time, classroom, currentUser.getEmail());

        if (editingCourse != null) {
            DataManager.updateCourse(editingCourse, newCourse);
            showAlert("Başarılı", "Ders güncellendi.", Alert.AlertType.INFORMATION);
            editingCourse = null;
        } else {
            DataManager.saveCourse(newCourse);
            DataManager.initializeGradesForNewCourse(code);
            showAlert("Başarılı", "Ders başarıyla eklendi.", Alert.AlertType.INFORMATION);
        }

        txtCourseCode.clear(); txtCourseName.clear(); comboDay.getSelectionModel().clearSelection(); txtTime.clear(); txtClassroom.clear();
        addCourseForm.setVisible(false);
        loadCourses();
        loadCoursesIntoCombo();
        refreshDashboardStats();
    }

    // --- NOT TABLOSU AYARLARI (GÜNCELLENDİ) ---
    private void setupGradesTable() {
        if(gradesTable == null) return;
        gradesTable.getColumns().clear();

        // Hoca için de Öğrenci için de tabloyu ekrana tam yayıyoruz (Boşluk kalmasın)
        gradesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        if (isStudentMode) {
            // ÖĞRENCİ MODU: Dersin ADINI gösteren sütun yapıyoruz.
            TableColumn<StudentGrade, String> colCourseName = new TableColumn<>("Ders");
            colCourseName.setCellValueFactory(data -> {
                // Ders kodundan dersin adını buluyoruz
                String code = data.getValue().getCourseCode();
                List<Course> allCourses = DataManager.loadCourses(null);
                for (Course c : allCourses) {
                    if (c.getCode().equals(code)) {
                        return new SimpleStringProperty(c.getName()); // Ders adını döndür
                    }
                }
                return new SimpleStringProperty(code); // Bulamazsa kodunu döndür
            });
            colCourseName.setMinWidth(150);

            TableColumn<StudentGrade, String> colMid = new TableColumn<>("Vize");
            colMid.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMidterm()));
            colMid.setStyle("-fx-alignment: CENTER;");

            TableColumn<StudentGrade, String> colFin = new TableColumn<>("Final");
            colFin.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFinalExam()));
            colFin.setStyle("-fx-alignment: CENTER;");

            TableColumn<StudentGrade, String> colProj = new TableColumn<>("Proje");
            colProj.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProject()));
            colProj.setStyle("-fx-alignment: CENTER;");

            TableColumn<StudentGrade, String> colHw = new TableColumn<>("Ödev");
            colHw.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHomework()));
            colHw.setStyle("-fx-alignment: CENTER;");

            gradesTable.getColumns().addAll(colCourseName, colMid, colFin, colProj, colHw);
        } else {
            // HOCA MODU: Klasik görünüm
            TableColumn<StudentGrade, String> colNo = new TableColumn<>("Öğrenci No");
            colNo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudentNo()));
            colNo.setMinWidth(100);

            TableColumn<StudentGrade, String> colName = new TableColumn<>("Adı Soyadı");
            colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullName()));
            colName.setMinWidth(200);

            TableColumn<StudentGrade, String> colMid = createValidatedGradeColumn("Vize", "midterm");
            TableColumn<StudentGrade, String> colFin = createValidatedGradeColumn("Final", "finalExam");
            TableColumn<StudentGrade, String> colProj = createValidatedGradeColumn("Proje", "project");
            TableColumn<StudentGrade, String> colHw = createValidatedGradeColumn("Ödev", "homework");

            gradesTable.getColumns().addAll(colNo, colName, colMid, colFin, colProj, colHw);
        }
    }

    private TableColumn<StudentGrade, String> createValidatedGradeColumn(String title, String fieldType) {
        TableColumn<StudentGrade, String> col = new TableColumn<>(title);

        if (fieldType.equals("midterm")) col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMidterm()));
        else if (fieldType.equals("finalExam")) col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFinalExam()));
        else if (fieldType.equals("project")) col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProject()));
        else col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHomework()));

        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setStyle("-fx-alignment: CENTER;");

        col.setOnEditCommit(e -> {
            if (isStudentMode) return;
            String input = e.getNewValue();
            if (input == null || input.trim().isEmpty()) {
                updateGradeValue(e.getRowValue(), fieldType, "");
                return;
            }
            try {
                double val = Double.parseDouble(input.replace(",", "."));
                if (val < 0 || val > 100) {
                    showAlert("Hatalı Not", "Not 0 ile 100 arasında olmalıdır!", Alert.AlertType.ERROR);
                    gradesTable.refresh();
                } else {
                    updateGradeValue(e.getRowValue(), fieldType, String.valueOf(val));
                }
            } catch (NumberFormatException ex) {
                showAlert("Hatalı Veri", "Lütfen sadece sayı giriniz", Alert.AlertType.ERROR);
                gradesTable.refresh();
            }
        });
        return col;
    }

    private void updateGradeValue(StudentGrade sg, String field, String value) {
        switch (field) {
            case "midterm": sg.setMidterm(value); break;
            case "finalExam": sg.setFinalExam(value); break;
            case "project": sg.setProject(value); break;
            case "homework": sg.setHomework(value); break;
        }
    }

    private void refreshDashboardStats() {
        if (currentUser == null && !isStudentMode) return;

        if (isStudentMode) {
            List<Course> myCourses = DataManager.getStudentCourses(currentStudentNumber);
            if (lblTotalCourses != null) lblTotalCourses.setText(String.valueOf(myCourses.size()));

            if (lblTotalStudents != null && lblTotalStudents.getParent() != null) {
                lblTotalStudents.getParent().setVisible(false);
                lblTotalStudents.getParent().setManaged(false);
            }

            if (avgContainer != null) {
                avgContainer.setVisible(true);
                avgContainer.getChildren().clear();
                Label title = new Label("Aldığım Dersler");
                title.setStyle("-fx-font-weight: bold; -fx-text-fill: #002855; -fx-font-size: 15px; -fx-padding: 0 0 5 0;");
                avgContainer.getChildren().add(title);

                for (Course c : myCourses) {
                    Label lbl = new Label("👉 " + c.getName());
                    lbl.setStyle("-fx-text-fill: #444; -fx-font-size: 13px; -fx-padding: 2;");
                    lbl.setWrapText(true);
                    avgContainer.getChildren().add(lbl);
                }
            }
            return;
        }

        if (lblTotalStudents != null && lblTotalStudents.getParent() != null) {
            lblTotalStudents.getParent().setVisible(true);
            lblTotalStudents.getParent().setManaged(true);
        }

        List<Course> courses = DataManager.loadCourses(currentUser.getEmail());
        if (lblTotalCourses != null) lblTotalCourses.setText(String.valueOf(courses.size()));

        int studentCount = DataManager.getUniqueStudentCount(currentUser.getEmail());
        if (lblTotalStudents != null) lblTotalStudents.setText(String.valueOf(studentCount));

        if (avgContainer != null) {
            avgContainer.getChildren().clear();
            List<String> averages = DataManager.getCourseAverageList(currentUser.getEmail());
            for (String avgText : averages) {
                Label lbl = new Label(avgText);
                lbl.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold; -fx-font-size: 14px;");
                avgContainer.getChildren().add(lbl);
            }
        }
    }

    private void loadWidgets() {
        String ownerId = isStudentMode ? currentStudentNumber : (currentUser != null ? currentUser.getEmail() : null);
        if (ownerId == null) return;

        List<String> todos = DataManager.loadToDos(ownerId);
        todoListItems = FXCollections.observableArrayList(todos);
        if (todoListView != null) todoListView.setItems(todoListItems);

        String note = DataManager.loadNote(ownerId);
        if (txtNoteArea != null) txtNoteArea.setText(note);
    }

    private void updateProfileUI() {
        if (isStudentMode) {
            if(welcomeLabel != null) welcomeLabel.setText(currentStudentName);
            if(emailLabel != null) emailLabel.setText("No: " + currentStudentNumber);
            if(facultyLabel != null) facultyLabel.setText("Öğrenci Paneli");
        } else if (currentUser != null) {
            if(welcomeLabel != null) welcomeLabel.setText(currentUser.getFullName());
            if(emailLabel != null) emailLabel.setText(currentUser.getEmail());
            String info = (!currentUser.getFaculty().isEmpty()) ? currentUser.getFaculty() : "";
            if (!currentUser.getDepartment().isEmpty()) info += " / " + currentUser.getDepartment();
            if(facultyLabel != null) facultyLabel.setText(info.isEmpty() ? "Profilinizi düzenleyin" : info);
        }
    }

    @FXML private void onProfileClicked() {
        if(isStudentMode) {
            showAlert("Bilgi", "Öğrenci bilgileri otomatik çekilmektedir.", Alert.AlertType.INFORMATION);
            return;
        }
        Dialog<User> dialog = new Dialog<>(); dialog.setTitle("Profili Düzenle"); dialog.setHeaderText("Akademik Bilgilerinizi Güncelleyin");
        ButtonType loginButtonType = new ButtonType("Kaydet", ButtonBar.ButtonData.OK_DONE); dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane(); grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20, 150, 10, 10));
        ComboBox<String> titleBox = new ComboBox<>(); titleBox.getItems().addAll("Arş. Gör.", "Öğr. Gör.", "Dr. Öğr. Üyesi", "Doç. Dr.", "Prof. Dr.", "Bölüm Bşk.", "Dekan"); titleBox.setValue(currentUser.getTitle());
        TextField facultyField = new TextField(); facultyField.setPromptText("Örn: Teknoloji Fakültesi"); facultyField.setText(currentUser.getFaculty());
        TextField deptField = new TextField(); deptField.setPromptText("Örn: Bilgisayar Mühendisliği"); deptField.setText(currentUser.getDepartment());

        grid.add(new Label("Unvan:"), 0, 0); grid.add(titleBox, 1, 0); grid.add(new Label("Fakülte:"), 0, 1); grid.add(facultyField, 1, 1); grid.add(new Label("Bölüm:"), 0, 2); grid.add(deptField, 1, 2);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> { if (dialogButton == loginButtonType) { return new User(currentUser.getEmail(), currentUser.getPassword(), currentUser.getFirstName(), currentUser.getLastName(), titleBox.getValue(), facultyField.getText(), deptField.getText()); } return null; });

        Optional<User> result = dialog.showAndWait();
        result.ifPresent(updatedData -> { DataManager.updateUserProfile(currentUser.getEmail(), updatedData.getTitle(), updatedData.getFaculty(), updatedData.getDepartment()); this.currentUser = new User(currentUser.getEmail(), currentUser.getPassword(), currentUser.getFirstName(), currentUser.getLastName(), updatedData.getTitle(), updatedData.getFaculty(), updatedData.getDepartment()); updateProfileUI(); showAlert("Başarılı", "Profiliniz güncellendi!", Alert.AlertType.INFORMATION); });
    }

    @FXML private void onLogoutClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) ((welcomeLabel != null) ? welcomeLabel.getScene().getWindow() : homePagePane.getScene().getWindow());
            currentStage.setScene(loginScene);
            currentStage.setTitle("Ders Takip - Giriş");
            currentStage.setMaximized(false);
            currentStage.setMaximized(true);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void loadCourses() {
        if(coursesContainer == null) return;
        coursesContainer.getChildren().clear();
        List<Course> myCourses;

        if (isStudentMode) myCourses = DataManager.getStudentCourses(currentStudentNumber);
        else myCourses = DataManager.loadCourses(currentUser.getEmail());

        if (lblTotalCourses != null && isStudentMode) lblTotalCourses.setText(String.valueOf(myCourses.size()));

        for (Course course : myCourses) {
            HBox courseRow = new HBox(); courseRow.setSpacing(10);
            courseRow.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
            courseRow.setPrefWidth(780);
            String instructorInfo = isStudentMode ? (" | Hoca: " + course.getInstructorEmail()) : "";
            Label infoLabel = new Label(course.getCode() + " - " + course.getName() + "\n" + "📅 " + course.getDay() + "    ⏰ " + course.getTime() + "    📍 " + course.getClassroom() + instructorInfo);
            infoLabel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-text-fill: #333;");

            HBox spacer = new HBox(); HBox.setHgrow(spacer, Priority.ALWAYS);
            courseRow.getChildren().addAll(infoLabel, spacer);

            if (!isStudentMode) {
                Button editBtn = new Button("Düzenle ✏️"); editBtn.setStyle("-fx-background-color: #e3f2fd; -fx-text-fill: #1976d2;");
                editBtn.setOnAction(e -> openEditMode(course));
                Button deleteBtn = new Button("Sil 🗑"); deleteBtn.setStyle("-fx-background-color: #ffebee; -fx-text-fill: #c62828;");
                deleteBtn.setOnAction(e -> { DataManager.deleteCourse(course); loadCourses(); refreshDashboardStats(); });
                courseRow.getChildren().addAll(editBtn, deleteBtn);
            }
            coursesContainer.getChildren().add(courseRow);
        }
    }

    @FXML private void onGradesCourseSelected() {
        if(gradesCourseCombo == null) return;
        String selectedCode = gradesCourseCombo.getValue();
        if (selectedCode == null) return;
        List<StudentGrade> grades = DataManager.loadGrades(selectedCode);
        currentGradesList = FXCollections.observableArrayList();

        if (isStudentMode) {
            for (StudentGrade sg : grades) if (sg.getStudentNo().trim().equals(currentStudentNumber)) currentGradesList.add(sg);
        } else currentGradesList.addAll(grades);
        if(gradesTable != null) gradesTable.setItems(currentGradesList);
    }

    @FXML private void onAttendanceCourseSelected() {
        if(attendanceCourseCombo == null) return;
        String selectedCode = attendanceCourseCombo.getValue();
        if (selectedCode == null) return;

        List<StudentGrade> students = DataManager.loadGrades(selectedCode);
        List<Attendance> savedAttendance = DataManager.loadAttendance(selectedCode);
        attendanceDataList = FXCollections.observableArrayList();

        for (StudentGrade sg : students) {
            StudentAttendanceRow row = new StudentAttendanceRow(sg.getStudentNo(), sg.getFullName());
            for (Attendance att : savedAttendance) {
                if (att.getStudentNo().trim().equals(sg.getStudentNo().trim())) {
                    row.setPresent(att.getWeekNumber(), att.isPresent());
                }
            }
            attendanceDataList.add(row);
        }

        if(attendanceTable != null) {
            attendanceTable.setItems(attendanceDataList);
            attendanceTable.refresh();
        }
    }

    @FXML private void showHomePage() { setPaneVisible(homePagePane); refreshDashboardStats(); loadWidgets(); }
    @FXML private void showCoursesPage() { setPaneVisible(coursesPagePane); loadCourses(); }

    @FXML private void showGradesPage() {
        setPaneVisible(gradesPagePane);

        if (isStudentMode) {
            // "Ders:" filtresinin bulunduğu kutuyu gizliyoruz.
            // FXML'de gradesCourseCombo'nun olduğu HBox'a erişebilmek için parent kontrolü yapıyoruz.
            if (gradesCourseCombo != null && gradesCourseCombo.getParent() != null) {
                gradesCourseCombo.getParent().setVisible(false);
                gradesCourseCombo.getParent().setManaged(false);
            }

            // Tüm notları toplayıp tabloya basıyoruz
            List<Course> myCourses = DataManager.getStudentCourses(currentStudentNumber);
            currentGradesList = FXCollections.observableArrayList();

            for (Course c : myCourses) {
                List<StudentGrade> grades = DataManager.loadGrades(c.getCode());
                for (StudentGrade sg : grades) {
                    if (sg.getStudentNo().trim().equals(currentStudentNumber)) {
                        currentGradesList.add(sg);
                        break;
                    }
                }
            }
            if(gradesTable != null) gradesTable.setItems(currentGradesList);

        } else {
            // HOCA İSE: Filtre kutusunu geri açıyoruz
            if (gradesCourseCombo != null && gradesCourseCombo.getParent() != null) {
                gradesCourseCombo.getParent().setVisible(true);
                gradesCourseCombo.getParent().setManaged(true);
            }
            if (gradesCourseCombo != null) gradesCourseCombo.setVisible(true);
            loadCoursesIntoCombo();
        }
    }

    @FXML private void showAttendancePage() {
        setPaneVisible(attendancePagePane);

        if (isStudentMode) {
            // ÖĞRENCİ İSE: Ders seçme kutusunu gizle
            if (attendanceCourseCombo != null && attendanceCourseCombo.getParent() != null) {
                attendanceCourseCombo.getParent().setVisible(false);
                attendanceCourseCombo.getParent().setManaged(false);
            }

            // Tabloyu öğrenci verisiyle doldur (Ders Adı - Haftalar)
            attendanceDataList = FXCollections.observableArrayList();
            List<Course> myCourses = DataManager.getStudentCourses(currentStudentNumber);

            for (Course c : myCourses) {
                CourseAttendanceRow row = new CourseAttendanceRow(c.getName());
                List<Attendance> atts = DataManager.loadAttendance(c.getCode());
                for (Attendance a : atts) {
                    if (a.getStudentNo().equals(currentStudentNumber)) {
                        row.setPresent(a.getWeekNumber(), a.isPresent());
                    }
                }
                attendanceDataList.add(row);
            }
            if (attendanceTable != null) {
                attendanceTable.setItems(attendanceDataList);
                attendanceTable.refresh();
            }

        } else {
            // HOCA İSE: Ders seçme kutusunu göster
            if (attendanceCourseCombo != null && attendanceCourseCombo.getParent() != null) {
                attendanceCourseCombo.getParent().setVisible(true);
                attendanceCourseCombo.getParent().setManaged(true);
            }
            loadCoursesIntoComboForAttendance();
        }
    }

    @FXML private void showSchedulePage() { setPaneVisible(schedulePagePane); loadSchedule(); }

    private void setPaneVisible(Pane paneToShow) {
        if(homePagePane != null) homePagePane.setVisible(paneToShow == homePagePane);
        if(coursesPagePane != null) coursesPagePane.setVisible(paneToShow == coursesPagePane);
        if(gradesPagePane != null) gradesPagePane.setVisible(paneToShow == gradesPagePane);
        if(attendancePagePane != null) attendancePagePane.setVisible(paneToShow == attendancePagePane);
        if(schedulePagePane != null) schedulePagePane.setVisible(paneToShow == schedulePagePane);
    }

    // --- DEVAMSIZLIK TABLOSU (ÖĞRENCİ) ---
    private void setupAttendanceTableForStudent() {
        if(attendanceTable == null) return;
        attendanceTable.getColumns().clear();
        attendanceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Ekrana yay

        TableColumn<Object, String> colCourse = new TableColumn<>("Ders Adı");
        colCourse.setCellValueFactory(data -> new SimpleStringProperty(((CourseAttendanceRow)data.getValue()).getCourseName()));
        colCourse.setMinWidth(150);
        attendanceTable.getColumns().add(colCourse);

        for (int i = 1; i <= 14; i++) {
            final int weekIndex = i;
            TableColumn<Object, String> colWeek = new TableColumn<>("H" + i);
            colWeek.setCellFactory(column -> new TableCell<>() {
                @Override protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Object itemData = getTableRow().getItem();
                        if (itemData instanceof CourseAttendanceRow) {
                            CourseAttendanceRow row = (CourseAttendanceRow) itemData;
                            boolean isPresent = row.isPresent(weekIndex);
                            if (isPresent) {
                                setText("✔");
                                setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-alignment: CENTER;");
                            } else {
                                setText("✗");
                                setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-alignment: CENTER;");
                            }
                        }
                    }
                }
            });
            attendanceTable.getColumns().add(colWeek);
        }
    }

    // --- DEVAMSIZLIK TABLOSU (HOCA) ---
    private void setupAttendanceTableForInstructor() {
        if(attendanceTable == null) return;
        attendanceTable.getColumns().clear();
        attendanceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Hoca tablosu da tam yayılsın

        TableColumn<Object, String> colNo = new TableColumn<>("No");
        colNo.setCellValueFactory(data -> new SimpleStringProperty(((StudentAttendanceRow)data.getValue()).getStudentNo()));
        colNo.setMinWidth(90);

        TableColumn<Object, String> colName = new TableColumn<>("Adı Soyadı");
        colName.setCellValueFactory(data -> new SimpleStringProperty(((StudentAttendanceRow)data.getValue()).getFullName()));
        colName.setMinWidth(180);

        attendanceTable.getColumns().addAll(colNo, colName);

        for (int i = 1; i <= 14; i++) {
            final int weekIndex = i;
            TableColumn<Object, String> colWeek = new TableColumn<>("H" + i);
            // Hoca için sütunları biraz daraltabiliriz ama constrained olunca otomatik ayarlanır
            colWeek.setPrefWidth(35);

            colWeek.setCellFactory(column -> new TableCell<>() {
                private final Button btn = new Button();
                @Override protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getTableRow() == null || getTableRow().getItem() == null) setGraphic(null);
                    else {
                        Object itemData = getTableRow().getItem();
                        if (!(itemData instanceof StudentAttendanceRow)) return;

                        StudentAttendanceRow row = (StudentAttendanceRow) itemData;
                        boolean isPresent = row.isPresent(weekIndex);

                        if (isPresent) { btn.setText("✔"); btn.setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-radius: 50;"); }
                        else { btn.setText("✗"); btn.setStyle("-fx-background-color: #ffebee; -fx-text-fill: red; -fx-font-weight: bold; -fx-background-radius: 50;"); }

                        btn.setOnAction(e -> {
                            boolean newState = !row.isPresent(weekIndex);
                            row.setPresent(weekIndex, newState);
                            if (newState) { btn.setText("✔"); btn.setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: green;"); }
                            else { btn.setText("✗"); btn.setStyle("-fx-background-color: #ffebee; -fx-text-fill: red;"); }
                        });
                        setGraphic(btn);
                    }
                }
            });
            attendanceTable.getColumns().add(colWeek);
        }
    }

    private void loadCoursesIntoCombo() { if(gradesCourseCombo == null) return; gradesCourseCombo.getItems().clear(); List<Course> courses = isStudentMode ? DataManager.getStudentCourses(currentStudentNumber) : DataManager.loadCourses(currentUser.getEmail()); for (Course c : courses) gradesCourseCombo.getItems().add(c.getCode()); }
    private void loadCoursesIntoComboForAttendance() { if(attendanceCourseCombo == null) return; attendanceCourseCombo.getItems().clear(); List<Course> courses = isStudentMode ? DataManager.getStudentCourses(currentStudentNumber) : DataManager.loadCourses(currentUser.getEmail()); for (Course c : courses) attendanceCourseCombo.getItems().add(c.getCode()); }

    @FXML private void onAddTodo() { String ownerId = isStudentMode ? currentStudentNumber : (currentUser != null ? currentUser.getEmail() : null); if (ownerId == null) return; String text = txtTodoInput.getText(); if (text != null && !text.trim().isEmpty()) { DataManager.saveToDo(ownerId, text.trim()); todoListItems.add(text.trim()); txtTodoInput.clear(); } }
    @FXML private void onDeleteTodo() { String ownerId = isStudentMode ? currentStudentNumber : (currentUser != null ? currentUser.getEmail() : null); if (ownerId == null) return; String selected = todoListView.getSelectionModel().getSelectedItem(); if (selected != null) { DataManager.deleteToDo(ownerId, selected); todoListItems.remove(selected); } }
    @FXML private void onSaveNote() { String ownerId = isStudentMode ? currentStudentNumber : (currentUser != null ? currentUser.getEmail() : null); if (ownerId == null) return; DataManager.saveNote(ownerId, txtNoteArea.getText()); showAlert("Başarılı", "Notunuz kaydedildi!", Alert.AlertType.INFORMATION); }

    // --- VERİ KAYBI SORUNUNU ÇÖZEN METOT ---
    @FXML
    private void saveGradesChanges() {
        if(isStudentMode) return;

        // Önce tüm verileri çekiyoruz (dosyayı okuyoruz)
        List<StudentGrade> allGrades = DataManager.loadGrades(null);
        String currentCourseCode = gradesCourseCombo.getValue();

        if (currentCourseCode == null) return;

        // Hafızadaki listeden, sadece şu an düzenlediğimiz dersin eski notlarını siliyoruz.
        // Diğer derslerin notlarına dokunmuyoruz.
        allGrades.removeIf(sg -> sg.getCourseCode().equals(currentCourseCode));

        // Tablodaki güncel notları listeye ekliyoruz.
        allGrades.addAll(gradesTable.getItems());

        // Tüm listeyi (diğer dersler + güncel ders) dosyaya geri yazıyoruz.
        DataManager.saveGrades(allGrades);

        showAlert("Başarılı", "Notlar veritabanına güvenle kaydedildi.", Alert.AlertType.INFORMATION);
    }

    @FXML private void saveAttendanceChanges() { if(isStudentMode) return; String selectedCode = attendanceCourseCombo.getValue(); if (selectedCode == null || attendanceDataList == null) return; List<Attendance> recordsToSave = new ArrayList<>(); for (Object item : attendanceDataList) { StudentAttendanceRow row = (StudentAttendanceRow) item; for (int i = 1; i <= 14; i++) recordsToSave.add(new Attendance(selectedCode, row.getStudentNo(), i, row.isPresent(i))); } DataManager.saveAttendance(selectedCode, recordsToSave); showAlert("Başarılı", "Devamsızlık verileri kaydedildi!", Alert.AlertType.INFORMATION); }

    private void loadSchedule() {
        if(colPazartesi != null) colPazartesi.getChildren().clear();
        if(colSali != null) colSali.getChildren().clear();
        if(colCarsamba != null) colCarsamba.getChildren().clear();
        if(colPersembe != null) colPersembe.getChildren().clear();
        if(colCuma != null) colCuma.getChildren().clear();
        if(colCumartesi != null) colCumartesi.getChildren().clear();
        if(colPazar != null) colPazar.getChildren().clear();

        List<Course> courses;
        if(isStudentMode) courses = DataManager.getStudentCourses(currentStudentNumber);
        else courses = DataManager.loadCourses(currentUser.getEmail());

        courses.sort(Comparator.comparingInt(c -> parseStartTime(c.getTime())));

        for (Course c : courses) {
            VBox card = createCourseCard(c);
            if (c.getDay() == null) continue;
            switch (c.getDay()) {
                case "Pazartesi": if(colPazartesi != null) colPazartesi.getChildren().add(card); break;
                case "Salı": if(colSali != null) colSali.getChildren().add(card); break;
                case "Çarşamba": if(colCarsamba != null) colCarsamba.getChildren().add(card); break;
                case "Perşembe": if(colPersembe != null) colPersembe.getChildren().add(card); break;
                case "Cuma": if(colCuma != null) colCuma.getChildren().add(card); break;
                case "Cumartesi": if(colCumartesi != null) colCumartesi.getChildren().add(card); break;
                case "Pazar": if(colPazar != null) colPazar.getChildren().add(card); break;
            }
        }
    }

    private int parseStartTime(String timeStr) { if (timeStr == null || timeStr.isEmpty()) return 9999; try { String startPart = timeStr.split("[-–]")[0].trim().replace(".", ":"); String[] parts = startPart.split(":"); int hour = Integer.parseInt(parts[0]); int minute = (parts.length > 1) ? Integer.parseInt(parts[1]) : 0; return hour * 60 + minute; } catch (Exception e) { return 9999; } }

    private VBox createCourseCard(Course c) { VBox card = new VBox(); card.setSpacing(5); card.setPadding(new Insets(10)); card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 0);"); Label codeLbl = new Label(c.getCode()); codeLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #002855; -fx-font-size: 14px;"); Label nameLbl = new Label(c.getName()); nameLbl.setWrapText(true); nameLbl.setStyle("-fx-text-fill: #555; -fx-font-size: 11px;"); Label timeLbl = new Label("⏰ " + c.getTime()); timeLbl.setStyle("-fx-text-fill: #888; -fx-font-size: 10px;"); Label roomLbl = new Label("📍 " + c.getClassroom()); roomLbl.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 10px; -fx-font-weight: bold;"); card.getChildren().addAll(codeLbl, nameLbl, new Separator(), timeLbl, roomLbl); return card; }

    private void openEditMode(Course c) { this.editingCourse = c; txtCourseCode.setText(c.getCode()); txtCourseName.setText(c.getName()); comboDay.setValue(c.getDay()); txtTime.setText(c.getTime()); txtClassroom.setText(c.getClassroom()); if(lblFormTitle != null) lblFormTitle.setText("Dersi Düzenle"); if(btnSaveCourse != null) btnSaveCourse.setText("Güncelle"); addCourseForm.setVisible(true); }
    @FXML private void showAddCourseForm() { if(isStudentMode) { showAlert("Uyarı", "Öğrenciler ders ekleyemez.", Alert.AlertType.WARNING); return;} editingCourse = null; txtCourseCode.clear(); txtCourseName.clear(); comboDay.getSelectionModel().clearSelection(); txtTime.clear(); txtClassroom.clear(); if(lblFormTitle != null) lblFormTitle.setText("Yeni Ders Ekle"); if(btnSaveCourse != null) btnSaveCourse.setText("Dersi Kaydet"); addCourseForm.setVisible(true); }
    @FXML private void hideAddCourseForm() { if(addCourseForm != null) addCourseForm.setVisible(false); editingCourse = null; }

    private void showAlert(String title, String message, Alert.AlertType type) { Alert alert = new Alert(type); alert.setTitle(title); alert.setHeaderText(null); alert.setContentText(message); alert.showAndWait(); }

    public static class StudentAttendanceRow { private String studentNo; private String fullName; private boolean[] weeks = new boolean[15]; public StudentAttendanceRow(String studentNo, String fullName) { this.studentNo = studentNo; this.fullName = fullName; } public String getStudentNo() { return studentNo; } public String getFullName() { return fullName; } public boolean isPresent(int week) { return weeks[week]; } public void setPresent(int week, boolean status) { weeks[week] = status; } }

    public static class CourseAttendanceRow { private String courseName; private boolean[] weeks = new boolean[15]; public CourseAttendanceRow(String courseName) { this.courseName = courseName; } public String getCourseName() { return courseName; } public boolean isPresent(int week) { return weeks[week]; } public void setPresent(int week, boolean status) { weeks[week] = status; } }
}