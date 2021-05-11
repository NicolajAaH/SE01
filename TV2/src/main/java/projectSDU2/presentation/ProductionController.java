package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Arrays;

public class ProductionController extends Controller{

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

    @Override
    public void initialize(){
        productionsList.setItems(FXCollections.observableArrayList(getDomainI().getProductions()));
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);

        for (int i = 0; i < getDomainI().getRoles().size(); i++) {
            rolesMenu.getItems().add(getDomainI().getRoles().get(i));
        }
    }

    public void cancelHandler1(){
        //resetFields();
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
    }

    public void finishHandler1(){
        if(descLabel.getText().equals("Add production")){
            getDomainI().addProduction(creditsProductionList.getItems(), nameFieldProduction.getText(), companyFieldProduction.getText());
        }else{
            //edit
            getDomainI().editProduction(getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).getProductionID(), nameFieldProduction.getText(),
                    companyFieldProduction.getText(), credits, validatedProduction.isSelected(), sentProduction.isSelected());
        }

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
        idFieldProduction.setText("");
        nameFieldProduction.setText("");
        companyFieldProduction.setText("");
        ObservableList<Object> strings = FXCollections.observableArrayList();
        creditsProductionList.setItems(strings);
    }

    public void addCreditHandler(){
        descLabelCredit.setText("Add credit");
        roles.removeAll(roles);
        idFieldCredit.setText("");
        idFieldCreditPerson.setText("");
        nameFieldPerson.setText("");
        phoneFieldPerson.setText("");
        emailFieldPerson.setText("");
        addCreditProduction.setDisable(false);
        addCreditProduction.setVisible(true);
    }

    public void editProductionHandler(){ //TODO
        if(productionsList.getSelectionModel().getSelectedItem() == null){
            productionsLabelStatus.setText("Select producer");
        }else {
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
            Object[] creditsString = getDomainI().findProduction(id).getCredits().toArray();
            ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(creditsString));
            creditsProductionList.setItems(strings);
            for (Object object : creditsString){
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
            productionsList.setItems(FXCollections.observableArrayList(getDomainI().getProductions()));
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
            getDomainI().deleteCredit(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getCreditID());
            Object[] creditsString = getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).getCredits().toArray();
            ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(creditsString));
            creditsProductionList.setItems(strings);
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

}
