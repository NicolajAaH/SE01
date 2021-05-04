package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

public class ProducerController extends Controller{

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
        //Object[] producersString = getDomainI().getProducers().toArray();
        //ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(producersString));
        //producersList.setItems(strings);
        addProducerpane.setDisable(true);
        addProducerpane.setVisible(false);
    }

    private void resetFields(){
        ObservableList<Object> empty = FXCollections.observableArrayList();
        nameFieldProducer.setText("");
        phoneFieldProducer.setText("");
        emailFieldProducer.setText("");
        companyFieldProducer.setText("");
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
            String selected = producersList.getSelectionModel().getSelectedItem().toString();
            String[] split = selected.split("\t");
            String email = split[2].substring(7);
            addProducerpane.setVisible(true);
            addProducerpane.setDisable(false);
            descLabel.setText("Edit producer");
            nameFieldProducer.setText(getDomainI().findPerson(email).getName());
            phoneFieldProducer.setText("" + getDomainI().findPerson(email).getPhone());
            emailFieldProducer.setText(getDomainI().findPerson(email).getEmail());
            //Object[] productionString = getDomainI().findPerson(email).getProductions().toArray();
            //ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(productionString));
            //productionsListProducer.setItems(strings);
        }
    }

    public void cancelHandler(){
        resetFields();
        addProducerpane.setDisable(true);
        addProducerpane.setVisible(false);
    }

    public void finishHandler(){
        addProducerpane.setDisable(true);
        addProducerpane.setVisible(false);

        //GØR NOGET FØRST.
        resetFields();
    }
}
