package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainWindowController {
    private MainController cont;
    private Stage stage;
    AnchorPane loginLayout;
    User user;
    @FXML
    private Button HomeButton,MessagesButton,EventsButton,StatisticsButton,PeopleButton,ExitButton;

    public void setMainWindowController(MainController cont,Stage stage,AnchorPane loginLayout,User user) {
        this.user=user;
        this.cont = cont;
        this.stage=stage;
        this.loginLayout=loginLayout;
        //SetButtonsImage(HomeButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\HomeIcon.png");
        //SetButtonsImage(MessagesButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\MessagesIcon2.png");
        //SetButtonsImage(PeopleButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\People2.png");
//        SetButtonsImage(EventsButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\EventIcon.png");
//        SetButtonsImage(StatisticsButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\StatisticsIcon.png");
//        SetButtonsImage(ExitButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\ExitIcon.png");

    }
    private void SetButtonsImage(Button NameButton,String url){
        Image img = new Image(url);
        javafx.scene.image.ImageView view = new ImageView(img);
        view.setFitHeight(23);
        view.setPreserveRatio(true);
        NameButton.setGraphic(view);
    }

    @FXML
    private AnchorPane currentLayout;
    @FXML
    private SplitPane splitMain;
    @FXML
    private TextField textSearch;

    public void handleHome(ActionEvent ev) throws IOException, InterruptedException {
        //System.out.println("HOME PROFILE");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("FinalFirstPage.fxml")).toURI().toURL());
        AnchorPane messLayout = fxmlLoader.load();
        splitMain.getItems().set(0,messLayout);
        Stage registerStage = new Stage();
        System.out.println(cont.toString());
        MainWindowController mainWindowController = fxmlLoader.getController();
        String Username=user.getUsername();

        mainWindowController.setMainWindowController(cont,registerStage,loginLayout,cont.getUserByUsername(Username));
    }

    public void handleSearch(ActionEvent ev) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("search-view.fxml")).toURI().toURL());
        AnchorPane seaLayout = fxmlLoader.load();
        splitMain.getItems().set(1, seaLayout);
        SearchGUIController seaController = fxmlLoader.getController();
        System.out.println(textSearch.getText());
        seaController.setSearchGUIController(cont,user,splitMain,textSearch);
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
        friendsTableController.setFriendsController(cont,stage,user,splitMain);

    }
    public void handleEvents(ActionEvent ev) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("ceva_de_proba.fxml")).toURI().toURL());
        AnchorPane friendLayout = fxmlLoader.load();
        splitMain.getItems().set(1,friendLayout);
        TableEvents tableEvents=fxmlLoader.getController();
        tableEvents.set(cont,user);

    }
    public void handleStatistics(ActionEvent ev) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("statistics-view.fxml")).toURI().toURL());
        AnchorPane statLayout=fxmlLoader.load();
        splitMain.getItems().set(1,statLayout);
        StatisticsGUIController statCont=fxmlLoader.getController();
        statCont.setStatisticsGUIController(cont,user);
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
