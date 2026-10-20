module com.mycompany.conteoclicks {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.conteoclicks to javafx.fxml;
    exports com.mycompany.conteoclicks;
}
