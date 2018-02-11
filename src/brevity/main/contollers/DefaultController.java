/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brevity.main.contollers;

import java.net.URL;
import java.util.ResourceBundle;

import brevity.main.pojos.WordMeanings;
import brevity.main.utility.SqliteDB;
import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.list.ImmutableList;

import static brevity.main.framework.StageManager.getStage;
import static brevity.main.messages.JMessagges.SucssesToast;

/**
 * @author Kajiva Kinsley
 */
public class DefaultController {
    @FXML
    private AnchorPane parentAncherPane;
    @FXML
    private JFXButton closButton, btnSearch, minButton;
    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXProgressBar progressbar;

    @FXML
    private JFXSpinner spinner;

    @FXML
    private JFXListView < Label > listview;

    @FXML
    private Label status;

    private ImmutableList < WordMeanings > words = null;
    private JFXDepthManager depthManager = new JFXDepthManager();


    public void initialize () {
        // node.managedProperty().bind(node.visibleProperty());
        initData();
        clicks();
    }

    private void clicks () {
        closButton.setOnMouseEntered( e -> getStage().getScene().setCursor( Cursor.HAND ) );
        closButton.setOnMouseExited( e -> getStage().getScene().setCursor( Cursor.DEFAULT ) );
        closButton.setOnAction( ev -> {
            Platform.exit();
            System.exit( 0 );
        } );
        btnSearch.setOnAction( ev -> {
            simpleSnackBar( parentAncherPane , "Error in Loading data" );
        } );
        txtSearch.textProperty().addListener( (observable , oldValue , newValue) -> {
            listview.getItems().clear();
            spinner.setVisible( true );

            words.stream()
                    .filter( each ->
                            each.getWord().contains( newValue.toUpperCase()

                            )
                    ).forEachOrdered( i -> {
                        //System.out.println( i.getWord() )

                        listview.getItems().add( new Label( i.getWord() ) );
                    }
            );
            if(listview.getItems().isEmpty()){
                status.setVisible( true );
                status.setText( "Word not found." );

            }else{
                status.setVisible( false );
            }
            spinner.setVisible( false );

                }
        );
    }

    private void initData () {


        depthManager.setDepth( parentAncherPane , 16 );
        parentAncherPane.setStyle("-fx-effect: dropshadow( one-pass-box , blue , 10, 0.5 , 4 , 4 );" );
        listview.depthProperty().set( 1 );
        listview.setExpanded( true );
        spinner.setVisible( true );
        progressbar.setVisible( false );
        status.setVisible( false );
        spinner.managedProperty().bind(spinner.visibleProperty());
        status.managedProperty().bind(status.visibleProperty());
       /* spinner.setManaged(false);
                progressbar.setManaged(false);*/

        Task < ImmutableList < WordMeanings > > getWordsTask = new Task < ImmutableList < WordMeanings > >() {
            @Override
            protected ImmutableList < WordMeanings > call () throws Exception {
                Thread.sleep( 2500 );
                return new SqliteDB().getWords();
            }
        };
        new Thread( getWordsTask ).start();

        getWordsTask.setOnSucceeded( ev -> {

            spinner.setVisible( false );
            progressbar.setVisible( false );
            if ( getWordsTask.getValue().isEmpty() ) {
                status.setVisible( true );
                status.setText( "Error in Loading data" );

            } else {
                words = getWordsTask.getValue();
                words.each( e ->
                        listview.getItems().add( new Label( e.getWord() ) )
                );
                listview.getStyleClass().add( "mylistview" );
            }
        } );

        getWordsTask.setOnFailed( ev -> {

            spinner.setVisible( false );
            progressbar.setVisible( false );
            status.setVisible( true );
            status.setText( "Error in Loading data" );
        } );
        listview.getSelectionModel().selectedItemProperty().addListener( (observable , oldValue , newValue) -> {
            if(oldValue !=null){
                System.out.println("selection changed oldValue:"  + oldValue.getText());
            }

            System.out.println("selection changed newValue:"  + newValue.getText());
        } );

        listview.setOnMouseClicked( event ->{
            System.out.println("clicked on " + listview.getSelectionModel().getSelectedItem().getText());
                }

        );
    }

    /**
     * This will show a bottom snack bar message for 12 seconds
     */
    public static void simpleSnackBar (Pane pane , String message) {
        // pane.setStyle("-fx-effect: dropshadow( one-pass-box , blue , 10, 0.5 , 4 , 4 );" );
        JFXSnackbar snackbar = new JFXSnackbar( pane );
        snackbar.show( message , 12000 );


    }

}
