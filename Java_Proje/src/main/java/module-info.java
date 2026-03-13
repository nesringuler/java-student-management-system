module com.example.java_proje {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;

    opens com.example.java_proje to javafx.fxml;

    opens com.example.java_proje.model to com.google.gson;
    opens com.example.java_proje.data to com.google.gson;

    exports com.example.java_proje;
}