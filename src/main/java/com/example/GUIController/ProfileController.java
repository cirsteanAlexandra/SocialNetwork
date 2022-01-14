package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import com.example.Utils.Observer.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.*;

public class ProfileController implements Observer {
    private MainController cont;
    private User user;//cel care are trimite cere
    private User other;

    @FXML
    private TextField textF;
    @FXML
    private TextField textL;
    @FXML
    private TextField textU;
    @FXML
    private TextField textNoF;
    @FXML
    private TextField textNoE;
    @FXML
    private TextField textData;

    @FXML
    private Label labelFr;

    @FXML
    private Label butR;
    @FXML
    private Label butL;

    public void setProfileController(MainController cont, User user,User user2){
        this.cont=cont;
        this.user=user;
        this.other=user2;
        cont.addObserver(this);
    }

    @Override
    public void update() {

    }
}