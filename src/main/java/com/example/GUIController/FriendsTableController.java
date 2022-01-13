package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Persone;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Utils.Observer.Observable;
import com.example.Utils.Observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FriendsTableController implements Observer {
    User user;
    private MainController cont;
    Stage registerStage;

    public void setFriendsController(MainController cont, Stage stage, User user) {
        this.cont = cont;
        this.registerStage = stage;
        this.user = user;
        cont.addObserver(this);
        initModel();
    }

    ObservableList<PrintedPersones> model = FXCollections.observableArrayList();

    @FXML
    private TableView<PrintedPersones> tableView;
    @FXML
    private TableColumn<PrintedPersones, String> tableColumnName;
    @FXML
    private TableColumn<PrintedPersones, String> tableColumnLastName;
    @FXML
    private TextField username;



    @FXML
    private TextField SearchFriend;

    @FXML
    private Button deleteButton;

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<PrintedPersones, String>("First_Name"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<PrintedPersones, String>("Last_Name"));
        tableView.setItems(model);

    }

    public void initModel() {

        Set<Persone> persones = cont.getFriendsByUsername(user.getUsername()).keySet();

        model.setAll(persones.stream()
                .map(x -> new PrintedPersones(x.getFirstName(), x.getLastName()))
                .collect(Collectors.toList()));

    }

    public void handleDeleteFriend(ActionEvent ev) {
        String LastName=tableView.getSelectionModel().getSelectedItem().getLast_Name();
        String userName= user.getUsername();
        String userName2= cont.getUsernameByFirstName(LastName);

        if(cont.GetTheSender(userName)) {
            cont.removeRelationshipByUsernames(userName, userName2);
            cont.removeRequestBySenderAndReceiver(userName, userName2);
            //notify();
        }
        else
        {
            cont.removeRelationshipByUsernames(userName2, userName);
            cont.removeRequestBySenderAndReceiver(userName2, userName);
           // notify();
        }

    }


    public void handleSearch(ActionEvent actionEvent) {


        String searchFriendUser= SearchFriend.getText();
        List<Persone> list=new ArrayList<>();
        for(Persone p: cont.getFriendsByUsername(user.getUsername()).keySet())
            if(p.getFirstName().equals(searchFriendUser)
            || p.getLastName().equals(searchFriendUser))
                list.add(p);

        model.removeAll();
        model.setAll(list.stream()
                .map(x -> new PrintedPersones(x.getFirstName(), x.getLastName()))
                .collect(Collectors.toList()));
        initModel();

    }

    @Override
    public void update() {
        initModel();
    }
}