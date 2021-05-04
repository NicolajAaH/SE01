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
    private ListView<Object> productionsList;
    @FXML
    private Label updateLabel;
    @FXML
    private Label productionLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label companyLabel;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(){
        if(!type.equals("systemadministrator")) {
            nameField.setText(getDomainI().findPerson(email).getName());
            phoneField.setText("" + getDomainI().findPerson(email).getPhone());
            emailField.setText(getDomainI().findPerson(email).getEmail());
            password.setText(getDomainI().findPerson(email).getPassword());
        }
        if(type.equals("participant")){
            companyField.setDisable(true);
            companyField.setVisible(false);
            productionsList.setDisable(true);
            productionsList.setVisible(false);
            productionLabel.setVisible(false);
            companyLabel.setVisible(false);
        }else if(type.equals("producer")){
            companyField.setText(getDomainI().findPerson(email).getEmail());
            //Object[] productionString = getDomainI().findPerson(email).getProductions().toArray();
            //ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(productionString));
            //productionsList.setItems(strings);
        }else if(type.equals("systemadministrator")){
            nameField.setDisable(true);
            nameField.setVisible(false);
            phoneField.setDisable(true);
            phoneField.setVisible(false);
            companyField.setDisable(true);
            companyField.setVisible(false);
            productionsList.setDisable(true);
            productionsList.setVisible(false);
            productionLabel.setVisible(false);
            companyLabel.setVisible(false);
            nameLabel.setVisible(false);
            phoneLabel.setVisible(false);
            emailField.setText(email);
            password.setText(accountPassword);
        }
    }

    public void updateHandler(){
        if(type.equals("systemadministrator")){
            //TODO
        }
    }

}
