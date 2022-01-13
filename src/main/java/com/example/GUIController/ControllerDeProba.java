package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class ControllerDeProba {
    public TextField ll;
    /*public TextField unu;
        public TextField doi;*/
    private MainController cont;
    @FXML
    public TextField unu,doi;

    //


    @FXML
    private AnchorPane[] fancyTable ;

    @FXML
    private VBox vb;

    public void setProfileController(MainController cont) {
        this.cont=cont;
        AnchorPane[] fancyTable = new AnchorPane[15];
       /* fancyTable[1]=new TextField();
        fancyTable[1].setText("primul text field");
        fancyTable[2]=new TextField();
        fancyTable[2].setText("al doilea text field");*/
        //fancyTable[1].g
        /*vb.getChildren().add(fancyTable[1]);
        vb.getChildren().add(new Label());
        vb.getChildren().add(fancyTable[2]);
        vb.getChildren().add(new Label());
        vb.getChildren().add(new Button("fff"));
        vb.getChildren().add(new VBox());
        vb.getChildren().add(new Label());*/
       // vb.getChildren().add(new AnchorPane().getBackground("green"));


        for(int i=1;i<=10;i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(new File(Algoritm.getFullPath("AnchorForTFancyTable.fxml")).toURI().toURL());

               //unu.setText("aici tre sa fie poza");
                fancyTable[i] = fxmlLoader.load();
                vb.getChildren().add(fancyTable[i]);
            } catch (IOException | InterruptedException | Exception e) {
                e.printStackTrace();
                MessageAlert.showErrorMessage(null, e.getMessage() + "\n" + e.getCause());
            }

        }


    }
}