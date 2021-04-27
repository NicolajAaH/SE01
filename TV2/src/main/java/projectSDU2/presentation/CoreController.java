package projectSDU2.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CoreController extends Controller{

    @FXML
    private Button personal;
    @FXML
    private Button participants;
    @FXML
    private Button productions;
    @FXML
    private Button producers;
    @FXML
    private Button signout;

    @Override
    public void initialize(){
        if(type.equals("systemadministrator")){

        }
        else if(type.equals("producer")){
            producers.setDisable(true);
            producers.setVisible(false);
        }
        else if(type.equals("participant")){
            producers.setDisable(true);
            producers.setVisible(false);

            productions.setDisable(true);
            productions.setVisible(false);

            participants.setDisable(true);
            participants.setVisible(false);
        }
    }

    @FXML
    private void personalHandler(){
        newFxml("personal.fxml");
    }

    private void participantsHandler(){
        newFxml("participants.fxml");
    }

    @FXML
    private void productionsHandler(){
        newFxml("productions.fxml");
    }

    @FXML
    private void producersHandler(){
        newFxml("producers.fxml");
    }


}
