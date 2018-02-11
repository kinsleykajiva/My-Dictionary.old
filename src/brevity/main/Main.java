/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brevity.main;

import java.net.URL;
import java.util.logging.LogManager;

import brevity.main.framework.StageManager;
import brevity.main.framework.ViewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Kajiva Kinsley
 */
public class Main extends Application {

    private StageManager stageManager = null;
    @Override
    public void start(Stage stage) throws Exception {
        /*URL resource = this.getClass().getResource("/resources/layouts/DefaultController.fxml");
        Parent root = FXMLLoader.load(resource);
        root.setOnMousePressed( event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        } );
        root.setOnMouseDragged( event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        } );
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();*/
        stageManager = new StageManager(stage);
        stageManager.switchScene( ViewController.DEFAULT_VIEW);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        launch(args);
    }
    
}
