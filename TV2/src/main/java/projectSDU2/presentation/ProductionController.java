package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class ProductionController extends Controller {

    //FXML Attributter
    @FXML
    private Label statusLabelProduction;
    @FXML
    private Label statusLabelCredit;
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

    //Attributter
    ObservableList<Object> roles = FXCollections.observableArrayList();
    ObservableList<Object> credits = FXCollections.observableArrayList();
    ArrayList<Object> tempDeletedCredits = new ArrayList<>();

    //Overrided initialize der køres hver gang fxml filen production loades
    @Override
    public void initialize() {
        if (type.equals("producer")) { //Hvis en producer er logget ind skal kun hans egne produktioner vises
            productionsList.setItems(FXCollections.observableArrayList(getDomainI().findWhereProducer(id)));
        } else { //systemadmin er logget ind
            productionsList.setItems(FXCollections.observableArrayList(getDomainI().getProductions()));
        }
        personsListSearch.setItems(FXCollections.observableArrayList(getDomainI().getPersons())); //Henter alle personer (undtagen systemadmin) og sætter dem til listen
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);

        for (int i = 0; i < getDomainI().getRoles().size(); i++) { //Tilføjer alle roller til listen der kan vælges i
            if (!rolesMenu.getItems().contains(getDomainI().getRoles().get(i))) { //Hvis den ikke allerede er der
                rolesMenu.getItems().add(getDomainI().getRoles().get(i));
            }
        }
        rolesMenu.getSelectionModel().selectFirst(); //Sætter den markerede rolle til at være den første
    }

    //Håndterer når der trykkes på cancel knap 1
    public void cancelHandler1() { //Nulstiller felter og sætter ting til at være tilgængelige igen
        addProductionpane.setDisable(true);
        addProductionpane.setVisible(false);
        finishButton.setDisable(false);
        addCreditButton.setDisable(false);
        editCreditButton.setDisable(false);
        deleteCreditButton.setDisable(false);
        nameFieldProduction.setEditable(true);
        companyFieldProduction.setEditable(true);
        credits.removeAll(credits);
        productionsLabelStatus.setText("");
        statusLabelProduction.setText("");
    }

    //Håndterer når der trykkes finish knap 1
    public void finishHandler1() {
        if (nameFieldProduction.getText().isBlank() || companyFieldProduction.getText().isBlank()) {
            statusLabelProduction.setText("One or more fields are blank"); //En eller flere felter er tomme
        } else {
            if (descLabel.getText().equals("Add production")) { //Tilføj produktion
                getDomainI().addProduction(creditsProductionList.getItems(), nameFieldProduction.getText(), companyFieldProduction.getText());
            } else {
                //edit produktion
                for (Object object : tempDeletedCredits) { //Sletter alle slettede credits i databasen ud fra listen med temp credits
                    getDomainI().deleteCredit(getDomainI().castToCredit(object).getCreditID());
                }
                getDomainI().editProduction(Integer.parseInt(idFieldProduction.getText()), nameFieldProduction.getText(),
                        companyFieldProduction.getText(), credits, validatedProduction.isSelected(), sentProduction.isSelected()); //Ændrer produktionen
                tempDeletedCredits.removeAll(tempDeletedCredits); //Nulstiller listen med temp credits
            }
            credits.removeAll(credits); //Nulstiller credits listen
            initialize(); //Kører initialize
        }
    }

    //Håndterer når der trykkes på cancel knap 2
    public void cancelHandler2() {
        addCreditProduction.setDisable(true);
        addCreditProduction.setVisible(false);
        productionsLabelStatus.setText("");
        statusLabelCredit.setText("");
    }

    //Tjekker om en person allerede har en credit i en produktion
    private boolean alreadyHasCredit(int id) {
        for (Object object : credits) {
            if (getDomainI().castToCredit(object).getPerson().getId() == id) {
                return true; //Har allerede en credit
            }
        }
        return false; //Har ikke
    }

    //Håndterer når der klikkes på finish knap 2
    public void finishHandler2() {
        if (rolesList.getItems().isEmpty()) { //Ikke tilføjet nogle roller til crediten
            statusLabelCredit.setText("Error: Credit needs role.");
        } else {
            if (idFieldCreditPerson.getText().isBlank()) { //Der er ikke valgt en person
                statusLabelCredit.setText("Person must be selected");
            } else {
                statusLabelCredit.setText("");
                if (descLabelCredit.getText().equals("Add credit")) { //Tilføj credit
                    if (alreadyHasCredit(Integer.parseInt(idFieldCreditPerson.getText()))) { //Person har allerede en credit
                        statusLabelCredit.setText("Error: Credit for person already exists. Edit existing one.");
                    } else { //Tilføjer credit
                        credits.add(getDomainI().createCredit(Integer.parseInt(idFieldCreditPerson.getText()), roles));
                        creditsProductionList.setItems(credits);
                        addCreditProduction.setDisable(true);
                        addCreditProduction.setVisible(false);
                        statusLabelCredit.setText("");
                    }
                } else if (descLabelCredit.getText().equals("Edit credit")) {
                    //edit credit
                    tempDeletedCredits.add(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()));
                    credits.remove(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem())); //Sletter den gamle
                    credits.add(getDomainI().createCredit(Integer.parseInt(idFieldCreditPerson.getText()), roles)); //Indsætter den nye
                    creditsProductionList.setItems(credits); //Sætter listen med credits
                    addCreditProduction.setDisable(true);
                    addCreditProduction.setVisible(false);
                    statusLabelCredit.setText("");
                }
            }
        }
    }

    //Håndterer når der trykkes add production
    public void addProductionHandler() {
        addProductionpane.setDisable(false);
        addProductionpane.setVisible(true);
        descLabel.setText("Add production");
        idFieldProduction.setText("ID will be given auto");
        nameFieldProduction.setText("");
        companyFieldProduction.setText("");
        validatedProduction.setSelected(false);
        sentProduction.setSelected(false);
        ObservableList<Object> strings = FXCollections.observableArrayList();
        creditsProductionList.setItems(strings); //Nulstiller listen
    }

    //Håndterer når der trykkes add credit
    public void addCreditHandler() {
        descLabelCredit.setText("Add credit");
        roles.removeAll(roles); //Nulstiller listen
        chooseButton.setDisable(false);
        idFieldCredit.setText("ID will be given auto");
        idFieldCreditPerson.setText("");
        nameFieldPerson.setText("");
        phoneFieldPerson.setText("");
        emailFieldPerson.setText("");
        addCreditProduction.setDisable(false);
        addCreditProduction.setVisible(true);
    }

    //Håndterer når der trykkes edit production
    public void editProductionHandler() {
        if (productionsList.getSelectionModel().getSelectedItem() == null) { //Production ikke valgt
            productionsLabelStatus.setText("Select production");
        } else {
            productionsLabelStatus.setText("");
            tempDeletedCredits.removeAll(tempDeletedCredits); //Nulstiller listen
            if (getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).isStatus()) { //Hvis den er godkendt skal den ikke kunne ændres
                finishButton.setDisable(true); //Gør så knapperne ikke kan bruges
                addCreditButton.setDisable(true);
                editCreditButton.setDisable(true);
                deleteCreditButton.setDisable(true);
                nameFieldProduction.setEditable(false);
                companyFieldProduction.setEditable(false);
            }
            descLabel.setText("Edit production");
            productionsLabelStatus.setText("");
            credits.removeAll(credits); //Nulstiller listen
            int id = getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).getProductionID();
            addProductionpane.setVisible(true);
            addProductionpane.setDisable(false);
            idFieldProduction.setText("" + getDomainI().findProduction(id).getProductionID()); //Sætter felterne
            nameFieldProduction.setText("" + getDomainI().findProduction(id).getName());
            companyFieldProduction.setText("" + getDomainI().findProduction(id).getCompany());
            validatedProduction.setSelected(getDomainI().findProduction(id).isStatus());
            sentProduction.setSelected(getDomainI().findProduction(id).isSent());
            creditsProductionList.setItems(FXCollections.observableArrayList(getDomainI()
                    .castToProduction(productionsList.getSelectionModel().getSelectedItem()).getCredits())); //Sætter listen med alle credits for produktionen
            for (Object object : creditsProductionList.getItems()) { //Tilføjer alle credits i listen til credit arraylisten
                credits.add(getDomainI().castToCredit(object));
            }
        }
    }

    //Håndterer når der trykkes delete production
    public void deleteProductionHandler() {
        if (productionsList.getSelectionModel().getSelectedItem() == null) { //Produktion ikke valgt
            productionsLabelStatus.setText("Select production");
        } else {
            productionsLabelStatus.setText("");
            getDomainI().deleteProduction(getDomainI().castToProduction(productionsList.getSelectionModel().getSelectedItem()).getProductionID()); //Sletter
            if (type.equals("producer")) { //Producer skal kun have sine egne produktioner
                productionsList.setItems(FXCollections.observableArrayList(getDomainI().findWhereProducer(id)));
            } else { //Systemadmin kan se alle
                productionsList.setItems(FXCollections.observableArrayList(getDomainI().getProductions()));
            }
        }
    }

    //Håndterer når der klikkes add role
    public void addRoleHandler() {
        if (!roles.contains(getDomainI().findRole(rolesMenu.getSelectionModel().getSelectedItem().toString()))) { //Hvis rollen ikke allerede er tilføjet
            roles.add(getDomainI().findRole(rolesMenu.getSelectionModel().getSelectedItem().toString())); //tilføjer den til listen
            rolesList.setItems(roles); //opdaterer listen
        }
    }

    //Slet role
    public void deleteRoleHandler() {
        if (rolesList.getSelectionModel().getSelectedItem() == null) { //role ikke valgt
            statusLabelCredit.setText("Select role");
        } else {
            if (roles.contains(getDomainI().findRole(rolesList.getSelectionModel().getSelectedItem().toString()))) { //Hvis den er i listen
                roles.remove(rolesList.getSelectionModel().getSelectedIndex()); //Sletter rollen fra listen
                rolesList.setItems(roles); //Opdaterer listen
            }
        }
    }

    //Slet credit
    public void deleteCreditHandler() {
        if (creditsProductionList.getSelectionModel().getSelectedItem() == null) { //Credit ikke valgt
            statusLabelProduction.setText("Select credit");
        } else {
            statusLabelProduction.setText("");
            tempDeletedCredits.add(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem())); //Tilføjer den til temporary listen
            credits.remove(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem())); //Sletter den fra listen
            creditsProductionList.setItems(FXCollections.observableArrayList(credits)); //Opdaterer listen
        }
    }

    //Håndterer når der trykkes edit credit
    public void editCreditHandler() {
        if (creditsProductionList.getSelectionModel().getSelectedItem() == null) { //Credit ikke valgt
            statusLabelProduction.setText("Select credit");
        } else {
            descLabelCredit.setText("");
            descLabelCredit.setText("Edit credit");
            chooseButton.setDisable(true);
            addCreditProduction.setDisable(false);
            addCreditProduction.setVisible(true);
            idFieldCredit.setText("" + getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getCreditID());
            idFieldCreditPerson.setText("" + getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getPerson().getId());
            nameFieldPerson.setText(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getPerson().getName());
            phoneFieldPerson.setText("" + getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getPerson().getPhone());
            emailFieldPerson.setText(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getPerson().getEmail());
            roles.removeAll(roles); //Reset listen
            roles.addAll(getDomainI().castToCredit(creditsProductionList.getSelectionModel().getSelectedItem()).getRoles()); //Tilføjer alle roles
            rolesList.setItems(roles); //opdaterer listen
        }
    }

    //Håndterer når der trykkes søg
    public void searchHandler() {
        productionsList.setItems(FXCollections.observableArrayList(getDomainI().searchProductions(searchField.getText()))); //Sætter listen
    }

    //Håndterer når der søges efter person til en credit
    public void searchPersonHandler() {
        personsListSearch.setItems(FXCollections.observableArrayList(getDomainI().searchPersons(personSearchField.getText()))); //Sætter listen
    }

    //Håndterer når der trykkes vælg person til credit
    public void chooseButtonHandler() {
        if (personsListSearch.getSelectionModel().getSelectedItem() == null) { //Person ikke valgt
            statusLabelCredit.setText("Select person");
        } else { //udfylder felter for personen
            idFieldCreditPerson.setText(getDomainI().castToPerson(personsListSearch.getSelectionModel().getSelectedItem()).getId() + "");
            nameFieldPerson.setText(getDomainI().castToPerson(personsListSearch.getSelectionModel().getSelectedItem()).getName());
            phoneFieldPerson.setText(getDomainI().castToPerson(personsListSearch.getSelectionModel().getSelectedItem()).getPhone() + "");
            emailFieldPerson.setText(getDomainI().castToPerson(personsListSearch.getSelectionModel().getSelectedItem()).getEmail());
        }
    }
}
