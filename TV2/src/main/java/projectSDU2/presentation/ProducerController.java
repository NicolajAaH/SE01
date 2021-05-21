package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProducerController extends Controller {

    //FXML Attributter
    @FXML
    private Label statusLabelProducer;
    @FXML
    private TextField idFieldProducer;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView producersList;
    @FXML
    private Label descLabel;
    @FXML
    private TextField nameFieldProducer;
    @FXML
    private TextField phoneFieldProducer;
    @FXML
    private TextField emailFieldProducer;
    @FXML
    private PasswordField passwordFieldProducer;
    @FXML
    private TextField companyFieldProducer;
    @FXML
    private ListView productionsListProducer;
    @FXML
    private TextField productionFieldProducer;
    @FXML
    private Button productionButton;
    @FXML
    private Button finishButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addProducerButton;
    @FXML
    private Button deleteProducerButton;
    @FXML
    private Button editProducerButton;
    @FXML
    private Label producersLabelStatus;
    @FXML
    private DialogPane addProducerpane;
    @FXML
    private Button deleteProd;

    //Overrided initialize der køres hver gang fxml filen producer loades
    @Override
    public void initialize() {
        producersList.setItems(FXCollections.observableArrayList(getDomainI().getProducers())); //Henter alle producers til listen
        addProducerpane.setDisable(true);
        addProducerpane.setVisible(false);
    }

    //Nulstiller felterne så de er tomme alle sammen
    private void resetFields() {
        ObservableList<Object> empty = FXCollections.observableArrayList();
        nameFieldProducer.setText("");
        phoneFieldProducer.setText("");
        emailFieldProducer.setText("");
        passwordFieldProducer.setText("");
        productionsListProducer.setItems(empty);
        producersLabelStatus.setText("");
        statusLabelProducer.setText("");
    }

    //Håndterer klik på add producer
    public void addProducerHandler() {
        addProducerpane.setVisible(true);
        addProducerpane.setDisable(false);
        descLabel.setText("Add producer");
        idFieldProducer.setText("ID will be given after");
    }

    //Håndterer klik på edit producer
    public void editProducerHandler() {
        if (producersList.getSelectionModel().getSelectedItem() == null) { //Producer ikke valgt
            producersLabelStatus.setText("Select producer");
        } else {
            producersLabelStatus.setText("");
            addProducerpane.setVisible(true);
            addProducerpane.setDisable(false);
            descLabel.setText("Edit producer");
            idFieldProducer.setText(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getId() + "");
            nameFieldProducer.setText(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getName());
            phoneFieldProducer.setText("" + getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getPhone());
            emailFieldProducer.setText(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getEmail());
            passwordFieldProducer.setText(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getPassword());
            productionsListProducer.setItems(FXCollections.observableArrayList(getDomainI().
                    findWhereProducer(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getId()))); //Productions for en producer sættes
        }
    }

    //Håndterer når der trykkes cancel
    public void cancelHandler() {
        resetFields(); //Nulstiller felterne
        addProducerpane.setDisable(true);
        addProducerpane.setVisible(false);
    }

    //Håndterer når der trykkes finish
    public void finishHandler() {
        if (nameFieldProducer.getText().isBlank() || phoneFieldProducer.getText().isBlank()
                || emailFieldProducer.getText().isBlank() || passwordFieldProducer.getText().isBlank()) { //et eller flere felter er tomme
            statusLabelProducer.setText("One or more fields are blank");
        } else {
            try {
                if (Integer.parseInt(phoneFieldProducer.getText()) < 10000000 || Integer.parseInt(phoneFieldProducer.getText()) > 99999999) {
                    statusLabelProducer.setText("Phone must be an integer with 8 figures"); //Phone skal være et telefonnummer
                } else {
                    if (descLabel.getText().equals("Edit producer")) { //Edit producer
                        int oid = getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getId();
                        String name = nameFieldProducer.getText();
                        int phone = Integer.parseInt(phoneFieldProducer.getText());
                        String email = emailFieldProducer.getText();
                        String password = passwordFieldProducer.getText();
                        getDomainI().editPerson(oid, name, phone, email, password);
                    } else {
                        //Add producer
                        String name = nameFieldProducer.getText();
                        int phone = Integer.parseInt(phoneFieldProducer.getText());
                        String email = emailFieldProducer.getText();
                        String password = passwordFieldProducer.getText();
                        getDomainI().addProducer(name, phone, email, password);
                    }
                    resetFields();
                    initialize();
                }
            } catch (NumberFormatException e) {
                statusLabelProducer.setText("Phone must be an integer"); //telefonnummer er ikke en integer
            }
        }
    }

    //Håndterer når der trykkes delete producer
    public void deleteHandler() {
        if (producersList.getSelectionModel().getSelectedItem() == null) { //Producer ikke valgt
            producersLabelStatus.setText("Select producer");
        } else {
            getDomainI().deletePerson(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getId()); //sletter
            producersList.setItems(FXCollections.observableArrayList(getDomainI().getProducers())); //Opdaterer listen
        }
    }

    //Håndterer når der trykkes søg
    public void searchHandler() {
        producersList.setItems(FXCollections.observableArrayList(getDomainI().searchProducers(searchField.getText())));
    }
}
