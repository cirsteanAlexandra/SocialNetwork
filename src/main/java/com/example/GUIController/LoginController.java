package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LoginController {
    private MainController cont;

    public void setLoginController(MainController cont) {
        this.cont = cont;
    }

    @FXML
    private TextField textUsername;

    public void handleLoginAction(ActionEvent ev){
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Feature not yet implemented","The menu for admin is not yet available!");
        try {
            String Username = textUsername.getText();
            if (Username.equals("admin"))
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Feature not yet implemented", "The menu for admin is not yet available!");
            if(Username.equals("") || Username==null ||Username.isEmpty())
                MessageAlert.showErrorMessage(null, "The text field must contain an username!");
            else {
                User user = cont.getUserByUsername(Username);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Feature not yet implemented", "The menu for user is not yet available!");
            }
        }
        catch(Exception e){
            MessageAlert.showErrorMessage(null, e.getDescription());
        }
        catch(NullPointerException e){
            MessageAlert.showErrorMessage(null, "The text field must contain an username!");
        }

    }

    public void handleRegisterAction(ActionEvent ev){
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Feature not yet implemented","The register is not yet available!");
        showRegistrationLayout();
    }

    public void showRegistrationLayout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("register_view.fxml")).toURI().toURL());
            AnchorPane loginLayout = fxmlLoader.load();

            Stage registerStage = new Stage();
            RegisterController registerController = fxmlLoader.getController();
            registerController.setRegisterController(cont,registerStage);
            Scene scene = new Scene(loginLayout);
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setTitle("Registration");
            registerStage.setScene(scene);
            registerStage.show();
        }catch(IOException | InterruptedException e){
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }
    }

}
