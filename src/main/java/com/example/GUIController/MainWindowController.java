package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainWindowController {
    private MainController cont;
    private Stage stage;
    AnchorPane loginLayout;
    User user;

    public void setMainWindowController(MainController cont,Stage stage,AnchorPane loginLayout,User user) {
        this.user=user;
        this.cont = cont;
        this.stage=stage;
        this.loginLayout=loginLayout;
    }

    @FXML
    private AnchorPane currentLayout;
    @FXML
    private SplitPane splitMain;

    public void handleHome(ActionEvent ev){
        System.out.println("HOME PROFILE");
    }

    public void handleMessage(ActionEvent ev) throws IOException, InterruptedException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("message_view.fxml")).toURI().toURL());
            AnchorPane messLayout = fxmlLoader.load();
            splitMain.getItems().set(1, messLayout);
            MessageGUIController messageController = fxmlLoader.getController();
            messageController.setMessageGUIController(cont, stage, user);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void handleFriends(ActionEvent ev) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("friends_requests.fxml")).toURI().toURL());
        AnchorPane friendLayout = fxmlLoader.load();
        splitMain.getItems().set(1,friendLayout);
        FriendsTableController friendsTableController = fxmlLoader.getController();
        friendsTableController.setFriendsController(cont,stage,user);

    }
    public void handleEvents(ActionEvent ev){
        System.out.println("EVENTS PROFILE");
    }
    public void handleStatistics(ActionEvent ev){
        System.out.println("STATISTICS PROFILE");
    }

    public void handleLogout(ActionEvent ev) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("login_view.fxml")).toURI().toURL());
        AnchorPane loginLayout=fxmlLoader.load();
        LoginController loginController= fxmlLoader.getController();

        Stage loginStage=new Stage();
        Scene scene = new Scene(loginLayout);
        loginController.setLoginController(cont,loginStage,loginLayout);
        loginStage.setTitle("Login");
        loginStage.setScene(scene);
        loginStage.show();
        stage.close();
    }
}
