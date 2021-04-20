package projectSDU2.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import projectSDU2.domain.user.Producer;

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
    private ListView<String> productionsList;
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
            nameField.setText(getDomainI().findAccount(email).getPerson().getName());
            phoneField.setText("" + getDomainI().findAccount(email).getPerson().getPhone());
            emailField.setText(getDomainI().findAccount(email).getPerson().getEmail());
            password.setText(getDomainI().findAccount(email).getPassword());
        }
        if(type.equals("participant")){
            companyField.setDisable(true);
            companyField.setVisible(false);
            productionsList.setDisable(true);
            productionsList.setVisible(false);
            productionLabel.setVisible(false);
            companyLabel.setVisible(false);
        }else if(type.equals("producer")){
            Producer producer = (Producer) getDomainI().findAccount(email).getPerson();
            companyField.setText(producer.getCompany());
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
