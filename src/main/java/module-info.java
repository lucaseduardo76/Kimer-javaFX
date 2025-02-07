module com.workshop.kimer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.workshop.kimer to javafx.fxml;
    exports com.workshop.kimer;
}