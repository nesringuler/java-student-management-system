package com.example.java_proje;

import com.example.java_proje.model.Attendance;
import com.example.java_proje.model.Course;
import com.example.java_proje.model.StudentGrade;
import com.example.java_proje.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataManager {

    // Dosyaların kaydedileceği yeri sistemden alıyoruz ki her bilgisayarda çalışsın.
    private static final String BASE_PATH = System.getProperty("user.dir") + File.separator;

    private static final String USERS_FILE = BASE_PATH + "users.json";
    private static final String COURSES_FILE = BASE_PATH + "courses.json";
    private static final String GRADES_FILE = BASE_PATH + "student_grades.json";
    private static final String ATTENDANCE_FILE = BASE_PATH + "attendance.json";
    private static final String TODOS_FILE = BASE_PATH + "todos.json";
    private static final String NOTES_FILE = BASE_PATH + "notes.json";

    static {
        System.out.println("Veri dosyaları şu konumda: " + BASE_PATH);
        ensureDefaultUserExists();
    }

    // Veritabanı kullanmadığımız için tüm öğrenci listesini buraya ekledik.
    private static List<StudentGrade> getMasterStudentList() {
        List<StudentGrade> list = new ArrayList<>();

        list.add(new StudentGrade("150820001", "BORA", "CEYLAN", "", "", "", "", ""));
        list.add(new StudentGrade("170422505", "ONUR BURAK", "SU", "", "", "", "", ""));
        list.add(new StudentGrade("170423011", "HAYAT", "DİLER", "", "", "", "", ""));
        list.add(new StudentGrade("170423033", "ALP KAAN", "AYAS", "", "", "", "", ""));
        list.add(new StudentGrade("170423505", "FURKAN", "ADIGÜZEL", "", "", "", "", ""));
        list.add(new StudentGrade("170423821", "MUHAMMET EMİN", "ANLAR", "", "", "", "", ""));
        list.add(new StudentGrade("170423921", "NOMEN JAVO", "RAZAKARIVONY", "", "", "", "", ""));
        list.add(new StudentGrade("170423996", "MARIA", "ALREFAI", "", "", "", "", ""));
        list.add(new StudentGrade("170424001", "NAZ", "ALTINBAŞ", "", "", "", "", ""));
        list.add(new StudentGrade("170424002", "ENDER", "YILMAZ", "", "", "", "", ""));
        list.add(new StudentGrade("170424003", "YUSUF SALİH", "SÜMER", "", "", "", "", ""));
        list.add(new StudentGrade("170424004", "OĞUZHAN", "DÜNDAR", "", "", "", "", ""));
        list.add(new StudentGrade("170424005", "MUHAMMET EMİN", "ZORA", "", "", "", "", ""));
        list.add(new StudentGrade("170424006", "SALİH EYMEN", "DÖNMEZ", "", "", "", "", ""));
        list.add(new StudentGrade("170424007", "KAĞAN", "ARSLAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424008", "YAĞMUR", "BOZKURT", "", "", "", "", ""));
        list.add(new StudentGrade("170424009", "YUNUS EMRE", "GEMİCİ", "", "", "", "", ""));
        list.add(new StudentGrade("170424011", "ELİF BÜŞRA", "ÇAYLAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424012", "MEHMET EMİN", "DİLBER", "", "", "", "", ""));
        list.add(new StudentGrade("170424013", "MUHAMMET EMİR", "ALGÜL", "", "", "", "", ""));
        list.add(new StudentGrade("170424014", "AHMET BİLAL", "YAZICIOĞLU", "", "", "", "", ""));
        list.add(new StudentGrade("170424015", "ÖMER KEREM", "ÇATAKLI", "", "", "", "", ""));
        list.add(new StudentGrade("170424016", "SEYRAN", "DURMUŞ", "", "", "", "", ""));
        list.add(new StudentGrade("170424017", "MUSTAFA TAHA", "ÖKSÜZ", "", "", "", "", ""));
        list.add(new StudentGrade("170424018", "SÜLEYMAN SEMİH", "ERKEN", "", "", "", "", ""));
        list.add(new StudentGrade("170424019", "EFE ARDA", "YÜCE", "", "", "", "", ""));
        list.add(new StudentGrade("170424020", "YAVUZ ÖMER", "DİKEL", "", "", "", "", ""));
        list.add(new StudentGrade("170424021", "AHMET YİĞİT", "DAYI", "", "", "", "", ""));
        list.add(new StudentGrade("170424022", "BERKE EMİN", "GİRİT", "", "", "", "", ""));
        list.add(new StudentGrade("170424023", "FATMA", "TANRIVERDİ", "", "", "", "", ""));
        list.add(new StudentGrade("170424024", "MEHMET SELİM", "ÖZDEMİR", "", "", "", "", ""));
        list.add(new StudentGrade("170424025", "EYÜP", "DEMİRKAPI", "", "", "", "", ""));
        list.add(new StudentGrade("170424026", "SELİN MÜGE", "TERZİ", "", "", "", "", ""));
        list.add(new StudentGrade("170424027", "HATİCE NUR", "TAPAR", "", "", "", "", ""));
        list.add(new StudentGrade("170424029", "AHMET EMRE", "ÖZÜMAĞI", "", "", "", "", ""));
        list.add(new StudentGrade("170424030", "ÖMER SADIK", "AYDIN", "", "", "", "", ""));
        list.add(new StudentGrade("170424031", "ABDULLAH", "TAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424032", "CEM", "ŞENGÜL", "", "", "", "", ""));
        list.add(new StudentGrade("170424033", "AYBERK", "ÇEVİK", "", "", "", "", ""));
        list.add(new StudentGrade("170424034", "EGEMEN", "ARDA", "", "", "", "", ""));
        list.add(new StudentGrade("170424035", "EREN", "EROĞLU", "", "", "", "", ""));
        list.add(new StudentGrade("170424036", "ASUDE", "ATAKUMAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424037", "YASİN", "BAYRAKTAR", "", "", "", "", ""));
        list.add(new StudentGrade("170424038", "RABİA", "ÜNLÜ", "", "", "", "", ""));
        list.add(new StudentGrade("170424039", "SELAMİ TUNAHAN", "KAYGISIZ", "", "", "", "", ""));
        list.add(new StudentGrade("170424040", "ERDEM", "KAYA", "", "", "", "", ""));
        list.add(new StudentGrade("170424041", "CEM ANIL", "ERDEM", "", "", "", "", ""));
        list.add(new StudentGrade("170424042", "MEHMET BURAK", "AKAR", "", "", "", "", ""));
        list.add(new StudentGrade("170424044", "NESRİN", "GÜLER", "", "", "", "", ""));
        list.add(new StudentGrade("170424045", "EMRE", "ARSLAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424046", "SERRA", "TANIŞ", "", "", "", "", ""));
        list.add(new StudentGrade("170424047", "ORHAN", "ÇEPPIOĞLU", "", "", "", "", ""));
        list.add(new StudentGrade("170424048", "MUHAMMET ALİ", "YALÇIN", "", "", "", "", ""));
        list.add(new StudentGrade("170424049", "ÖMER ŞERİF", "ODUNCU", "", "", "", "", ""));
        list.add(new StudentGrade("170424050", "SACİT TARHAN", "YILMAZ", "", "", "", "", ""));
        list.add(new StudentGrade("170424051", "HÜSEYİN", "EKİZ", "", "", "", "", ""));
        list.add(new StudentGrade("170424052", "ELİF NAZ", "KIDIL", "", "", "", "", ""));
        list.add(new StudentGrade("170424053", "BURAK", "NİZAM", "", "", "", "", ""));
        list.add(new StudentGrade("170424054", "MUSTAFA DENİZ", "ÇAM", "", "", "", "", ""));
        list.add(new StudentGrade("170424055", "ERHAN", "ÖZTÜRK", "", "", "", "", ""));
        list.add(new StudentGrade("170424056", "EMİN", "YAMAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424057", "AHMET BERAT", "YÜRÜK", "", "", "", "", ""));
        list.add(new StudentGrade("170424058", "SADİ SAMED", "KARABULUT", "", "", "", "", ""));
        list.add(new StudentGrade("170424059", "CİVAN MİR NAZIM", "EKİNCİ", "", "", "", "", ""));
        list.add(new StudentGrade("170424060", "BAHADIR TUNA", "ÖCAL", "", "", "", "", ""));
        list.add(new StudentGrade("170424061", "AHMET SAİT", "ÖZBEK", "", "", "", "", ""));
        list.add(new StudentGrade("170424062", "KEREM SADIK", "HACIÖMEROĞLU", "", "", "", "", ""));
        list.add(new StudentGrade("170424063", "ARDA", "DEMİRBİLEK", "", "", "", "", ""));
        list.add(new StudentGrade("170424064", "MÜMÜN CAN", "YILDIZ", "", "", "", "", ""));
        list.add(new StudentGrade("170424065", "KEREM", "GÜNLÜOĞLU", "", "", "", "", ""));
        list.add(new StudentGrade("170424066", "EDA", "ÖZTÜRK", "", "", "", "", ""));
        list.add(new StudentGrade("170424067", "İDİL", "ŞEN", "", "", "", "", ""));
        list.add(new StudentGrade("170424506", "TUNAHAN", "KAVAKLI", "", "", "", "", ""));
        list.add(new StudentGrade("170424507", "ENES", "BULUT", "", "", "", "", ""));
        list.add(new StudentGrade("170424515", "VEFA", "ŞENTÜRK", "", "", "", "", ""));
        list.add(new StudentGrade("170424522", "RAMAZAN YUSUF", "UÇAK", "", "", "", "", ""));
        list.add(new StudentGrade("170424526", "ALİ TALHA", "YURTSEVEN", "", "", "", "", ""));
        list.add(new StudentGrade("170424851", "GÖKHAN", "ÇOBAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424852", "VOLKAN", "DEMİRÖRS", "", "", "", "", ""));
        list.add(new StudentGrade("170424950", "RAUL", "KAZIMLI", "", "", "", "", ""));
        list.add(new StudentGrade("170424951", "FİDAN", "IMAMALIYEVA", "", "", "", "", ""));
        list.add(new StudentGrade("170424961", "RAMAZAN", "BOZAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424962", "METEHAN", "AYDOĞDU", "", "", "", "", ""));
        list.add(new StudentGrade("170424963", "TALAT SAMET", "TURGUTLU", "", "", "", "", ""));
        list.add(new StudentGrade("170424964", "MEHMET", "ARSLAN", "", "", "", "", ""));
        list.add(new StudentGrade("170424965", "VEYSEL", "GÖKÇE", "", "", "", "", ""));
        list.add(new StudentGrade("171423002", "YUSUF TUNAHAN", "ERGIN", "", "", "", "", ""));
        list.add(new StudentGrade("171423004", "EFE SERHAT", "EREK", "", "", "", "", ""));
        list.add(new StudentGrade("171423009", "TUNAHAN", "KOÇAK", "", "", "", "", ""));
        list.add(new StudentGrade("171423010", "BATUHAN", "ÇITAK", "", "", "", "", ""));
        list.add(new StudentGrade("171423501", "BARIŞ", "ÇALIŞKAN", "", "", "", "", ""));
        list.add(new StudentGrade("171424001", "KORAY", "ÇİFTÇİ", "", "", "", "", ""));
        list.add(new StudentGrade("171424002", "İSMAİL EFE", "TERLEMEZ", "", "", "", "", ""));
        list.add(new StudentGrade("171424003", "TAHA ALP", "AYDIN", "", "", "", "", ""));
        list.add(new StudentGrade("171424004", "FAHRİ", "ERDOĞAN", "", "", "", "", ""));
        list.add(new StudentGrade("171424005", "KEREM", "KAYNAR", "", "", "", "", ""));
        list.add(new StudentGrade("171424006", "KUZEY YAĞIZ", "YILDIZ", "", "", "", "", ""));
        list.add(new StudentGrade("171424007", "KADİR", "KUŞKONMAZ", "", "", "", "", ""));
        list.add(new StudentGrade("171424008", "MUHAMMED", "ÇOBANHAN", "", "", "", "", ""));
        list.add(new StudentGrade("171424010", "TAHA YASİR", "GÜZELDAL", "", "", "", "", ""));
        list.add(new StudentGrade("171424011", "MURAT", "ÇOLAKOĞLU", "", "", "", "", ""));
        list.add(new StudentGrade("171424012", "ÖMER", "AVCU", "", "", "", "", ""));
        list.add(new StudentGrade("171424013", "İBRAHİM ERDEM", "TOPCU", "", "", "", "", ""));
        list.add(new StudentGrade("171424014", "KAAN", "COŞKUN", "", "", "", "", ""));
        list.add(new StudentGrade("171424015", "YUSUF TALHA", "SAĞIRLI", "", "", "", "", ""));
        list.add(new StudentGrade("171424016", "YUNUS EMRE", "GÜLTEKİN", "", "", "", "", ""));
        list.add(new StudentGrade("171424017", "İSMAİL TAHA", "EROL", "", "", "", "", ""));
        list.add(new StudentGrade("171424018", "TALHA", "YILDIZ", "", "", "", "", ""));
        list.add(new StudentGrade("171424019", "İLKNUR", "GÜNER", "", "", "", "", ""));
        list.add(new StudentGrade("171424020", "EMİR", "PEKER", "", "", "", "", ""));
        list.add(new StudentGrade("171424530", "FATİH", "KAYA", "", "", "", "", ""));
        list.add(new StudentGrade("199824112", "ÖMER", "KAYA", "", "", "", "", ""));

        return list;
    }

    // Öğrenci giriş yaparken büyük küçük harf hatası yaparsa sorun çıkmasın diye kontrol ediyoruz.
    public static boolean validateStudentLogin(String inputName, String inputNo) {
        List<StudentGrade> allStudents = getMasterStudentList();

        java.util.Locale trLocale = new java.util.Locale("tr", "TR");

        String cleanInputName = inputName.trim().toUpperCase(trLocale);
        String cleanInputNo = inputNo.trim();

        for (StudentGrade s : allStudents) {
            String dbFullName = (s.getFirstName() + " " + s.getLastName()).trim().toUpperCase(trLocale);

            if (s.getStudentNo().equals(cleanInputNo) && dbFullName.equals(cleanInputName)) {
                return true;
            }
        }

        System.out.println("Giriş Başarısız: " + cleanInputName);
        return false;
    }

    // Hoca yeni ders eklediğinde öğrencileri o derse boş notlarla kaydediyoruz.
    public static void initializeGradesForNewCourse(String courseCode) {
        List<StudentGrade> allGrades = loadGrades(null);
        List<StudentGrade> masterStudentList = getMasterStudentList();

        boolean courseExists = false;
        for(StudentGrade sg : allGrades) {
            if(sg.getCourseCode().equals(courseCode)) {
                courseExists = true;
                break;
            }
        }

        if (!courseExists) {
            for (StudentGrade templateStudent : masterStudentList) {
                allGrades.add(new StudentGrade(
                        templateStudent.getStudentNo(),
                        templateStudent.getFirstName(),
                        templateStudent.getLastName(),
                        courseCode,
                        "", "", "", ""
                ));
            }
            saveGrades(allGrades);
        }
    }

    // Dosyadan devamsızlık verilerini okuyoruz.
    public static List<Attendance> loadAttendance(String courseCode) {
        List<Attendance> list = new ArrayList<>();
        File file = new File(ATTENDANCE_FILE);
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String code = extractValue(line, "course");
                if (courseCode == null || (code != null && code.equals(courseCode))) {
                    String stuNo = extractValue(line, "student");
                    String weekStr = extractValue(line, "week");
                    String statusStr = extractValue(line, "status");
                    if (stuNo != null && weekStr != null && statusStr != null) {
                        list.add(new Attendance(code, stuNo, Integer.parseInt(weekStr), Boolean.parseBoolean(statusStr)));
                    }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    // Devamsızlık verilerini güncelleyip dosyaya tekrar yazıyoruz.
    public static void saveAttendance(String courseCode, List<Attendance> newRecords) {
        List<Attendance> allRecords = loadAttendance(null);
        allRecords.removeIf(att -> att.getCourseCode().equals(courseCode));
        allRecords.addAll(newRecords);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ATTENDANCE_FILE))) {
            for (Attendance att : allRecords) {
                String jsonLine = String.format("{\"course\":\"%s\", \"student\":\"%s\", \"week\":\"%d\", \"status\":\"%b\"}",
                        att.getCourseCode(), att.getStudentNo(), att.getWeekNumber(), att.isPresent());
                writer.write(jsonLine);
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Öğrencinin aldığı dersleri filtreleyip getiriyoruz.
    public static List<Course> getStudentCourses(String studentNo) {
        List<Course> allCourses = loadCourses(null);
        List<Course> studentCourses = new ArrayList<>();
        for (Course c : allCourses) {
            List<StudentGrade> grades = loadGrades(c.getCode());
            for (StudentGrade sg : grades) {
                if (sg.getStudentNo().equals(studentNo)) {
                    studentCourses.add(c);
                    break;
                }
            }
        }
        return studentCourses;
    }

    // Notları json formatında dosyaya kaydediyoruz.
    public static void saveGrades(List<StudentGrade> grades) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GRADES_FILE))) {
            for (StudentGrade sg : grades) {
                String jsonLine = String.format("{\"no\":\"%s\", \"name\":\"%s\", \"surname\":\"%s\", \"code\":\"%s\", \"mid\":\"%s\", \"fin\":\"%s\", \"hw\":\"%s\", \"proj\":\"%s\"}",
                        sg.getStudentNo(), sg.getFirstName(), sg.getLastName(), sg.getCourseCode(), sg.getMidterm(), sg.getFinalExam(), sg.getHomework(), sg.getProject());
                writer.write(jsonLine);
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<StudentGrade> loadGrades(String courseCode) {
        List<StudentGrade> grades = new ArrayList<>();
        File file = new File(GRADES_FILE);
        if (!file.exists()) return grades;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String code = extractValue(line, "code");
                if (courseCode == null || (code != null && code.equals(courseCode))) {
                    grades.add(new StudentGrade(extractValue(line, "no"), extractValue(line, "name"), extractValue(line, "surname"), code, extractValue(line, "mid"), extractValue(line, "fin"), extractValue(line, "hw"), extractValue(line, "proj")));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return grades;
    }

    // Derslerin not ortalamasını hesaplayıp listeliyoruz.
    public static List<String> getCourseAverageList(String instructorEmail) {
        List<Course> myCourses = loadCourses(instructorEmail);
        List<String> resultList = new ArrayList<>();
        for (Course c : myCourses) {
            List<StudentGrade> grades = loadGrades(c.getCode());
            double totalAverage = 0;
            int studentCount = 0;
            for (StudentGrade sg : grades) {
                double mid = parseGrade(sg.getMidterm());
                double fin = parseGrade(sg.getFinalExam());
                double proj = parseGrade(sg.getProject());
                double hw = parseGrade(sg.getHomework());
                double studentAvg = (mid * 0.3) + (fin * 0.5) + (proj * 0.1) + (hw * 0.1);
                if (mid + fin + proj + hw > 0) {
                    totalAverage += studentAvg;
                    studentCount++;
                }
            }
            double courseAvg = (studentCount == 0) ? 0.0 : (totalAverage / studentCount);
            resultList.add(c.getCode() + ": " + String.format("%.2f", courseAvg));
        }
        return resultList;
    }

    private static double parseGrade(String gradeStr) {
        if (gradeStr == null || gradeStr.isEmpty()) return 0.0;
        try { return Double.parseDouble(gradeStr); } catch (NumberFormatException e) { return 0.0; }
    }

    public static List<String> loadToDos(String email) {
        List<String> todos = new ArrayList<>();
        File file = new File(TODOS_FILE);
        if (!file.exists()) return todos;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String dbEmail = extractValue(line, "email");
                if (dbEmail != null && dbEmail.equals(email)) {
                    String todo = extractValue(line, "todo");
                    if (todo != null) todos.add(todo);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return todos;
    }

    public static void saveToDo(String email, String todo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TODOS_FILE, true))) {
            writer.write(String.format("{\"email\":\"%s\", \"todo\":\"%s\"}", email, todo));
            writer.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void deleteToDo(String email, String todoToDelete) {
        List<String> linesToKeep = new ArrayList<>();
        File file = new File(TODOS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String dbEmail = extractValue(line, "email");
                    String dbTodo = extractValue(line, "todo");
                    if (dbEmail != null && dbEmail.equals(email) && dbTodo != null && dbTodo.equals(todoToDelete)) continue;
                    linesToKeep.add(line);
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TODOS_FILE))) {
            for (String line : linesToKeep) { writer.write(line); writer.newLine(); }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Not defterindeki verileri okurken alt satırları düzeltiyoruz.
    public static String loadNote(String email) {
        File file = new File(NOTES_FILE);
        if (!file.exists()) return "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String dbEmail = extractValue(line, "email");
                if (dbEmail != null && dbEmail.equals(email)) {
                    String note = extractValue(line, "note");
                    return note != null ? note.replace("[NL]", "\n") : "";
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return "";
    }

    public static void saveNote(String email, String noteContent) {
        List<String> otherLines = new ArrayList<>();
        File file = new File(NOTES_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String dbEmail = extractValue(line, "email");
                    if (dbEmail == null || !dbEmail.equals(email)) otherLines.add(line);
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTES_FILE))) {
            for (String line : otherLines) { writer.write(line); writer.newLine(); }
            String flattenedNote = noteContent.replace("\n", "[NL]");
            writer.write(String.format("{\"email\":\"%s\", \"note\":\"%s\"}", email, flattenedNote));
            writer.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String email = extractValue(line, "email");
                if (email != null) {
                    String faculty = extractValue(line, "faculty");
                    String department = extractValue(line, "department");
                    users.add(new User(email, extractValue(line, "password"), extractValue(line, "firstName"), extractValue(line, "lastName"), extractValue(line, "title"), faculty, department));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return users;
    }

    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(String.format("{\"email\":\"%s\", \"password\":\"%s\", \"firstName\":\"%s\", \"lastName\":\"%s\", \"title\":\"%s\", \"faculty\":\"%s\", \"department\":\"%s\"}",
                    user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getTitle(), user.getFaculty(), user.getDepartment()));
            writer.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Profil güncelleme işleminde dosyayı yeniden yazıyoruz.
    public static void updateUserProfile(String email, String newTitle, String newFaculty, String newDept) {
        List<User> users = loadUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) {
                if (u.getEmail().equals(email)) {
                    writer.write(String.format("{\"email\":\"%s\", \"password\":\"%s\", \"firstName\":\"%s\", \"lastName\":\"%s\", \"title\":\"%s\", \"faculty\":\"%s\", \"department\":\"%s\"}",
                            u.getEmail(), u.getPassword(), u.getFirstName(), u.getLastName(), newTitle, newFaculty, newDept));
                } else {
                    writer.write(String.format("{\"email\":\"%s\", \"password\":\"%s\", \"firstName\":\"%s\", \"lastName\":\"%s\", \"title\":\"%s\", \"faculty\":\"%s\", \"department\":\"%s\"}",
                            u.getEmail(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getTitle(), u.getFaculty(), u.getDepartment()));
                }
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static int getUniqueStudentCount(String instructorEmail) {
        List<Course> myCourses = loadCourses(instructorEmail);
        Set<String> uniqueStudentIds = new HashSet<>();
        for (Course c : myCourses) {
            List<StudentGrade> grades = loadGrades(c.getCode());
            for (StudentGrade sg : grades) uniqueStudentIds.add(sg.getStudentNo());
        }
        return uniqueStudentIds.size();
    }

    public static double getOverallAverage(String instructorEmail) { return 0.0; }

    // Giriş yaparken bilgileri kontrol ediyoruz.
    public static User validateUser(String email, String password) {
        for (User user : loadUsers()) if (user.getEmail().equals(email) && user.getPassword().equals(password)) return user;
        return null;
    }

    public static boolean emailVarMi(String email) {
        for (User user : loadUsers()) if (user.getEmail().equalsIgnoreCase(email)) return true;
        return false;
    }

    public static void saveCourse(Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSES_FILE, true))) {
            writer.write(String.format("{\"code\":\"%s\", \"name\":\"%s\", \"day\":\"%s\", \"time\":\"%s\", \"classroom\":\"%s\", \"instructor\":\"%s\"}",
                    course.getCode(), course.getName(), course.getDay(), course.getTime(), course.getClassroom(), course.getInstructorEmail()));
            writer.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void deleteCourse(Course courseToDelete) {
        List<Course> all = loadCourses(null);
        all.removeIf(c -> c.getCode().equals(courseToDelete.getCode()) && c.getInstructorEmail().equals(courseToDelete.getInstructorEmail()));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSES_FILE))) {
            for (Course c : all) {
                writer.write(String.format("{\"code\":\"%s\", \"name\":\"%s\", \"day\":\"%s\", \"time\":\"%s\", \"classroom\":\"%s\", \"instructor\":\"%s\"}",
                        c.getCode(), c.getName(), c.getDay(), c.getTime(), c.getClassroom(), c.getInstructorEmail()));
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<Course> loadCourses(String instructorEmail) {
        List<Course> courses = new ArrayList<>();
        File file = new File(COURSES_FILE);
        if (!file.exists()) return courses;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String instructor = extractValue(line, "instructor");
                if (instructorEmail == null || (instructor != null && instructor.equals(instructorEmail)))
                    courses.add(new Course(extractValue(line, "code"), extractValue(line, "name"), extractValue(line, "day"), extractValue(line, "time"), extractValue(line, "classroom"), instructor));
            }
        } catch (IOException e) { e.printStackTrace(); }
        return courses;
    }

    public static void updateCourse(Course oldCourse, Course newCourse) {
        List<Course> all = loadCourses(null);
        for (int i = 0; i < all.size(); i++) {
            Course c = all.get(i);
            if (c.getCode().equals(oldCourse.getCode()) && c.getInstructorEmail().equals(oldCourse.getInstructorEmail())) {
                all.set(i, newCourse); break;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSES_FILE))) {
            for (Course c : all) {
                writer.write(String.format("{\"code\":\"%s\", \"name\":\"%s\", \"day\":\"%s\", \"time\":\"%s\", \"classroom\":\"%s\", \"instructor\":\"%s\"}",
                        c.getCode(), c.getName(), c.getDay(), c.getTime(), c.getClassroom(), c.getInstructorEmail()));
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // İlk açılışta admin kullanıcısı yoksa oluşturuyoruz.
    private static void ensureDefaultUserExists() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            User defaultUser = new User("admin", "123", "Admin", "Hoca", "Prof. Dr.", "Teknoloji Fakültesi", "Bilgisayar Müh.");
            saveUser(defaultUser);
        }
    }

    // Json verisini parçalamak için yazdığımız basit fonksiyon.
    private static String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int startIndex = json.indexOf(pattern);
        if (startIndex == -1) return null;
        startIndex += pattern.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}