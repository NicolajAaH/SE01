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

    private static DomainI domainI = new DomainConnect();

    @FXML
    private Button login;
    @FXML
    private Label status;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;

    static String type = "";

    static String email;

    static String accountPassword;

    static int id = -1;

    public static DomainI getDomainI() {
        return domainI;
    }

    public void initialize(){
        domainI.runSetup();
    }

    @FXML
    private void loginHandler(){
        if(domainI.authorize(emailField.getText(), passwordField.getText())){ //tjek om det er producer, admin osv. og giv næste scene afhængig af det
            type = domainI.findPerson(emailField.getText()).getType();
            email = emailField.getText();
            accountPassword = passwordField.getText();
            id = domainI.findPerson(emailField.getText()).getId();
            newFxml("core.fxml");
        }
        else{
            status.setText("Incorrect login information.");
        }
    }

    public void newFxml(String fileName){
        Parent newRoot = null;
        try {
            newRoot = runGUI.getFxmlLoader().load(getClass().getClassLoader().getResource(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        runGUI.getStage().setScene(new Scene(newRoot));
        runGUI.getStage().show();
    }

    public void signoutHandler(){
        newFxml("login.fxml");
    }

    public void backHandler(){
        newFxml("core.fxml");
    }


}
