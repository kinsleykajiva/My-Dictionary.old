/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brevity.main;

import java.net.URL;
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
    private double x = 0;
    private double y= 0;
    @Override
    public void start(Stage stage) throws Exception {
        URL resource = this.getClass().getResource("/resources/layouts/DefaultController.fxml");
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
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
