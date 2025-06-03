module com.example.cab302project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
//    requires org.junit.jupiter.api;
    requires java.sql;
    requires java.desktop;

    opens com.example.cab302project to javafx.fxml;
    exports com.example.cab302project;
    exports com.example.cab302project.Model;
    opens com.example.cab302project.Model to javafx.fxml;
    exports com.example.cab302project.Controller;
    opens com.example.cab302project.Controller to javafx.fxml;
}
