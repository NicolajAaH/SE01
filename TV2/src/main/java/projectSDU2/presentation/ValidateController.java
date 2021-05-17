package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ValidateController extends Controller{

    @FXML
    private ListView validationsList;
    @FXML
    private Button validateButton;

    @Override
    public void initialize() {
        validationsList.setItems(FXCollections.observableArrayList(getDomainI().getNotValidated()));
    }

    public void validateHandler(){
        getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()).setStatus(true);
        getDomainI().getPersistenceI().getFacade().edit(getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()).getProductionID(),
                getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()), "productionmapper");
        initialize();
    }


}
