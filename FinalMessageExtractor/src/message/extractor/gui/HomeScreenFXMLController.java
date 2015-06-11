/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.extractor.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import weka.gui.arffviewer.ArffViewer;

/**
 * FXML Controller class
 *
 * @author HBK
 */
public class HomeScreenFXMLController implements Initializable, ControlledScreen {

    ScreensController myController;
    @FXML
    private Button openWekaBtn;
    @FXML
    private Button addDataBtn;
    @FXML
    private Button graphBtn;
    @FXML
    private Button creditBtn;

    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    public void goToMain(ActionEvent event) {
        myController.setScreen(MainApplication.MAIN_SCREEN);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleOpenWeka(ActionEvent event) {
        ArffViewer arffViewer = new ArffViewer();
        arffViewer.setVisible(true);
    }

    @FXML
    private void handleExitBtn(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    private void handleGraphBtn(ActionEvent event) {
        myController.setScreen(MainApplication.GRAPH_SCREEN);
    }

    @FXML
    private void handleAddDataBtn(ActionEvent event) {
        myController.setScreen(MainApplication.ANAL_SCREEN);
    }

    @FXML
    private void handleCreditBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("creditPageFXML.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
