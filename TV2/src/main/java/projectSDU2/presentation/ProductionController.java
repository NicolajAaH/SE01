package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductionController extends Controller{

    @FXML
    private ListView personsListSearch;
    @FXML
    private Button searchPersonButton;
    @FXML
    private TextField personSearchField;
    @FXML
    private Button chooseButton;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox rolesMenu;
    @FXML
    private TextField idFieldProduction;
    @FXML
    private ListView creditsProductionList;
    @FXML
    private CheckBox validatedProduction;
    @FXML
    private CheckBox sentProduction;
    @FXML
    private Button addCreditButton;
    @FXML
    private Button editCreditButton;
    @FXML
    private Button deleteCreditButton;
    @FXML
    private DialogPane addCreditProduction;
    @FXML
    private Label descLabelCredit;
    @FXML
    private TextField nameFieldPerson;
    @FXML
    private Button finishButton1;
    @FXML
    private Button cancelButton1;
    @FXML
    private TextField idFieldCredit;
    @FXML
    private ListView rolesList;
    @FXML
    private Button addRoleButton;
    @FXML
    private Button deleteRoleButton;
    @FXML
    private TextField idFieldCreditPerson;
    @FXML
    private TextField nameFieldPerson1;
    @FXML
    private TextField phoneFieldPerson;
    @FXML
    private TextField emailFieldPerson;
    @FXML
    private ListView productionsList;
    @FXML
    private Button addProductionButton;
    @FXML
    private Button deleteProductionButton;
    @FXML
    private Button editProductionButton;
    @FXML
    private Label productionsLabelStatus;
    @FXML
    private DialogPane addProductionpane;
    @FXML
    private Label descLabel;
    @FXML
    private TextField nameFieldProduction;
    @FXML
    private TextField companyFieldProduction;
    @FXML
    private Button cancelButton;
    @FXML
    private Button finishButton;

    ObservableList<Object> roles = FXCollections.observableArrayList();
    ObservableList<Object> credits = FXCollections.observableArrayList();

    ArrayList<Object> tempDeletedCredits = new ArrayList<>();

    @Override
    public void initialize(){
        if(type.equals("producer")){
            productionsList.setItems(FXCollections.observableArrayList(getDomainI().findWhereProducer(id)));
        }else {
            productionsList.setItems(FXCollections.observableArrayList(getDomainI().getProductions()));
        }
        personsListSearch.setItems(FXCollections.observableArrayList(getDomainI().getPersons()));
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);

        for (int i = 0; i < getDomainI().getRoles().size(); i++) {
            if(!rolesMenu.getItems().contains(getDomainI().getRoles().get(i))){
                rolesMenu.getItems().add(getDomainI().getRoles().get(i));
            }
        }
    }

    public void cancelHandler1(){
        //resetFields();
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
        finishButton.setDisable(false);
        addCreditButton.setDisable(false);
        editCreditButton.setDisable(false);
        deleteCreditButton.setDisable(false);
        nameFieldProduction.setEditable(true);
        companyFieldProduction.setEditable(true);
        credits.removeAll(credits);
    }

    public void finishHandler1(){
        if(descLabel.getText().equals("Add production")){
            getDomainI().addProduction(creditsProductionList.getItems(), nameFieldProduction.getText(), companyFieldProduction.getText());
        }else{
            //edit
            for (Object object : tempDeletedCredits){
                getDomainI().deleteCredit(getDomainI().castToCredit(object).getCreditID());
            }
            getDomainI().editProduction(Integer.parseInt(idFieldProduction.getText()), nameFieldProduction.getText(),
                    companyFieldProduction.getText(), credits, validatedProduction.isSelected(), sentProduction.isSelected());
            tempDeletedCredits.removeAll(tempDeletedCredits);
            System.out.println(credits.toString());
        }
        credits.removeAll(credits);
        initialize();
    }

    public void cancelHandler2(){
        //resetFields();
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);
    }

    public void finishHandler2(){
        if(descLabelCredit.getText().equals("Add credit")) {
            credits.add(getDomainI().createCredit(Integer.parseInt(idFieldCreditPerson.getText()), roles));
        }else if(descLabelCredit.getText().equals("Edit credit")){
            //edit
            credits.remove(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()));
            credits.add(getDomainI().createCredit(Integer.parseInt(idFieldCreditPerson.getText()), roles));
            //getDomainI().editCredit(getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).getProductionID(),
              //      Integer.parseInt(idFieldCreditPerson.getText()), roles);
        }

        creditsProductionList.setItems(credits);
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);
    }

    public void addProductionHandler(){
        addProductionpane.setDisable(false);
        addProductionpane.setVisible(true);
        descLabel.setText("Add production");
        idFieldProduction.setText("ID will be given auto");
        nameFieldProduction.setText("");
        companyFieldProduction.setText("");
        validatedProduction.setSelected(false);
        sentProduction.setSelected(false);
        ObservableList<Object> strings = FXCollections.observableArrayList();
        creditsProductionList.setItems(strings);
    }

    public void addCreditHandler(){
        descLabelCredit.setText("Add credit");
        roles.removeAll(roles);
        idFieldCredit.setText("ID will be given auto");
        idFieldCreditPerson.setText("");
        nameFieldPerson.setText("");
        phoneFieldPerson.setText("");
        emailFieldPerson.setText("");
        addCreditProduction.setDisable(false);
        addCreditProduction.setVisible(true);
    }

    public void editProductionHandler(){
        if(productionsList.getSelectionModel().getSelectedItem() == null){
            productionsLabelStatus.setText("Select producer");
        }else {
            tempDeletedCredits.removeAll(tempDeletedCredits);
            if(getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).isStatus()){
                finishButton.setDisable(true);
                addCreditButton.setDisable(true);
                editCreditButton.setDisable(true);
                deleteCreditButton.setDisable(true);
                nameFieldProduction.setEditable(false);
                companyFieldProduction.setEditable(false);
            }
            descLabel.setText("Edit production");
            productionsLabelStatus.setText("");
            credits.removeAll(credits);
            int id = getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).getProductionID();
            addProductionpane.setVisible(true);
            addProductionpane.setDisable(false);
            idFieldProduction.setText(""+getDomainI().findProduction(id).getProductionID());
            nameFieldProduction.setText(""+getDomainI().findProduction(id).getName());
            companyFieldProduction.setText(""+getDomainI().findProduction(id).getCompany());
            validatedProduction.setSelected(getDomainI().findProduction(id).isStatus());
            sentProduction.setSelected(getDomainI().findProduction(id).isSent());
            creditsProductionList.setItems(FXCollections.observableArrayList(getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).getCredits()));
            for (Object object : creditsProductionList.getItems()){
                credits.add(getDomainI().castToCredit(object));
            }
        }
    }

    public void deleteProductionHandler(){
        if(productionsList.getSelectionModel().getSelectedItem() == null){
            productionsLabelStatus.setText("Select production");
        }else {
            productionsLabelStatus.setText("");
            getDomainI().deleteProduction(getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).getProductionID());
            if(type.equals("producer")){
                productionsList.setItems(FXCollections.observableArrayList(getDomainI().findWhereProducer(id)));
            }else {
                productionsList.setItems(FXCollections.observableArrayList(getDomainI().getProductions()));
            }
        }
    }

    public void addRoleHandler(){
        if(!roles.contains(getDomainI().findRole(rolesMenu.getSelectionModel().getSelectedItem().toString()))) {
            roles.add(getDomainI().findRole(rolesMenu.getSelectionModel().getSelectedItem().toString()));
            rolesList.setItems(roles);
        }
    }

    public void deleteRoleHandler(){
        if(roles.contains(getDomainI().findRole(rolesList.getSelectionModel().getSelectedItem().toString()))) {
            roles.remove(rolesList.getSelectionModel().getSelectedIndex());
            rolesList.setItems(roles);
        }
    }

    public void deleteCreditHandler(){
        if(productionsList.getSelectionModel().getSelectedItem() != null){
            tempDeletedCredits.add(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()));
            credits.remove(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()));
            creditsProductionList.setItems(FXCollections.observableArrayList(credits));
        }
    }

    public void editCreditHandler(){
        if(creditsProductionList.getSelectionModel().getSelectedItem() == null){
            productionsLabelStatus.setText("Select credit");
        }else{
            descLabelCredit.setText("Edit credit");
            addCreditProduction.setDisable(false);
            addCreditProduction.setVisible(true);
            idFieldCredit.setText(""+getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getCreditID());
            idFieldCreditPerson.setText(""+getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getPerson().getId());
            nameFieldPerson.setText(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getPerson().getName());
            phoneFieldPerson.setText(""+getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getPerson().getPhone());
            emailFieldPerson.setText(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getPerson().getEmail());
            roles.removeAll(roles); //Reset listen
            roles.addAll(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getRoles());
            rolesList.setItems(roles);
        }
    }

    public void searchHandler(){
        productionsList.setItems(FXCollections.observableArrayList(getDomainI().searchProductions(searchField.getText())));
    }

    public void searchPersonHandler(){
        personsListSearch.setItems(FXCollections.observableArrayList(getDomainI().searchPersons(personSearchField.getText())));
    }

    public void chooseButtonHandler(){
        idFieldCreditPerson.setText(getDomainI().castToPerson(personsListSearch.getSelectionModel().getSelectedItem()).getId() + "");
        nameFieldPerson.setText(getDomainI().castToPerson(personsListSearch.getSelectionModel().getSelectedItem()).getName());
        phoneFieldPerson.setText(getDomainI().castToPerson(personsListSearch.getSelectionModel().getSelectedItem()).getPhone() + "");
        emailFieldPerson.setText(getDomainI().castToPerson(personsListSearch.getSelectionModel().getSelectedItem()).getEmail());
    }

}
