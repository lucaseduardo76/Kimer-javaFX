module com.workshop.kimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Adicionado para garantir compatibilidade com JavaFX
    requires java.sql;
    requires jdk.jshell;
    requires static lombok;

    opens com.workshop.kimer to javafx.fxml;
    exports com.workshop.kimer;

    opens com.workshop.demo to javafx.fxml;
    exports com.workshop.demo;

    opens com.workshop.model.entities to javafx.base;

    opens com.workshop.model.Dto to javafx.base; // Permite o acesso ao pacote
    exports com.workshop.model.service;

}
