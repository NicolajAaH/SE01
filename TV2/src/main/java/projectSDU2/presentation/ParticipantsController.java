package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ParticipantsController extends Controller{

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

    @Override
    public void initialize(){
        participantsList.setItems(FXCollections.observableArrayList(getDomainI().getParticipants()));
        addParticipantpane.setDisable(true);
        addParticipantpane.setVisible(false);
    }

    public void addParticipantHandler() {
        addParticipantpane.setVisible(true);
        addParticipantpane.setDisable(false);
        descLabel.setText("Add participant");
    }

    public void editParticipantHandler() {
        if(participantsList.getSelectionModel().getSelectedItem() == null){
            participantsLabelStatus.setText("Select producer");
        }else {
            participantsLabelStatus.setText("");
            addParticipantpane.setVisible(true);
            addParticipantpane.setDisable(false);
            descLabel.setText("Edit participant");
        }
    }

    public void finishHandler() {
        if(descLabel.getText().equals("Edit participant")){
            //edit
            int oid = getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getId();
            String name = nameFieldParticipant.getText();
            int phone = Integer.parseInt(phoneFieldParticipant.getText());
            String email = emailFieldParticipant.getText();
            String password = passwordFieldParticipant.getText();
            getDomainI().editPerson(oid, name, phone, email, password);
        }else{
            //add
            String name = nameFieldParticipant.getText();
            int phone = Integer.parseInt(phoneFieldParticipant.getText());
            String email = emailFieldParticipant.getText();
            String password = passwordFieldParticipant.getText();
            getDomainI().addParticipant(name, phone, email, password);
        }
        resetFields();
        initialize();
    }

    private void resetFields() {
        nameFieldParticipant.setText("");
        phoneFieldParticipant.setText("");
        emailFieldParticipant.setText("");
        passwordFieldParticipant.setText("");
    }

    public void cancelHandler() {
        resetFields();
        addParticipantpane.setDisable(true);
        addParticipantpane.setVisible(false);
    }

    public void deleteHandler(){
        getDomainI().deletePerson(getDomainI().castToPerson(participantsList.getSelectionModel().getSelectedItem()).getId());
        participantsList.setItems(FXCollections.observableArrayList(getDomainI().getParticipants()));
    }
}
