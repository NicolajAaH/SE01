package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;

public class PersonalController extends Controller{

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
            nameField.setText(getDomainI().findPerson(email).getName());
            phoneField.setText("" + getDomainI().findPerson(email).getPhone());
            emailField.setText(getDomainI().findPerson(email).getEmail());
            password.setText(getDomainI().findPerson(email).getPassword());

        //else{
            //Object[] productionString = getDomainI().findPerson(email).getProductions().toArray();
            //ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(productionString));
            //productionsList.setItems(strings);
        //}
    }

    public void updateHandler(){
        getDomainI().editPerson(getDomainI().findPerson(email).getId(), nameField.getText(), Integer.parseInt(phoneField.getText()),
                emailField.getText(), password.getText(), getDomainI().findPerson(email).getType());
        updateLabel.setText("Update finished");
    }

}
