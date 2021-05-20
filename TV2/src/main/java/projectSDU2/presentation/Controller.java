package projectSDU2.presentation;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import projectSDU2.Interfaces.DomainI;
import projectSDU2.business.domain.DomainConnect;

import java.io.IOException;

public class Controller {

    //Attributter
    private static DomainI domainI = new DomainConnect();
    static String type = "";
    static String email;
    static String accountPassword;
    static int id = -1;

    //FXML Attributter
    @FXML
    private Button login;
    @FXML
    private Label status;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;

    //Getter
    public static DomainI getDomainI() {
        return domainI;
    }

    //Initialize som køres når programmet startes
    public void initialize() {
        domainI.runSetup();
    }

    //Håndterer når der klikkes login
    @FXML
    private void loginHandler() {
        if (domainI.authorize(emailField.getText(), passwordField.getText())) { //Autoriserer brugeren, og sætter attributterne
            type = domainI.findPerson(emailField.getText()).getType();
            email = emailField.getText();
            accountPassword = passwordField.getText();
            id = domainI.findPerson(emailField.getText()).getId();
            newFxml("core.fxml"); //Skift fxml fil
        } else {
            status.setText("Incorrect login information.");
        }
    }

    //Metode til at skifte fxml fil
    public void newFxml(String fileName) {
        Parent newRoot = null;
        try {
            newRoot = runGUI.getFxmlLoader().load(getClass().getClassLoader().getResource(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        runGUI.getStage().setScene(new Scene(newRoot));
        runGUI.getStage().show();
    }

    //Metode til at håndtere når der trykkes logud
    public void signoutHandler() {
        newFxml("login.fxml");
    }

    //Metode til at håndtere når der trykkes back
    public void backHandler() {
        newFxml("core.fxml");
    }
}
