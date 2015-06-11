package message.extractor.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static final String MAIN_SCREEN = "homeScreen";
    public static final String MAIN_SCREEN_FXML = "homeScreenFXML.fxml";

    public static final String ANAL_SCREEN = "analsSreen";
    public static final String ANAL_SCREEN_FXML = "analyzeScreenFXML.fxml";

    public static final String GRAPH_SCREEN = "graphScreen";
    public static final String GRAPH_SCREEN_FXML = "graphScreenFXML.fxml";

    @Override
    public void start(Stage stage) throws Exception {

        ScreensController mainContainer = new ScreensController();
        
        mainContainer.loadScreen(MAIN_SCREEN, MAIN_SCREEN_FXML);
        mainContainer.loadScreen(ANAL_SCREEN, ANAL_SCREEN_FXML);
        mainContainer.loadScreen(GRAPH_SCREEN, GRAPH_SCREEN_FXML);

        mainContainer.setScreen(MAIN_SCREEN);        
        
        Scene scene = new Scene(mainContainer);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}