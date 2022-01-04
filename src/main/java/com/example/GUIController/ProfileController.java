package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Utils.Exceptions.Exception;
import com.example.Utils.Observer.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.awt.*;
import java.time.LocalDate;

public class ProfileController implements Observer {
    public javafx.scene.control.Button add;
    public javafx.scene.control.TextField a,b,c;


    private MainController cont;
    private User user;//cel care are trimite cere
    private User user2;//cel la care se trimte cererea

    @FXML
    public Button AddFriendButton;

    /*@FXML
    private TextField b;
    @FXML
    private TextField c;
    @FXML
    private TextField a;
    @FXML
    public TextField ceva;*/

    public void setProfileController(MainController cont, User user,User user2){
    this.cont=cont;
    this.user=user;
    this.user2=user2;

    a.setText(user2.getUsername());
    b.setText(user2.getPers().getFirstName());
    c.setText((user2.getPers().getLastName()));
    cont.addObserver(this);
       SetTextButtonAdd();
}

    public void SetTextButtonAdd(){
        String username= user2.getUsername();
        String status=cont.getStatusRequest(user.getUsername(), username);
        String status1=cont.getStatusRequest(username,user.getUsername());
        if(status!=null ) // in tabela de request  exista o cerere A a trimis la B
        {
            if(status.equals("accept")) // daca e acceptata
                add.setText("Friend");
            if(status.equals("reject"))
                add.setText("Add Friend");
            if(status.equals("pending"))
                add.setText("requested");
        }
        if(status==null) // A nu a trimis la B
        {
            if (status1 != null) { // dar B a trimis la A
                if (status1.equals("accept"))
                    add.setText("Friend");
                if (status1.equals("reject"))
                    add.setText("Add Friend");
                if (status1.equals("pending"))
                    add.setText("reply");
            }
            else add.setText("Add Friend");
        }
    }

    public void handleAddFriend(ActionEvent actionEvent) {
        String username= user2.getUsername();

            try {

                Relationship rel = new Relationship(user.getUsername(), username, LocalDate.now(), "pending");
                cont.AddRequest(rel);
               // add.setText("Requested");
                SetTextButtonAdd();

            } catch (Exception e) {

                    if(!add.getText().equals("reply"))
                    {
                        if(cont.GetTheSender(user.getUsername())) {
                            cont.removeRequestBySenderAndReceiver(user.getUsername(), username);
                            if(cont.existedFriendship(user.getUsername(), username))
                                cont.removeRelationshipByUsernames(user.getUsername(), username);
                        }
                        else
                        {
                            cont.removeRequestBySenderAndReceiver(username,user.getUsername());
                            if(cont.existedFriendship(user.getUsername(), username))
                                cont.removeRelationshipByUsernames(username,user.getUsername());
                        }
                        add.setText("Add Friend");
                    }
            }

    }

    @Override
    public void update() {
        SetTextButtonAdd();
    }
}