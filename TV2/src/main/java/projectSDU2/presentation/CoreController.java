package projectSDU2.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CoreController extends Controller {

    //FXML Attributter
    @FXML
    private Label statusLabelCore;
    @FXML
    private Button financeReportButton;
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

    //Overrider initialize metoden, som køres hver gang der skiftes til Core fxml-filen
    @Override
    public void initialize() {
        statusLabelCore.setText("");
        if (type.equals("systemadministrator")) {
            //Alt er synligt
        } else if (type.equals("producer")) { //Skjuler ting som ikke er relevant for producer
            producers.setDisable(true);
            producers.setVisible(false);
            creditingReportButton.setDisable(true);
            creditingReportButton.setVisible(false);
            verifyButton.setDisable(true);
            verifyButton.setVisible(false);
            mergePersonsButton.setDisable(true);
            mergePersonsButton.setVisible(false);
            financeReportButton.setDisable(true);
            financeReportButton.setVisible(false);
        } else if (type.equals("participant")) { //Skjuler ting som ikke er relevant for participant
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
            financeReportButton.setDisable(true);
            financeReportButton.setVisible(false);
        }
    }

    //Metoder der håndterer forskellige klik på knapper. Skifter til hver sin fxml fil
    @FXML
    private void personalHandler() {
        newFxml("personal.fxml");
    }

    @FXML
    private void participantsHandler() {
        newFxml("participants.fxml");
    }

    @FXML
    private void productionsHandler() {
        newFxml("productions.fxml");
    }

    @FXML
    private void producersHandler() {
        newFxml("producers.fxml");
    }

    @FXML
    private void verifyHandler() {
        newFxml("validate.fxml");
    }

    @FXML
    private void mergePersonsHandler() {
        newFxml("merge.fxml");
    }

    //Metoder til at håndtere knapper til at generere rapporter
    @FXML
    private void generateCreditingReportHandler() {
        getDomainI().generateCreditingReport();
        statusLabelCore.setText("Crediting report generated"); //Sætter statusLabel
    }

    @FXML
    private void generateFinanceReportHandler() {
        getDomainI().generateFinanceReport();
        statusLabelCore.setText("Finance report generated"); //Sætter statusLabel
    }
}
