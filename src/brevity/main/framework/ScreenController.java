package brevity.main.framework;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ScreenController {
    private ScreenController(){
        new Exception ("Can not create an Object from this Class");
    }


    public static void setScreen(ViewController screen) {
        switch ( screen ) {
            case DEFAULT_VIEW:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.DEFAULT_VIEW.getFxmlFile())));
                } catch ( IOException e ) {
                    e.printStackTrace();

                }
                break;
            default:
                break;


        }
    }

}
