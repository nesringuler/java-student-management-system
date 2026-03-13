package com.example.java_proje.data;

import com.example.java_proje.model.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    // Verileri kaydedeceğimiz dosyanın adını veriyoruz.
    private static final String FILE_NAME = "dersler.json";
    private Gson gson;

    public DataHelper() {
        this.gson = new Gson();
    }

    //Kaydetme fonksiyonumuz listeden alıp dosyaya yazıyoruz.
    public void saveCourses(List<Course> courseList) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            // Gson kütüphanesi listeyi JSON formatına çevirip yazar.
            gson.toJson(courseList, writer);
            System.out.println("Veriler başarıyla kaydedildi!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Kaydederken bir hata oluştu :(");
        }
    }

    // Bu fonksiyonumuz dosyayı okuyup liste olarak bize geri veriyor.
    public List<Course> loadCourses() {
        // Dosya var mı kontrol et (İlk açılışta olmaz çünkü).
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>(); // Dosya yoksa boş liste döndür.
        }

        try (Reader reader = new FileReader(FILE_NAME)) {
            // Gson'a dosya içinde liste olduğunun bilgisinş veriyoruz.
            Type listType = new TypeToken<ArrayList<Course>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}