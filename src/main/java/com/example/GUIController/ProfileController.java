package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Utils.Exceptions.Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.time.LocalDate;

public class ProfileController {
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
        String username= user2.getUsername();
        String status=cont.getStatusRequest(user.getUsername(), username);
        if(status!=null)
        {
            if(status.equals("accept"))
                add.setText("Friend");
            if(status.equals("reject"))
                add.setText("Add Friend");
            if(status.equals("pending"))
                add.setText("requested");
        }
        else add.setText("Add Friend");

}

    public void handleAddFriend(ActionEvent actionEvent) {
        String username= user2.getUsername();

            try {

                Relationship rel = new Relationship(user.getUsername(), username, LocalDate.now(), "pending");
                cont.AddRequest(rel);
                add.setText("Requested");
                // MessageAlert.showMessage(null,null,null, "cerere trimisa cu succes");
            } catch (Exception e) {
                // MessageAlert.showErrorMessage(null, e.getDescription());
                cont.removeRequestBySenderAndReceiver(user.getUsername(), username);
                add.setText("Add Friend");
            }

    }
}