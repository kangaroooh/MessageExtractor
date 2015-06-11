/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.extractor.gui;

import java.io.IOException;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ScreensController extends StackPane {
    
    public ScreensController() {
        setStyle("-fx-background-color: black");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private HashMap<String, Node> screens = new HashMap<String, Node>();

    private void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public boolean loadScreen(String name, String resource) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent loadedScreen = (Parent) loader.load();
            ControlledScreen myScreenController = ((ControlledScreen) loader.getController());
            myScreenController.setParentScreen(this);

            addScreen(name, loadedScreen);
            
            return true;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) {

        if (screens.get(name) != null) { //screen loaded 
            final DoubleProperty opacity = opacityProperty();

            //Is there is more than one screen 
            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(100),
                                new EventHandler() {

                                    @Override
                                    public void handle(Event t) {
                                        //remove displayed screen 
                                        getChildren().remove(0);
                                        //add new screen 
                                        getChildren().add(0, screens.get(name));
                                        Timeline fadeIn = new Timeline(
                                                new KeyFrame(Duration.ZERO,
                                                        new KeyValue(opacity, 0.0)),
                                                new KeyFrame(new Duration(80),
                                                        new KeyValue(opacity, 1.0)));
                                        fadeIn.play();
                                    }
                                }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                //no one else been displayed, then just show 
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(250),
                                new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            
            return true;
            
        } else {
            System.out.println("screen hasn't been loaded!\n");
            
            return false;
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }   
}