package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class UserGuiController {
    private MainController cont;

    @FXML
    private TextField FirstNameUser;

    @FXML
    private TextField LastNameUser;

    public void setUserGuiController(MainController cont,User user) {
        this.cont = cont;
        this.user=user;
        textUsername.setText(user.getUsername());
        FirstNameUser.setText(user.getPers().getFirstName());
        LastNameUser.setText(user.getPers().getLastName());
    }

    @FXML
    private TextField textUsername;


    @FXML
    private Label LabelUsername;

    private User user;
    public void handleTest(ActionEvent ev){
        showTestTableLayout();
    }



    public void showTestTableLayout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("requests_table_view.fxml")).toURI().toURL());
            AnchorPane loginLayout = fxmlLoader.load();
            String Username = textUsername.getText();
            Stage registerStage = new Stage();
            RequestsTableController requestController = fxmlLoader.getController();
            requestController.setRequestsController(cont,registerStage,cont.getUserByOther(Username));
            Scene scene = new Scene(loginLayout);
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setTitle("Requests");
            registerStage.setScene(scene);
            registerStage.show();
        }catch(IOException | InterruptedException | Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }
    }


    public void handleTestFriends(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("friends_table_view.fxml")).toURI().toURL());
            AnchorPane loginLayout = fxmlLoader.load();
            String Username = textUsername.getText();
            Stage registerStage = new Stage();
            FriendsTableController requestController = fxmlLoader.getController();
            requestController.setFriendsController(cont,registerStage,cont.getUserByOther(Username));
            Scene scene = new Scene(loginLayout);
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setTitle("Friends");
            registerStage.setScene(scene);
            registerStage.show();
        }catch(IOException | InterruptedException | Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }

    }


}
