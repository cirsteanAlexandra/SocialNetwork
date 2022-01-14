package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Repository.PagingRepo.PageType;
import com.example.Utils.Exceptions.Exception;
import com.example.Utils.Observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class FriendsTableController implements Observer {
    User user;
    private MainController cont;
    Stage registerStage;

    public void setFriendsController(MainController cont, Stage stage, User user) {
        this.cont = cont;
        this.registerStage = stage;
        this.user = user;
        cont.addObserver(this);
        updateListFriend(PageType.CURRENT);
        updateListRequest(PageType.CURRENT);
        //initModel();
    }

    ObservableList<PrintedPersones> model = FXCollections.observableArrayList();

    @FXML
    private Label flname1;
    @FXML
    private Label flname2;
    @FXML
    private Label flname3;
    @FXML
    private Label flname4;

    @FXML
    private Label flnamer1;
    @FXML
    private Label flnamer2;
    @FXML
    private Label flnamer3;
    @FXML
    private Label flnamer4;

    @FXML
    private Label uname1;
    @FXML
    private Label uname2;
    @FXML
    private Label uname3;
    @FXML
    private Label uname4;

    @FXML
    private Label unamer1;
    @FXML
    private Label unamer2;
    @FXML
    private Label unamer3;
    @FXML
    private Label unamer4;

    @FXML
    private HBox hboxf1;
    @FXML
    private HBox hboxf2;
    @FXML
    private HBox hboxf3;
    @FXML
    private HBox hboxf4;

    @FXML
    private HBox hboxr1;
    @FXML
    private HBox hboxr2;
    @FXML
    private HBox hboxr3;
    @FXML
    private HBox hboxr4;

    private List<User> listU;
    private List<User> listR;

    public void printFriends(){
        if(!listU.isEmpty())
        for(int i=1;i<=4;i++){
            if(i==1 && i<=listU.size() ){
                User use=listU.get(i-1);
                flname1.setText(user.getPers().getFirstName()+" "+use.getPers().getLastName());
                uname1.setText(use.getUsername());
            }
            if(i==2 && i<=listU.size() ){
                User use=listU.get(i-1);
                flname2.setText(user.getPers().getFirstName()+" "+use.getPers().getLastName());
                uname2.setText(use.getUsername());
            }
            if(i==3 && i<=listU.size() ){
                User use=listU.get(i-1);
                flname3.setText(user.getPers().getFirstName()+" "+use.getPers().getLastName());
                uname3.setText(use.getUsername());
            }
            if(i==4 && i<=listU.size() ){
                User use=listU.get(i-1);
                flname4.setText(user.getPers().getFirstName()+" "+use.getPers().getLastName());
                uname4.setText(use.getUsername());
            }
        }
        for(int i=listU.size()+1;i<=4;i++){
            if(i==1)hboxf1.setVisible(false);
            if(i==2)hboxf2.setVisible(false);
            if(i==3)hboxf3.setVisible(false);
            if(i==4)hboxf4.setVisible(false);
        }
    }

    public void printRequests(){
        if(!listR.isEmpty())
        for(int i=1;i<=4;i++){
            if(i==1 && i<=listR.size() ){
                User use=listR.get(i-1);
                flnamer1.setText(user.getPers().getFirstName()+" "+use.getPers().getLastName());
                unamer1.setText(use.getUsername());
            }
            if(i==2 && i<=listR.size() ){
                User use=listR.get(i-1);
                flnamer2.setText(user.getPers().getFirstName()+" "+use.getPers().getLastName());
                unamer2.setText(use.getUsername());
            }
            if(i==3 && i<=listR.size() ){
                User use=listR.get(i-1);
                flnamer3.setText(user.getPers().getFirstName()+" "+use.getPers().getLastName());
                unamer3.setText(use.getUsername());
            }
            if(i==4 && i<=listR.size() ){
                User use=listR.get(i-1);
                flnamer4.setText(user.getPers().getFirstName()+" "+use.getPers().getLastName());
                unamer4.setText(use.getUsername());
            }
        }
        for(int i=listR.size()+1;i<=4;i++){
            if(i==1)hboxr1.setVisible(false);
            if(i==2)hboxr2.setVisible(false);
            if(i==3)hboxr3.setVisible(false);
            if(i==4)hboxr4.setVisible(false);
        }
    }

    public void handleRemoveFriend1(ActionEvent actionEvent) {
        String username=uname1.getText();
        remove(username);
    }
    public void handleRemoveFriend2(ActionEvent actionEvent) {
        String username=uname1.getText();
        remove(username);
    }
    public void handleRemoveFriend3(ActionEvent actionEvent) {
        String username=uname1.getText();
        remove(username);
    }
    public void handleRemoveFriend4(ActionEvent actionEvent) {
        String username=uname1.getText();
        remove(username);
    }

    public void handleAcceptFriend1(ActionEvent actionEvent) {
        String username=uname1.getText();
        accept(username);
    }
    public void handleAcceptFriend2(ActionEvent actionEvent) {
        String username=uname1.getText();
        accept(username);
    }
    public void handleAcceptFriend3(ActionEvent actionEvent) {
        String username=uname1.getText();
        accept(username);
    }
    public void handleAcceptFriend4(ActionEvent actionEvent) {
        String username=uname1.getText();
        accept(username);
    }

    public void handleRejectFriend1(ActionEvent actionEvent) {
        String username=uname1.getText();
        reject(username);
    }
    public void handleRejectFriend2(ActionEvent actionEvent) {
        String username=uname1.getText();
        reject(username);
    }
    public void handleRejectFriend3(ActionEvent actionEvent) {
        String username=uname1.getText();
        reject(username);
    }
    public void handleRejectFriend4(ActionEvent actionEvent) {
        String username=uname1.getText();
        reject(username);
    }

    public void remove(String username){
        try {
            //Relationship rel = new Relationship(user.getUsername(), username, LocalDate.now(), "pending");
            cont.removeRelationshipByUsernames(username,user.getUsername());
        } catch (Exception e) {
            MessageAlert.showErrorMessage(registerStage,e.getDescription());
        }
    }

    public void accept(String username){
        try {
            Relationship rel = new Relationship(user.getUsername(), username, LocalDate.now(), "pending");
            cont.AddRequest(rel);
        } catch (Exception e) {
            MessageAlert.showErrorMessage(registerStage,e.getDescription());
        }
    }

    public void reject(String username){
        try {
            cont.UpdateStatusRequest("accept",username,user.getUsername());
        } catch (Exception e) {
            MessageAlert.showErrorMessage(registerStage,e.getDescription());
        }
    }

    public void handleFriendNextPage(ActionEvent ev){
        if(listU.size()==4){
            updateListFriend(PageType.NEXT);
        }
    }

    public void handleFriendPreviousPage(ActionEvent ev){
            updateListFriend(PageType.PREVIOUS);
    }

    public void handleRequestNextPage(ActionEvent ev){
        if(listR.size()==4){
            updateListRequest(PageType.NEXT);
        }
    }

    public void handleRequestPreviousPage(ActionEvent ev){
        updateListRequest(PageType.PREVIOUS);
    }

    public void updateListFriend( PageType type){
        listU=cont.getPageFriends(user.getUsername(), type);
        printFriends();
    }

    public void updateListRequest( PageType type){
        listR=cont.getPageRequests(user.getUsername(), type);
        printRequests();
    }

    @Override
    public void update() {
        updateListFriend(PageType.CURRENT);
        updateListRequest(PageType.CURRENT);
    }

    /*
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
    */
}