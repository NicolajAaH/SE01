package usersystem.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    //FXMLLoader og stage gemmes i statiske variable
    private static FXMLLoader fxmlLoader = new FXMLLoader();
    private static Stage stage;

    //Start metoden kører ved start af programmet, hvor start fxml filen loades og scenen sættes
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("usersystem_core.fxml")); //FXML fil indlæses
        stage.setTitle("Forbrugersystem");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    //Kører programmet
    public static void main(String[] args) {
        launch(args);
    }
}
