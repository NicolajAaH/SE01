module projectSDU2.presentation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires postgresql;
    requires com.google.gson;

    opens projectSDU2.business.domain.credit to com.google.gson;
    opens projectSDU2.business.domain.user to com.google.gson;
    opens projectSDU2.presentation to javafx.fxml;
    exports projectSDU2.presentation;

}