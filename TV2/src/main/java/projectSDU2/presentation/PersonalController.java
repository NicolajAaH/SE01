package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;

public class PersonalController extends Controller{

    @FXML
    private ListView productionsList;
    @FXML
    private TextField idField;
    @FXML
    private Button updateButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField companyField;
    @FXML
    private Label updateLabel;
    @FXML
    private Label productionLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(){
        if(getDomainI().findPerson(email).getType().equals("producer")){
            productionsList.setItems(FXCollections.observableArrayList(getDomainI().findWhereProducer(getDomainI().findPerson(email).getId())));
        }else{
            productionsList.setVisible(false);
            productionsList.setDisable(true);
            productionLabel.setVisible(false);
            productionLabel.setDisable(true);
        }
            nameField.setText(getDomainI().findPerson(email).getName());
            phoneField.setText("" + getDomainI().findPerson(email).getPhone());
            emailField.setText(getDomainI().findPerson(email).getEmail());
            password.setText(getDomainI().findPerson(email).getPassword());
            idField.setText(getDomainI().findPerson(email).getId() + "");
    }

    public void updateHandler(){
        if(emailField.getText().isBlank() || password.getText().isBlank() || nameField.getText().isBlank() || phoneField.getText().isBlank()){
            updateLabel.setText("One or more fields are blank");
        }else {
            try {
                if (getDomainI().findPerson(emailField.getText()) == null || email.equals(emailField.getText())) {
                    int phone = Integer.parseInt(phoneField.getText());
                    getDomainI().editPerson(getDomainI().findPerson(email).getId(), nameField.getText(), phone,
                            emailField.getText(), password.getText(), getDomainI().findPerson(email).getType());
                    updateLabel.setText("Update finished");
                    email = emailField.getText();
                    getDomainI().runSetup();
                } else {
                    updateLabel.setText("Email already in use!");
                }
            } catch (NumberFormatException e) {
                updateLabel.setText("Phone must be an integer");
            }
        }
    }

}
