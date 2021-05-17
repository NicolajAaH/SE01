package projectSDU2.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CoreController extends Controller{

    @FXML
    private Button mergePersonsButton;
    @FXML
    private Button verifyButton;
    @FXML
    private Button creditingReportButton;
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
            creditingReportButton.setDisable(true);
            creditingReportButton.setVisible(false);
            verifyButton.setDisable(true);
            verifyButton.setVisible(false);
            mergePersonsButton.setDisable(true);
            mergePersonsButton.setVisible(false);
        }
        else if(type.equals("participant")){
            producers.setDisable(true);
            producers.setVisible(false);

            productions.setDisable(true);
            productions.setVisible(false);

            participants.setDisable(true);
            participants.setVisible(false);

            creditingReportButton.setDisable(true);
            creditingReportButton.setVisible(false);
            verifyButton.setDisable(true);
            verifyButton.setVisible(false);
            mergePersonsButton.setDisable(true);
            mergePersonsButton.setVisible(false);
        }
    }

    @FXML
    private void personalHandler(){
        newFxml("personal.fxml");
    }

    @FXML
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

    @FXML
    private void verifyHandler(){
        newFxml("validate.fxml");
    }

    @FXML
    private void mergePersonsHandler(){
        newFxml("merge.fxml");
    }

    public void generateCreditingReportHandler(){
        getDomainI().generateCreditingReport();
    }


}
