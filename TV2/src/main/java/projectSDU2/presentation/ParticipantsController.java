package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ParticipantsController extends Controller {

    //FXML Attributter
    @FXML
    private Label labelStatusAddEdit;
    @FXML
    private TextField idFieldParticipant;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView participantsList;
    @FXML
    private Button addParticipantButton;
    @FXML
    private Button deleteParticipantButton;
    @FXML
    private Button editParticipantButton;
    @FXML
    private Label participantsLabelStatus;
    @FXML
    private DialogPane addParticipantpane;
    @FXML
    private Label descLabel;
    @FXML
    private TextField nameFieldParticipant;
    @FXML
    private TextField phoneFieldParticipant;
    @FXML
    private TextField emailFieldParticipant;
    @FXML
    private PasswordField passwordFieldParticipant;

    //Overrided initialize der køres hver gang fxml filen loades
    @Override
    public void initialize() {
        participantsList.setItems(FXCollections.observableArrayList(getDomainI().getParticipants())); //Sætter listen til alle participants
        addParticipantpane.setDisable(true);
        addParticipantpane.setVisible(false);
    }

    //Håndterer når der klikkes add participant
    public void addParticipantHandler() {
        addParticipantpane.setVisible(true);
        addParticipantpane.setDisable(false);
        descLabel.setText("Add participant");
        idFieldParticipant.setText("ID will be given after");
    }

    //Håndterer når der klikkes edit participant
    public void editParticipantHandler() {
        if (participantsList.getSelectionModel().getSelectedItem() == null) { //Der er ikke valgt en participant
            participantsLabelStatus.setText("Select participant");
        } else {
            participantsLabelStatus.setText("");
            addParticipantpane.setVisible(true);
            addParticipantpane.setDisable(false);
            descLabel.setText("Edit participant");
            idFieldParticipant.setText(getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getId() + "");
            nameFieldParticipant.setText(getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getName());
            phoneFieldParticipant.setText(getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getPhone() + "");
            emailFieldParticipant.setText(getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getEmail());
            passwordFieldParticipant.setText(getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getPassword());
        }//Henter alle felter for den valgte participant
    }

    //Håndterer når der trykkes finish
    public void finishHandler() {
        if (emailFieldParticipant.getText().isBlank() || passwordFieldParticipant.getText().isBlank()
                || nameFieldParticipant.getText().isBlank() || phoneFieldParticipant.getText().isBlank()) { //En eller flere af felterne er blanke
            labelStatusAddEdit.setText("One or more fields are blank");
        } else {
            try {
                if (Integer.parseInt(phoneFieldParticipant.getText()) < 10000000 || Integer.parseInt(phoneFieldParticipant.getText()) > 99999999) {
                    labelStatusAddEdit.setText("Phone must be an integer with 8 figures"); //Skal være telefonnummer
                } else {
                    if (descLabel.getText().equals("Edit participant")) {
                        //edit participant
                        int oid = getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getId();
                        String name = nameFieldParticipant.getText();
                        int phone = Integer.parseInt(phoneFieldParticipant.getText());
                        String email = emailFieldParticipant.getText();
                        String password = passwordFieldParticipant.getText();
                        getDomainI().editPerson(oid, name, phone, email, password); //Ændrer
                    } else {
                        //add participant
                        String name = nameFieldParticipant.getText();
                        int phone = Integer.parseInt(phoneFieldParticipant.getText());
                        String email = emailFieldParticipant.getText();
                        String password = passwordFieldParticipant.getText();
                        getDomainI().addParticipant(name, phone, email, password); //Tilføjer
                    }
                    resetFields(); //Nulstiller felter
                    initialize(); //Kalder initialize
                }
            } catch (NumberFormatException e) {
                labelStatusAddEdit.setText("Error: Phone needs to be an integer"); //Telefon er ikke en integer
            }
        }
    }

    //Nulstiller alle felter
    private void resetFields() {
        nameFieldParticipant.setText("");
        phoneFieldParticipant.setText("");
        emailFieldParticipant.setText("");
        passwordFieldParticipant.setText("");
        participantsLabelStatus.setText("");
        labelStatusAddEdit.setText("");
    }

    //Håndterer når der trykkes cancel
    public void cancelHandler() {
        resetFields();
        addParticipantpane.setDisable(true);
        addParticipantpane.setVisible(false);
    }

    //Håndterer når der trykkes delete
    public void deleteHandler() {
        if (participantsList.getSelectionModel().getSelectedItem() == null) { //Participant ikke valgt
            participantsLabelStatus.setText("Select participant");
        } else {
            getDomainI().deletePerson(getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getId()); //Sletter participant
            participantsList.setItems(FXCollections.observableArrayList(getDomainI().getParticipants())); //Opdaterer listen med participants
        }
    }

    //Håndterer når der klikkes søg
    public void searchHandler() {
        participantsList.setItems(FXCollections.observableArrayList(getDomainI().searchParticipants(searchField.getText()))); //Søger
    }
}
