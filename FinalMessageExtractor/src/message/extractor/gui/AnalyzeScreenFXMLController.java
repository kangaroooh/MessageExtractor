package message.extractor.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import message.extractor.analyzer.MessageExtractor;
import message.extractor.arff.ArffFileWriter;

public class AnalyzeScreenFXMLController implements Initializable, ControlledScreen {

    private int[] nValues = new int[8];
    private ArffFileWriter arffWriter;
    private ArffFileWriter arffWriterWeighted;
    private MessageExtractor msgExtractor;
    private ScreensController parentScreen;

    private File selectedInputFile;
    private Stage stage;

    private boolean isInFileMode;
    private boolean thSentenceMode = true;

    public void setParentScreen(ScreensController parentScreen) {
        this.parentScreen = parentScreen;
    }

    @FXML
    public void handleBackBtn(ActionEvent event) {
        parentScreen.setScreen(MainApplication.MAIN_SCREEN);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void LoadBtnHandler(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
        selectedInputFile = fileChooser.showOpenDialog(stage);
        FileNameLabel.setText("File : " + selectedInputFile.getName());
        if (selectedInputFile == null) {
            isInFileMode = false;
        } else {
            isInFileMode = true;
        }

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

    @FXML
    private void analyzeBtnHandler(ActionEvent event) {
        if (!isInFileMode) {

            msgExtractor.setUserDefineN(Integer.parseInt(W4n.getText()), Integer.parseInt(W5n.getText()),
                    Integer.parseInt(E1n.getText()), Integer.parseInt(E2n.getText()), Integer.parseInt(E3n.getText()),
                    Integer.parseInt(E4n.getText()), Integer.parseInt(E5n.getText()), Integer.parseInt(E6n.getText()));

            arffWriter.setUserDefineN(Integer.parseInt(W4n.getText()), Integer.parseInt(W5n.getText()),
                    Integer.parseInt(E1n.getText()), Integer.parseInt(E2n.getText()), Integer.parseInt(E3n.getText()),
                    Integer.parseInt(E4n.getText()), Integer.parseInt(E5n.getText()), Integer.parseInt(E6n.getText()));

            arffWriterWeighted.setUserDefineN(Integer.parseInt(W4n.getText()), Integer.parseInt(W5n.getText()),
                    Integer.parseInt(E1n.getText()), Integer.parseInt(E2n.getText()), Integer.parseInt(E3n.getText()),
                    Integer.parseInt(E4n.getText()), Integer.parseInt(E5n.getText()), Integer.parseInt(E6n.getText()));

            msgExtractor.setSentenceMode(thSentenceMode);
            arffWriter.setSentenceMode(thSentenceMode);
            arffWriterWeighted.setSentenceMode(thSentenceMode);
            
            // set to uneditble
            W4n.setDisable(true);
            W5n.setDisable(true);
            E1n.setDisable(true);
            E2n.setDisable(true);
            E3n.setDisable(true);
            E4n.setDisable(true);
            E5n.setDisable(true);
            E6n.setDisable(true);
            thRadio.setDisable(true);
            enRadio.setDisable(true);
            if (authorField.getText().equals("")) {
                msgExtractor.extract("??", inputTextArea.getText());
            } else {
                msgExtractor.extract(authorField.getText(), inputTextArea.getText());

            }
            // Clear Input Fields
            //authorField.setText("");
            //inputTextArea.setText("");

            //setEnable
            //saveClearBtn.setDisable(false);
        } else { // In file mode

            msgExtractor.setUserDefineN(Integer.parseInt(W4n.getText()), Integer.parseInt(W5n.getText()),
                    Integer.parseInt(E1n.getText()), Integer.parseInt(E2n.getText()), Integer.parseInt(E3n.getText()),
                    Integer.parseInt(E4n.getText()), Integer.parseInt(E5n.getText()), Integer.parseInt(E6n.getText()));

            arffWriter.setUserDefineN(Integer.parseInt(W4n.getText()), Integer.parseInt(W5n.getText()),
                    Integer.parseInt(E1n.getText()), Integer.parseInt(E2n.getText()), Integer.parseInt(E3n.getText()),
                    Integer.parseInt(E4n.getText()), Integer.parseInt(E5n.getText()), Integer.parseInt(E6n.getText()));

            arffWriterWeighted.setUserDefineN(Integer.parseInt(W4n.getText()), Integer.parseInt(W5n.getText()),
                    Integer.parseInt(E1n.getText()), Integer.parseInt(E2n.getText()), Integer.parseInt(E3n.getText()),
                    Integer.parseInt(E4n.getText()), Integer.parseInt(E5n.getText()), Integer.parseInt(E6n.getText()));

            // Read
            try {

                BufferedReader reader = new BufferedReader(new FileReader(selectedInputFile));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {

                    if (line.equals(separatorField.getText()) && separatorField.getText().length() != 0) {
                        msgExtractor.extract(authorField.getText(), sb.toString());
                        System.out.println(sb.toString());
                        sb = new StringBuilder();
                        continue;
                    }
                    sb.append(line);
                }

                msgExtractor.extract(authorField.getText(), sb.toString());
                System.out.println(sb.toString());

            } catch (IOException e) {
                System.err.println(e);
            }

            // set to uneditble
            W4n.setDisable(true);
            W5n.setDisable(true);
            E1n.setDisable(true);
            E2n.setDisable(true);
            E3n.setDisable(true);
            E4n.setDisable(true);
            E5n.setDisable(true);
            E6n.setDisable(true);
            thRadio.setDisable(true);
            enRadio.setDisable(true);

            //saveClearBtn.setDisable(false);
        }
        //saveClearBtn.setDisable(false);
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) {
        if (msgExtractor.getNumRecords() == 0) {
            Dialogs.showInformationDialog(null, "No data to be saved !!");
            return;
        }
        
        W4n.setDisable(false);
        W5n.setDisable(false);
        E1n.setDisable(false);
        E2n.setDisable(false);
        E3n.setDisable(false);
        E4n.setDisable(false);
        E5n.setDisable(false);
        E6n.setDisable(false);
        thRadio.setDisable(false);
        enRadio.setDisable(false);

        String fileName;

        do {
            fileName = Dialogs.showInputDialog(null, "File Name", "File Name", "Please input File Name");
        } while (fileName.isEmpty());

        msgExtractor.close(fileName, fileName + " (Weighted)");
        Dialogs.showInformationDialog(null, "Files successfully saved");

        //saveClearBtn.setDisable(true);
        inputTextArea.setDisable(false);
        resultArea.setText("");

        isInFileMode = false;
    }
    
    @FXML
    private void noBtnHandler(ActionEvent event) {

        // Clear Input and Result Fields
        authorField.setText("");
        inputTextArea.setText("");
        resultArea.setText("");
    }
    
    @FXML
    private void yesBtnHandler(ActionEvent event) {

        // Save this line to arff
        msgExtractor.saveArffRecord();
        
        // Clear Input and Result Fields
        authorField.setText("");
        inputTextArea.setText("");
        resultArea.setText("");
        
        //setEnable
        //saveClearBtn.setDisable(false);
    }
    
    @FXML
    private void thRadioHandler(ActionEvent event) {
        if (thRadio.isSelected()) thSentenceMode = true;
    }
    
    @FXML
    private void enRadioHandler(ActionEvent event) {
        if (enRadio.isSelected()) thSentenceMode = false;
    }

    @FXML
    private void textAreaHandler(KeyEvent event) {
        isInFileMode = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        arffWriter = new ArffFileWriter();
        arffWriterWeighted = new ArffFileWriter();
        msgExtractor = new MessageExtractor(arffWriter, arffWriterWeighted);

        //Checkbox set
        UnknownCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {
                if (new_val) {
                    authorField.setDisable(true);
                    authorField.setText("??");

                } else {
                    authorField.setDisable(false);
                    authorField.setText("");
                }
            }
        });

        // Set textarea to msgExtrator
        msgExtractor.setResultTextArea(resultArea);
    }

    //////////////////////// FXML Related Variables /////////////////////////
    @FXML
    private TextField authorField;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private Label AnalyzeLabel;
    @FXML
    private CheckBox UnknownCheckBox;

    @FXML
    private TextField W4n;
    @FXML
    private TextField W5n;
    @FXML
    private TextField E1n;
    @FXML
    private TextField E2n;
    @FXML
    private TextField E3n;
    @FXML
    private TextField E4n;
    @FXML
    private TextField E5n;
    @FXML
    private TextField E6n;

    @FXML
    private TextField separatorField;

    @FXML
    private TextArea resultArea;

    @FXML
    private Button backBtn;
    @FXML
    private Button LoadBtn;
    @FXML
    private Button analyzeBtn;
    @FXML
    private Button saveClearBtn;
    @FXML
    private Label FileNameLabel;
    
    @FXML
    private Button yesBtn;
    @FXML
    private Button noBtn;    
    @FXML
    private RadioButton thRadio;
    @FXML
    private RadioButton enRadio;
    @FXML
    private ToggleGroup sentenceToggle;

    /////////////////////////////////////////////////////////////////////////
}
