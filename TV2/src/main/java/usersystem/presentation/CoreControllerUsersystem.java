package usersystem.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import usersystem.Interfaces.DomainI;
import usersystem.business.DomainConnect;

import java.util.ArrayList;
import java.util.HashMap;

public class CoreControllerUsersystem {

    //Opretter instance af DomainI som er kommunikationen til domænelaget
    private DomainI instance = new DomainConnect();

    //FXML Attributter
    @FXML
    private ListView listSearchResults;
    @FXML
    private TextField searchFieldUser;
    @FXML
    private Button searchButtonUser;
    @FXML
    private Button selectButton;
    @FXML
    private DialogPane selectPane;
    @FXML
    private Label labelType;
    @FXML
    private Label companyTypeLabel;
    @FXML
    private ListView creditsList;
    @FXML
    private Button backButton;
    @FXML
    private Label nameLabel;

    //Initialize som køres hver gang FXML filen usersystem_core loades
    public void initialize() {
        listSearchResults.setItems(FXCollections.observableArrayList(instance.getAll())); //Sætter listen til at alle produktioner og alle personer (undtaget systemadmins)
        selectPane.setDisable(true);
        selectPane.setVisible(false);
    }

    //Håndterer når der klikkes på søg
    public void searchHandler() {
        listSearchResults.setItems(FXCollections.observableArrayList(instance.search(searchFieldUser.getText()))); //Sætter listen med resultatet af søgningen
    }

    //Håndterer når der klikkes select
    public void selectHandler() {
        String type = instance.castToSimpleObject(listSearchResults.getSelectionModel().getSelectedItem()).getType(); //Finder typen af det valgte objekt
        HashMap<String, ArrayList<String>> result = instance.getSpecific(type, instance.castToSimpleObject(listSearchResults.getSelectionModel().getSelectedItem()).getId());
        selectPane.setDisable(false);
        selectPane.setVisible(true);
        String name = result.get("Name").get(0); //Henter attributterne ud fra resultatet returneret af serveren. get(0) da listen kun indeholder et element.
        nameLabel.setText("Name: " + name);
        ArrayList<String> credits = result.get("Credits");
        if (type.equals("Production")) { //Hvis det er en produktion har den company
            labelType.setText("Production");
            String company = result.get("Company").get(0);
            companyTypeLabel.setText("Company: " + company);
        } else { //Hvis det er person har den type
            labelType.setText("Person");
            String typePerson = result.get("Type").get(0);
            companyTypeLabel.setText("Type: " + typePerson);
        }
        creditsList.setItems(FXCollections.observableArrayList(credits)); //Sætter credits fra personen/produktionen til listen
    }

    //Håndterer når der klikkes back
    public void backHandler() {
        selectPane.setDisable(true);
        selectPane.setVisible(false);
    }
}
