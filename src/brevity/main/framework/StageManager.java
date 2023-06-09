package brevity.main.framework;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class StageManager {
    private static Stage stage;
    private static AnchorPane pane;
    private double x = 0;
    private double y= 0;

    public StageManager (Stage stage) {
        this.stage = stage;
    }
    public void switchScene (ViewController viewController) {
        try{
            URL resource = this.getClass().getResource(viewController.getFxmlFile());
            Parent parent = FXMLLoader.load(resource);

            stage.centerOnScreen();
            Image img = new Image(getClass().getResource("/resources/drawables/dictionary.png").toURI().toString());
            stage.getIcons().add(img);
            parent.setOnMousePressed( event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            } );
            parent.setOnMouseDragged( event -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            } );
            stage.setScene(new Scene(parent));
            stage.initStyle( StageStyle.TRANSPARENT);

            stage.show();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            /*close the platform or application as whole when the error happens*/
            Platform.exit();
        }
    }
    public static void setRoot (Parent root) {
        stage.getScene().setRoot(root);
    }

    public static void setPane (AnchorPane pane) {
        StageManager.pane = pane;
    }

    public static void setPaneFragment (Parent root) {
        StageManager.pane.getChildren().setAll(root);
    }

    public static Stage getStage () {
        return stage;
    }
}
