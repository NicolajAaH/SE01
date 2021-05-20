package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PersonalController extends Controller {

    //FXML Attributter
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

    //Overrided initialize der køres hver gang fxml filen personal loades
    @Override
    public void initialize() {
        if (getDomainI().findPerson(email).getType().equals("producer")) { //Hvis en producer er logget ind, så skal der være en liste med produktioner
            productionsList.setItems(FXCollections.observableArrayList(getDomainI().findWhereProducer(getDomainI().findPerson(email).getId())));
        } else { //Ikke producer
            productionsList.setVisible(false);
            productionsList.setDisable(true);
            productionLabel.setVisible(false);
            productionLabel.setDisable(true);
        }
        nameField.setText(getDomainI().findPerson(email).getName()); //Sætter felter
        phoneField.setText("" + getDomainI().findPerson(email).getPhone());
        emailField.setText(getDomainI().findPerson(email).getEmail());
        password.setText(getDomainI().findPerson(email).getPassword());
        idField.setText(getDomainI().findPerson(email).getId() + "");
    }

    //Håndterer når der klikkes update
    public void updateHandler() {
        if (emailField.getText().isBlank() || password.getText().isBlank() || nameField.getText().isBlank() || phoneField.getText().isBlank()) {
            updateLabel.setText("One or more fields are blank"); //Felter er blanke
        } else {
            try {
                if (getDomainI().findPerson(emailField.getText()) == null || email.equals(emailField.getText())) {//Sikrer sig at der ikke kan gemmes en allerede mail i brug
                    int phone = Integer.parseInt(phoneField.getText());
                    getDomainI().editPerson(getDomainI().findPerson(email).getId(), nameField.getText(), phone,
                            emailField.getText(), password.getText(), getDomainI().findPerson(email).getType()); //Ændrer person
                    updateLabel.setText("Update finished");
                    email = emailField.getText(); //Sætter email til ny
                    getDomainI().runSetup(); //Opdaterer listerne i systemet
                } else {
                    updateLabel.setText("Email already in use!"); //Email allerede i brug, ikke tilladt pga. UNIQUE constraint i DB
                }
            } catch (NumberFormatException e) {
                updateLabel.setText("Phone must be an integer"); //Telefonnummer er ikke integer
            }
        }
    }
}
