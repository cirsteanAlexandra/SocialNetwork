package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Utils.Exceptions.Exception;
import com.example.Utils.Observer.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.time.LocalDate;

public class ProfileController implements Observer {
    public javafx.scene.control.Button add;


    private MainController cont;
    private User user;//cel care are trimite cere
    private User user2;//cel la care se trimte cererea

    @FXML
    public Button AddFriendButton;

    public void setProfileController(MainController cont, User user,User user2){
    this.cont=cont;
    this.user=user;
    this.user2=user2;
    cont.addObserver(this);
        String username= user2.getUsername();
        String status=cont.getStatusRequest(user.getUsername(), username);
        String status1=cont.getStatusRequest(username,user.getUsername());
        if(status!=null )
        {
            if(status.equals("accept"))
                add.setText("Friend");
            if(status.equals("reject"))
                add.setText("Add Friend");
            if(status.equals("pending"))
                add.setText("requested");
        }
        if(status==null)
            //add.setText("Add Friend");
        {

            if (status1 != null) {
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

    public void SetTextButtonAdd(){
        String username= user2.getUsername();
        String status=cont.getStatusRequest(user.getUsername(), username);
        String status1=cont.getStatusRequest(username,user.getUsername());
        if(status!=null )
        {
            if(status.equals("accept"))
                add.setText("Friend");
            if(status.equals("reject"))
                add.setText("Add Friend");
            if(status.equals("pending"))
                add.setText("requested");
        }
        if(status==null)
        //add.setText("Add Friend");
        {
            if (status1 != null) {
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
                add.setText("Requested");

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