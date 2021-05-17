package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

public class ProducerController extends Controller{

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

    @Override
    public void initialize(){
        producersList.setItems(FXCollections.observableArrayList(getDomainI().getProducers()));
        addProducerpane.setDisable(true);
        addProducerpane.setVisible(false);
    }

    private void resetFields(){
        ObservableList<Object> empty = FXCollections.observableArrayList();
        nameFieldProducer.setText("");
        phoneFieldProducer.setText("");
        emailFieldProducer.setText("");
        passwordFieldProducer.setText("");
        productionsListProducer.setItems(empty);
    }

    public void addProducerHandler(){
        addProducerpane.setVisible(true);
        addProducerpane.setDisable(false);
        descLabel.setText("Add producer");
    }

    public void editProducerHandler(){
        if(producersList.getSelectionModel().getSelectedItem() == null){
            producersLabelStatus.setText("Select producer");
        }else {
            producersLabelStatus.setText("");
            addProducerpane.setVisible(true);
            addProducerpane.setDisable(false);
            descLabel.setText("Edit producer");
            idFieldProducer.setText(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getId() +"");
            nameFieldProducer.setText(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getName());
            phoneFieldProducer.setText(""+getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getPhone());
            emailFieldProducer.setText(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getEmail());
            passwordFieldProducer.setText(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getPassword());
            productionsListProducer.setItems(FXCollections.observableArrayList(getDomainI().findWhereProducer(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getId())));
        }
    }

    public void cancelHandler(){
        resetFields();
        addProducerpane.setDisable(true);
        addProducerpane.setVisible(false);
    }

    public void finishHandler(){
        //try catch her
        if(descLabel.getText().equals("Edit producer")){
            int oid = getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getId();
            String name = nameFieldProducer.getText();
            int phone = Integer.parseInt(phoneFieldProducer.getText());
            String email = emailFieldProducer.getText();
            String password = passwordFieldProducer.getText();
            getDomainI().editPerson(oid, name, phone, email, password);
        }else{
            //add
            String name = nameFieldProducer.getText();
            int phone = Integer.parseInt(phoneFieldProducer.getText());
            String email = emailFieldProducer.getText();
            String password = passwordFieldProducer.getText();
            getDomainI().addProducer(name, phone, email, password);
        }
        resetFields();
        initialize();
    }

    public void deleteHandler(){
        getDomainI().deletePerson(getDomainI().castToPerson(producersList.getSelectionModel().getSelectedItem()).getId());
        producersList.setItems(FXCollections.observableArrayList(getDomainI().getProducers()));
    }

    public void searchHandler(){
        producersList.setItems(FXCollections.observableArrayList(getDomainI().searchProducers(searchField.getText())));
    }
}
