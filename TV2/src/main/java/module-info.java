module projectSDU2.presentation {
    requires javafx.controls;
    requires javafx.fxml;

    opens projectSDU2.presentation to javafx.fxml;
    exports projectSDU2.presentation;
}