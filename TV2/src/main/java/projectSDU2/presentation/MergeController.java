package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MergeController extends Controller {

    //FXML Attributter
    @FXML
    private Label labelStatus;
    @FXML
    private ListView mergePersonsList;
    @FXML
    private Button mergePerson1Button;
    @FXML
    private Button mergePerson2Button;
    @FXML
    private TextField namePerson1;
    @FXML
    private TextField phonePerson1;
    @FXML
    private TextField emailPerson1;
    @FXML
    private PasswordField passwordPerson1;
    @FXML
    private TextField idPerson1;
    @FXML
    private TextField namePerson2;
    @FXML
    private TextField phonePerson2;
    @FXML
    private TextField emailPerson2;
    @FXML
    private PasswordField passwordPerson2;
    @FXML
    private TextField idPerson2;
    @FXML
    private TextField nameMerged;
    @FXML
    private TextField phoneMerged;
    @FXML
    private TextField emailMerged;
    @FXML
    private PasswordField passwordMerged;
    @FXML
    private TextField idMerged;
    @FXML
    private Button mergeButton;

    //Attribut
    String type = "";

    //Overrided initialize metode, der køres hver gang der skiftes til merge fxml-filen
    @Override
    public void initialize() {
        mergePersonsList.setItems(FXCollections.observableArrayList(getDomainI().getPersons()));
        mergeButton.setDisable(true);
    }

    //Metode til at håndtere når der trykkes på choose 1
    public void chooseHandler1() {
        try {
            if (mergePersonsList.getSelectionModel().getSelectedItem() == null) { //Ikke valgt person 1
                labelStatus.setText("Choose person 1");
            } else {
                if (!idPerson2.getText().equals("") && (mergePersonsList.getSelectionModel().getSelectedItem() == null
                        || getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getId() == Integer.parseInt(idPerson2.getText()))) {
                    if (getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getId() == Integer.parseInt(idPerson2.getText())) {
                        labelStatus.setText("Cannot merge same person"); //Personen er allerede valgt i en af dem
                    } else {
                        labelStatus.setText("Select person 2"); //Ikke valgt i listen
                    }
                } else { //Udfylder felter for person 1
                    idPerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getId() + "");
                    namePerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getName());
                    phonePerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getPhone() + "");
                    emailPerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getEmail());
                    passwordPerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getPassword());
                    type = getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getType();
                    labelStatus.setText("");
                }
            }
        } catch (NumberFormatException e) {
            labelStatus.setText("Choose person 2"); //Person 2 skal vælges
        }
    }

    //Metode til at håndtere når der trykkes på choose 2. Logik meget ens fra forrige metode. Se kommentarer der
    public void chooseHandler2() {
        try {
            if (mergePersonsList.getSelectionModel().getSelectedItem() == null) {
                labelStatus.setText("Choose person 2");
            } else {
                if (!idPerson1.getText().equals("") && (mergePersonsList.getSelectionModel().getSelectedItem() == null || getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getId() == Integer.parseInt(idPerson1.getText()))) {
                    if (getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getId() == Integer.parseInt(idPerson1.getText())) {
                        labelStatus.setText("Cannot merge same person");
                    } else {
                        labelStatus.setText("Select person 2");
                    }
                } else {
                    idPerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getId() + "");
                    namePerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getName());
                    phonePerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getPhone() + "");
                    emailPerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getEmail());
                    passwordPerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getPassword());
                    labelStatus.setText("");
                }
            }
        } catch (NumberFormatException e) {
            labelStatus.setText("Choose person 1");
        }
    }

    //Metode til at se merged person
    public void mergeCheckHandler() {
        if (idPerson1.getText().isBlank() || idPerson2.getText().isBlank()) { //Hvis der ikke er valgt begge personer
            labelStatus.setText("Both persons must be selected!");
        } else {
            if (getDomainI().findPerson(Integer.parseInt(idPerson1.getText())).getType().equals(getDomainI().findPerson(Integer.parseInt(idPerson2.getText())).getType())) {
                idMerged.setText("Will be given after"); //Sætter felter
                nameMerged.setText(namePerson1.getText());
                phoneMerged.setText(phonePerson1.getText());
                emailMerged.setText(emailPerson1.getText());
                passwordMerged.setText(passwordPerson1.getText());
                mergeButton.setDisable(false);
                labelStatus.setText("");
            } else {
                labelStatus.setText("Persons must be of same type"); //Hvis personerne har forskellig type
            }
        }
    }

    //Håndterer merge knappen og merger personerne
    public void mergeHandler() {
        if (nameMerged.getText().isBlank() || phoneMerged.getText().isBlank() || emailMerged.getText().isBlank() || passwordMerged.getText().isBlank()) {
            labelStatus.setText("One or more fields are blank"); //Hvis nogle af felterne er blanke
        } else {
            try {
                int phone = Integer.parseInt(phoneMerged.getText());
                getDomainI().merge(Integer.parseInt(idPerson1.getText()), Integer.parseInt(idPerson2.getText()), nameMerged.getText(), phone,
                        emailMerged.getText(), passwordMerged.getText(), type); //Merger dem
                resetFields(); //Nulstiller felterne
                labelStatus.setText("Merge complete");
            } catch (NumberFormatException e) {
                labelStatus.setText("Phone must be an integer"); //Hvis telefonnummer ikke er en integer
            }
        }
    }

    //Nulstiller felter så de er tomme
    private void resetFields() {
        idPerson1.setText("");
        namePerson1.setText("");
        phonePerson1.setText("");
        emailPerson1.setText("");
        passwordPerson1.setText("");
        idPerson2.setText("");
        namePerson2.setText("");
        phonePerson2.setText("");
        emailPerson2.setText("");
        passwordPerson2.setText("");
        nameMerged.setText("");
        phoneMerged.setText("");
        emailMerged.setText("");
        passwordMerged.setText("");
        initialize(); //Kalder initialize
    }
}
