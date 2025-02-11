module com.workshop.kimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Adicionado para garantir compatibilidade com JavaFX
    requires java.sql;
    requires jdk.jshell;

    opens com.workshop.kimer to javafx.fxml;
    exports com.workshop.kimer;

    opens com.workshop.demo to javafx.fxml;
    exports com.workshop.demo;

    opens com.workshop.model.entities to javafx.base;

}
