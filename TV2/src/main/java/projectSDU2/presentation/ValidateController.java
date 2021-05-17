package projectSDU2.presentation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ValidateController extends Controller{

    @FXML
    private Label statusLabel;
    @FXML
    private ListView validationsList;
    @FXML
    private Button validateButton;

    @Override
    public void initialize() {
        validationsList.setItems(FXCollections.observableArrayList(getDomainI().getNotValidated()));
        statusLabel.setText("");
    }

    public void validateHandler(){
        if(validationsList.getSelectionModel().getSelectedItem() == null){
            statusLabel.setText("Select production");
        }else {
            getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()).setStatus(true);
            getDomainI().getPersistenceI().getFacade().edit(getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()).getProductionID(),
                    getDomainI().castToProduction(validationsList.getSelectionModel().getSelectedItem()), "productionmapper");
            initialize();
        }
    }


}
