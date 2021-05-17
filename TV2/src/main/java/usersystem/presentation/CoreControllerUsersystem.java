package usersystem.presentation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import usersystem.Interfaces.DomainI;
import usersystem.business.Client;
import usersystem.business.DomainConnect;

import java.util.ArrayList;
import java.util.HashMap;

public class CoreControllerUsersystem {
    private DomainI instance = new DomainConnect();

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

    public void initialize(){
        listSearchResults.setItems(FXCollections.observableArrayList(instance.getAll()));
        selectPane.setDisable(true);
        selectPane.setVisible(false);
    }

    public void searchHandler(){
        listSearchResults.setItems(FXCollections.observableArrayList(instance.search(searchFieldUser.getText())));
    }

    public void selectHandler(){
        String type = instance.castToSimpleObject(listSearchResults.getSelectionModel().getSelectedItem()).getType();
        HashMap<String, ArrayList<String>> result = instance.getSpecific(type, instance.castToSimpleObject(listSearchResults.getSelectionModel().getSelectedItem()).getId());
        selectPane.setDisable(false);
        selectPane.setVisible(true);
        String name = result.get("Name").get(0);
        nameLabel.setText("Name: " + name);
        ArrayList<String> credits = result.get("Credits");
        if(type.equals("Production")){
            labelType.setText("Production");
            String company = result.get("Company").get(0);
            companyTypeLabel.setText("Company: " + company);
        }else{
            labelType.setText("Person");
            String typePerson = result.get("Type").get(0);
            companyTypeLabel.setText("Type: " + typePerson);
        }
        creditsList.setItems(FXCollections.observableArrayList(credits));
    }

    public void backHandler(){
        selectPane.setDisable(true);
        selectPane.setVisible(false);
    }
}
