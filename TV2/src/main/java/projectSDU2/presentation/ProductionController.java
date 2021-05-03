package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Arrays;

public class ProductionController extends Controller{

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

    @Override
    public void initialize(){
        Object[] producersString = getDomainI().getProductions().toArray();
        ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(producersString));
        productionsList.setItems(strings);
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);

        for (int i = 0; i < getDomainI().getRoles().length; i++) {
            rolesMenu.getItems().add(getDomainI().getRoles()[i]);
        }
    }

    public void cancelHandler1(){
        //resetFields();
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
    }

    public void finishHandler1(){ //TODO MANGLER ALLE SET
        if(descLabel.getText().equals("Add production")){
            getDomainI().getProductions().add(getDomainI().createProduction(Integer.parseInt(idFieldProduction.getText()), creditsProductionList.getItems(), nameFieldProduction.getText(), companyFieldProduction.getText()));
        }

        Object[] producersString = getDomainI().getProductions().toArray();
        ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(producersString));
        productionsList.setItems(strings);
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
    }

    public void cancelHandler2(){
        //resetFields();
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);
    }

    public void finishHandler2(){
        if(descLabelCredit.getText().equals("Add credit")) {
            if(getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex() + 1) == null){
                //MANGLER NOGET HER
            }else {
                getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex() + 1)
                        .addCredit(getDomainI().createCredit(Integer.parseInt(idFieldCredit.getText()), getDomainI().findPerson("producer@test.dk"), rolesList.getItems()));
            }
        }else if(descLabelCredit.getText().equals("Edit credit")){
            getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex() + 1)
                    .getCredits().get(creditsProductionList.getSelectionModel().getSelectedIndex()).setRoles(rolesList.getItems());
            getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex() + 1)
                    .getCredits().get(creditsProductionList.getSelectionModel().getSelectedIndex()).setPerson(getDomainI().findPerson(Integer.parseInt(idFieldCreditPerson.getText())));
            //GEMMER IKKE ORDENTLIGT!
        }
        Object[] creditsString = getDomainI().findProduction(Integer.parseInt(idFieldProduction.getText())).getCredits().toArray();
        ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(creditsString));
        creditsProductionList.setItems(strings);
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);

        //GØR NOGET FØRST.
        //resetFields();
        //fix at man skal kunne tilføje produktion med krediteringer!!
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
            descLabel.setText("Edit production");
            productionsLabelStatus.setText("");
            String selected = productionsList.getSelectionModel().getSelectedItem().toString();
            //String[] split = selected.split("\t");
            int id = Integer.parseInt(selected); //FIX TAL!!
            addProductionpane.setVisible(true);
            addProductionpane.setDisable(false);
            descLabel.setText("Edit production");
            idFieldProduction.setText(""+getDomainI().findProduction(id).getProductionID());
            nameFieldProduction.setText(""+getDomainI().findProduction(id).getName());
            companyFieldProduction.setText(""+getDomainI().findProduction(id).getCompany());
            validatedProduction.setSelected(getDomainI().findProduction(id).isStatus());
            sentProduction.setSelected(getDomainI().findProduction(id).isSent());
            Object[] creditsString = getDomainI().findProduction(id).getCredits().toArray();
            ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(creditsString));
            creditsProductionList.setItems(strings);
        }
    }

    public void deleteProductionHandler(){
        if(productionsList.getSelectionModel().getSelectedItem() == null){
            productionsLabelStatus.setText("Select production");
        }else {
            productionsLabelStatus.setText("");
            int selected = productionsList.getSelectionModel().getSelectedIndex()+1;
            getDomainI().getProductions().remove(getDomainI().findProduction(selected));
            Object[] productionsString = getDomainI().getProductions().toArray();
            ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(productionsString));
            productionsList.setItems(strings);
        }
    }

    public void addRoleHandler(){
        if(!roles.contains(getDomainI().findRole(rolesMenu.getSelectionModel().getSelectedItem().toString()))) {
            roles.add(getDomainI().findRole(rolesMenu.getSelectionModel().getSelectedItem().toString()));
            rolesList.setItems(roles);
        }
    }

    public void deleteRoleHandler(){
        if(roles.contains(getDomainI().findRole(rolesMenu.getSelectionModel().getSelectedItem().toString()))) {
            roles.remove(rolesMenu.getSelectionModel().getSelectedIndex() - 1);
            rolesList.setItems(roles);
        }
    }

    public void deleteCreditHandler(){
        if(productionsList.getSelectionModel().getSelectedItem() != null){
            getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex()+1).getCredits()
                    .remove(getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex()+1).
                            findCredit(creditsProductionList.getSelectionModel().getSelectedIndex()+1));
            Object[] creditsString = getDomainI().findProduction(Integer.parseInt(idFieldProduction.getText())).getCredits().toArray();
            ObservableList<Object> strings = FXCollections.observableArrayList(Arrays.asList(creditsString));
            creditsProductionList.setItems(strings);
        }
    }

    public void editCreditHandler(){ //LIGE NU GEMMER DEN IKKE ÆNDRINGER I ROLES I EN CREDIT
        if(creditsProductionList.getSelectionModel().getSelectedItem() == null){
            productionsLabelStatus.setText("Select credit");
        }else{
            descLabelCredit.setText("Edit credit");
            addCreditProduction.setDisable(false);
            addCreditProduction.setVisible(true);
            idFieldCredit.setText(""+getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex()+1).findCredit(creditsProductionList.getSelectionModel().getSelectedIndex()+1).getCreditID());
            idFieldCreditPerson.setText(""+getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex()+1).findCredit(creditsProductionList.getSelectionModel().getSelectedIndex()+1).getPerson().getId());
            nameFieldPerson.setText(""+getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex()+1).findCredit(creditsProductionList.getSelectionModel().getSelectedIndex()+1).getPerson().getName());
            phoneFieldPerson.setText(""+getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex()+1).findCredit(creditsProductionList.getSelectionModel().getSelectedIndex()+1).getPerson().getPhone());
            emailFieldPerson.setText(""+getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex()+1).findCredit(creditsProductionList.getSelectionModel().getSelectedIndex()+1).getPerson().getEmail());
            roles.removeAll(roles); //Reset listen
            roles.addAll(getDomainI().findProduction(productionsList.getSelectionModel().getSelectedIndex()+1).findCredit(creditsProductionList.getSelectionModel().getSelectedIndex()+1).getRoles());
            rolesList.setItems(roles);
        }
    }



}
