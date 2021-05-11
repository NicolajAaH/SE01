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
        getDomainI().editPerson(getDomainI().findPerson(email).getId(), nameField.getText(), Integer.parseInt(phoneField.getText()),
                emailField.getText(), password.getText(), getDomainI().findPerson(email).getType());
        updateLabel.setText("Update finished");
    }

}
