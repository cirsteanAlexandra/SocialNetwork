package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Message;
import com.example.Domain.User;
import com.example.Utils.Observer.Observer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MessageGUIController implements Observer {
    private MainController cont;
    private Stage stage;
    private User user;

    private User otherPerson;

    public void setMessageGUIController (MainController cont, Stage stage, User user){
        this.cont=cont;
        this.stage=stage;
        this.user=user;
        initModel();
        //initConversation();
        cont.addObserver(this);
    }

    @FXML
    private TextArea textMess;

    @FXML
    private VBox mainSceneTExt;

    @FXML
    private TextField textFilterUser;

    @FXML
    private TableView<String> tableUsername;
    @FXML
    private TableColumn<String,String> columnUsername;

    private ObservableList<String> model = FXCollections.observableArrayList();

    private void initModel(){
        model.setAll(cont.getUserFriendsUsername(user.getUsername()));
    }

    public void loadTheConversation(){
        List<Message> conv=cont.loadConversation(user.getUsername(),otherPerson.getUsername());
        //mainSceneTExt.getChildren().clear();
        //mainSceneTExt.
        //mainSceneTExt.getChildren().clear();
        for (var mess:conv){
            HBox convo_mess=new HBox();
            //TextArea convo_mess=new TextArea();
            //Text Username1= new Text(mess.getFrom().getUsername()+": ");
            TextArea Username1= new TextArea(mess.getFrom().getUsername());
            Username1.setPadding(Insets.EMPTY);
            //Username1.setStyle("-fx-padding: 0,0,0,0");
            Username1.setEditable(false);
            //Text Username1=new Text(mess.getFrom().getUsername()+":");
            if (mess.getFrom().getUsername().equals(user.getUsername()))
                //Username1.setFill(Color.RED);
                Username1.setStyle("-fx-background-color:transparent; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: red; ");
            else if(mess.getFrom().getUsername().equals(otherPerson.getUsername()))
                //Username1.setFill(Color.GREEN);
                Username1.setStyle("-fx-background-color:rgba(0,0,0,0); -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: green; ");
           // Username1.setEditable(false);
            //Text textMess=new Text(mess.getMessage());
            TextArea Mess=new TextArea(mess.getMessage());
            Mess.setPadding(Insets.EMPTY);
            //Mess.setStyle("-fx-padding: 0,0,0,0");
            Mess.setEditable(false);
            Mess.setWrapText(true);
           // Mess.setBackground(Background.EMPTY);
            //Text Mess= new Text(mess.getMessage());
//            Text Date=new Text(mess.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")).toString()+":");
            TextArea Date=new TextArea( mess.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")).toString()+":");
            Date.setEditable(false);
            Date.setWrapText(true);
            Date.setPadding(Insets.EMPTY);
            Date.setBorder(Border.EMPTY);
            //Date.setStyle("-fx-padding: 0,0,0,0");
           // Date.setPrefSize(Date.getPrefWidth()/2,3*Date.getPrefHeight()/4);
           // mainSceneTExt.getChildren().addAll(Date,Username1,Mess);
           // mainSceneTExt.getChildren().add(new Text(System.lineSeparator()));
            convo_mess.getChildren().add(Date);
            convo_mess.getChildren().add(Username1);
            convo_mess.getChildren().add(Mess);
            convo_mess.setPadding(Insets.EMPTY);
            //convo_mess.setPrefSize(Mess.getWidth(),Mess.getHeight());

            mainSceneTExt.getChildren().add(convo_mess);
        }
    }

    @FXML
    private void initialize(){
        columnUsername.setCellValueFactory((p)->{ return new ReadOnlyStringWrapper(p.getValue());});
        tableUsername.setItems(model);

        textFilterUser.textProperty().addListener(o->handleFilterAction());
    }

    public void handleFilterAction(){
        Predicate<String> pred= u-> u.startsWith(textFilterUser.getText());
        model.setAll(cont.getUserFriendsUsername(user.getUsername())
                .stream()
                .filter(pred)
                .collect(Collectors.toList()));
    }

    public void handleSend(ActionEvent ev){
        String message=textMess.getText();
        if(otherPerson==null)
            MessageAlert.showErrorMessage(null,"There is no conversation to reply");
        else if (message.equals("") || message==null || message.isBlank())
            MessageAlert.showErrorMessage(null,"The text field is null");
        else{
            Message mess= new Message(user,message, Arrays.asList(otherPerson), LocalDateTime.now());
            cont.sendMessage(mess);
            textMess.clear();
            loadTheConversation();
        }
    }

    public void handleChoose(MouseEvent ev){
        String Usern=tableUsername.getSelectionModel().getSelectedItem();
        if(Usern==null)
            MessageAlert.showErrorMessage(null,"You haven't selected an user to chat with!");
        else
        {
            otherPerson=cont.getUserByUsername(Usern);
            loadTheConversation();
        }
    }

    public void handleExit(ActionEvent ev){
        stage.close();
    }


    @Override
    public void update() {
        initModel();
        loadTheConversation();
    }
}
