package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RequestsTableController {
    User user;
    private MainController cont;
    Stage registerStage;
    public void setRequestsController(MainController cont, Stage stage,User user) {
        this.cont = cont;
        this.registerStage=stage;
        this.user=user;
        initModel();
    }
    ObservableList<PrintedRelationship> model = FXCollections.observableArrayList();

    @FXML
    private TableView<PrintedRelationship> tableView;
    @FXML
    private TableColumn<PrintedRelationship,String> tableColumnFrom;
    @FXML
    private TableColumn<PrintedRelationship, LocalDate> tableColumnDate;
    @FXML
    private TextField username;

    @FXML
    public void initialize() {
        tableColumnFrom.setCellValueFactory(new PropertyValueFactory<PrintedRelationship, String>("From"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<PrintedRelationship, LocalDate>("Date"));
        tableView.setItems(model);
    }

    public void initModel(){
        //System.out.println(cont.RequestsForAUser(user.getUsername()));
        List<Relationship> requests=cont.RequestsForAUser(user.getUsername());
        model.setAll(requests.stream()
                .map(x-> new PrintedRelationship(x.getFirstUserName(),x.getDtf()))
                .collect(Collectors.toList()));

    }
}
