package message.extractor.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import message.extractor.graph.BarChartWrapper;
import message.extractor.graph.GraphData;

public class GraphScreenFXMLController implements Initializable, ControlledScreen {

    private GraphData graphData;
    private String[] keys;
    private ObservableList<String> listData;
    private ArrayList<CheckBox> allCheckBox;
    private File currentWokingFile;
    
    private Integer[] nValues = new Integer[8];
    private ArrayList<String> selectedCode = new ArrayList<>();
    
    private ScreensController parentScreen;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayGraphBtn.setDisable(true);
        resultList.setDisable(true);
        nameArea.setDisable(true);
        
        // Add all checkbox into Array List
        allCheckBox = new ArrayList<>();
        putCheckBoxInBag();
        
        // Add change listener to select all check box
        Allbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (new_val) {
                    setAllCheckBoxSelected(true);
                } else {
                    setAllCheckBoxSelected(false);
                }
            }
        });
        
        // Setup checkbox handler
        setUpCheckBoxHandler();
        
        // Setup listview
        resultList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setParentScreen(ScreensController parentScreen) {
        this.parentScreen = parentScreen;
    }

    @FXML
    public void handleBackBtn(ActionEvent event) {
        
        // Go back to menu
        parentScreen.setScreen(MainApplication.MAIN_SCREEN);
        
        // Perform UI Logics
        displayGraphBtn.setDisable(true);
        resultList.setDisable(true);
        nameArea.setDisable(true);
        setAllCheckBoxSelected(false);
        Allbox.setSelected(false);
        
        // Clear data in listData
        if(listData!=null) {
            listData.clear();
            resultList.setItems(listData);
        }
    }

    @FXML
    public void handleLoadFileBtn(ActionEvent event) {
        // Ask for arff file to work with
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Arff File ...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arff File", "*.arff"));
        currentWokingFile = fileChooser.showOpenDialog(null);
        
        // If user doesn't select any file, just simple return from function
        if(currentWokingFile == null)
            return;
        
        // Load GraphData and get keys
        graphData = new GraphData(currentWokingFile);
        Object[] tempKeys = graphData.getKeys();

        // Convert Object[] to String[]
        keys = new String[tempKeys.length];
        for (int i = 0; i < tempKeys.length; i++) {
            // keys[i] = new String();
            keys[i] = tempKeys[i].toString();
        }

        // Load All keys into list view
        listData = FXCollections.observableArrayList(keys);
        resultList.setItems(listData);

        // Get nValues and set in UI
        nValues = graphData.getNValues();
        nW4.setText(nValues[0].toString());
        nW5.setText(nValues[1].toString());
        nE1.setText(nValues[2].toString());
        nE2.setText(nValues[3].toString());
        nE3.setText(nValues[4].toString());
        nE4.setText(nValues[5].toString());
        nE5.setText(nValues[6].toString());
        nE6.setText(nValues[7].toString());

        displayGraphBtn.setDisable(false);
        resultList.setDisable(false);
        nameArea.setDisable(false);
    }

    private void setAllCheckBoxSelected(boolean checked) {
        for (CheckBox current : allCheckBox) {
            current.setSelected(checked);
        }
    }

    private void setUpCheckBoxHandler() {
        for (final CheckBox current : allCheckBox) {
            current.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                    if (new_val) {
                        selectedCode.add(current.getText());
                    } else {
                        selectedCode.remove(current.getText());
                    }

                    // display all values in selectedCode
                    for (String current : selectedCode) {
                        System.out.println(current);
                    }
                }
            });
        }
    }

    private void putCheckBoxInBag() {
        allCheckBox.add(C1box);
        allCheckBox.add(C2box);
        allCheckBox.add(C3box);
        allCheckBox.add(C4box);
        allCheckBox.add(C5box);
        allCheckBox.add(C6box);
        allCheckBox.add(C7box);
        allCheckBox.add(C8box);
        allCheckBox.add(C9box);
        allCheckBox.add(W1box);
        allCheckBox.add(W2box);
        allCheckBox.add(W3box);
        allCheckBox.add(W4box);
        allCheckBox.add(W5box);
        allCheckBox.add(W6box);
        allCheckBox.add(W7box);
        allCheckBox.add(W8box);
        allCheckBox.add(W9box);
        allCheckBox.add(W10box);
        allCheckBox.add(P1box);
        allCheckBox.add(P2box);
        allCheckBox.add(P3box);
        allCheckBox.add(P4box);
        allCheckBox.add(P5box);
        allCheckBox.add(P6box);
        allCheckBox.add(P7box);
        allCheckBox.add(P8box);
        allCheckBox.add(P9box);
        allCheckBox.add(P10box);
        allCheckBox.add(E1box);
        allCheckBox.add(E2box);
        allCheckBox.add(E3box);
        allCheckBox.add(E4box);
        allCheckBox.add(E5box);
        allCheckBox.add(E6box);
        allCheckBox.add(E7box);
        allCheckBox.add(E8box);
        allCheckBox.add(E9box);
        allCheckBox.add(E10box);
        allCheckBox.add(S1box);
        allCheckBox.add(S2box);
        allCheckBox.add(S3box);
        allCheckBox.add(S4box);
        allCheckBox.add(S5box);
        allCheckBox.add(T1box);
        allCheckBox.add(T2box);
        allCheckBox.add(T3box);
        allCheckBox.add(T4box);
        allCheckBox.add(T5box);
        allCheckBox.add(T6box);
    }

    @FXML
    private void handleNameFieldChange(KeyEvent event) {

        System.out.println(nameArea.getText());

        String requireChars = nameArea.getText();
        ArrayList<String> matchedName = new ArrayList<>();

        for (String currentName : keys) {
            if (currentName.startsWith(requireChars)) {
                matchedName.add(currentName);
            }
        }

        ObservableList<String> tempListData = FXCollections.observableArrayList(matchedName);
        resultList.setItems(tempListData);

    }

    @FXML
    private void handleDisplayGraphBtn(ActionEvent event) {
        
        // Build title String
        ObservableList<String> selectedKeys = resultList.getSelectionModel().getSelectedItems();
        StringBuilder sb = new StringBuilder();
        sb.append("Graph of ");
        for (String currentKey : selectedKeys) {
            sb.append(currentKey);
            sb.append(", ");
        }      

        // Display Graph
        BarChartWrapper.display(selectedKeys, selectedCode, sb.substring(0, sb.length()-2), graphData);
    }

    @FXML
    private void handleHelpBtn(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("helpPageFXML.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);

            Stage graphStage = new Stage();
            graphStage.setScene(scene);
            graphStage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /////////////////////// FXML Related Variables /////////////////////
    @FXML
    private CheckBox C1box;
    @FXML
    private CheckBox C2box;
    @FXML
    private CheckBox C3box;
    @FXML
    private CheckBox C4box;
    @FXML
    private CheckBox C5box;
    @FXML
    private CheckBox C6box;
    @FXML
    private CheckBox C7box;
    @FXML
    private CheckBox C8box;
    @FXML
    private CheckBox C9box;

    @FXML
    private CheckBox W1box;
    @FXML
    private CheckBox W2box;
    @FXML
    private CheckBox W3box;
    @FXML
    private CheckBox W4box;
    @FXML
    private CheckBox W5box;
    @FXML
    private CheckBox W6box;
    @FXML
    private CheckBox W7box;
    @FXML
    private CheckBox W8box;
    @FXML
    private CheckBox W9box;
    @FXML
    private CheckBox W10box;

    @FXML
    private CheckBox P1box;
    @FXML
    private CheckBox P2box;
    @FXML
    private CheckBox P3box;
    @FXML
    private CheckBox P4box;
    @FXML
    private CheckBox P5box;
    @FXML
    private CheckBox P6box;
    @FXML
    private CheckBox P7box;
    @FXML
    private CheckBox P8box;
    @FXML
    private CheckBox P9box;
    @FXML
    private CheckBox P10box;

    @FXML
    private CheckBox E1box;
    @FXML
    private CheckBox E2box;
    @FXML
    private CheckBox E3box;
    @FXML
    private CheckBox E4box;
    @FXML
    private CheckBox E5box;
    @FXML
    private CheckBox E6box;
    @FXML
    private CheckBox E7box;
    @FXML
    private CheckBox E8box;
    @FXML
    private CheckBox E9box;
    @FXML
    private CheckBox E10box;

    @FXML
    private CheckBox S1box;
    @FXML
    private CheckBox S2box;
    @FXML
    private CheckBox S3box;
    @FXML
    private CheckBox S4box;
    @FXML
    private CheckBox S5box;

    @FXML
    private CheckBox T1box;
    @FXML
    private CheckBox T2box;
    @FXML
    private CheckBox T3box;
    @FXML
    private CheckBox T4box;
    @FXML
    private CheckBox T5box;
    @FXML
    private CheckBox T6box;

    @FXML
    private CheckBox Allbox;

    public Label nW4;
    public Label nW5;
    public Label nE1;
    public Label nE2;
    public Label nE3;
    public Label nE4;
    public Label nE5;
    public Label nE6;

    @FXML
    private TextField nameArea;
    @FXML
    private ListView<String> resultList;

    @FXML
    private Label label;
    @FXML
    private Font x1;
    @FXML
    private Button helpBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button loadFileBtn;
    @FXML
    private Button displayGraphBtn;

    ////////////////////////////////////////////////////////////////////////
}
