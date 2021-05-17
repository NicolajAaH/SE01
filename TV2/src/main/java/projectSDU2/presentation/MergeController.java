package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MergeController extends Controller{

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

    String type = "";

    @Override
    public void initialize() {
        mergePersonsList.setItems(FXCollections.observableArrayList(getDomainI().getPersons()));
    }

    public void chooseHandler1(){
        idPerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getId() + "");
        namePerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getName());
        phonePerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getPhone() + "");
        emailPerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getEmail());
        passwordPerson1.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getPassword());
        type = getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getType();
    }

    public void chooseHandler2(){
        idPerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getId() + "");
        namePerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getName());
        phonePerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getPhone() + "");
        emailPerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getEmail());
        passwordPerson2.setText(getDomainI().castToPerson(mergePersonsList.getSelectionModel().getSelectedItem()).getPassword());
    }

    public void mergeCheckHandler(){
        idMerged.setText("Will be given after");
        nameMerged.setText(namePerson1.getText());
        phoneMerged.setText(phonePerson1.getText());
        emailMerged.setText(emailPerson1.getText());
        passwordMerged.setText(passwordPerson1.getText());
    }

    public void mergeHandler(){
        getDomainI().merge(Integer.parseInt(idPerson1.getText()), Integer.parseInt(idPerson2.getText()), nameMerged.getText(), Integer.parseInt(phoneMerged.getText()),
                emailMerged.getText(), passwordMerged.getText(), type);
        resetFields();
    }

    private void resetFields(){
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
        initialize();
    }



}
