module com.mycompany.carrera {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.carrera to javafx.fxml;
    exports com.mycompany.carrera;
}
