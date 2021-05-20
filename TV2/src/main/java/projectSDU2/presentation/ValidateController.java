package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ValidateController extends Controller {

    //FXML Attributter
    @FXML
    private Label statusLabel;
    @FXML
    private ListView validationsList;
    @FXML
    private Button validateButton;

    //Overrided initialize der køres hver gang fxml filen validate loades
    @Override
    public void initialize() {
        validationsList.setItems(FXCollections.observableArrayList(getDomainI().getNotValidated())); //Sætter listen til alle ikke-valideret produktioner
        statusLabel.setText("");
    }

    //Håndterer når der trykkes valider
    public void validateHandler() {
        if (validationsList.getSelectionModel().getSelectedItem() == null) { //Produktion ikke valgt
            statusLabel.setText("Select production");
        } else {
            getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()).setStatus(true); //Ændrer til at den er valideret
            getDomainI().getPersistenceI().getFacade().edit(getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()).getProductionID(),
                    getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()), "productionmapper"); //ændrer i databasen
            initialize(); //Opdaterer listen
        }
    }
}
