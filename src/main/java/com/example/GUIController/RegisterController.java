package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Controller.Validator.ContextValidator;
import com.example.Controller.Validator.Strategy;
import com.example.Controller.Validator.Validator;
import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Utils.Exceptions.Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    private MainController cont;
    Stage registerStage;
    public void setRegisterController(MainController cont, Stage stage) {
        this.cont = cont;
        this.registerStage=stage;
    }

    @FXML
    private TextField textUsername;
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textLastName;

    public void handleRegister(ActionEvent ev){
        try{
            String Username = textUsername.getText();
            String fN = textFirstName.getText();
            String lN = textLastName.getText();
            User user;
            user=new User(Username,new Persone(fN,lN));
            Validator vali= ContextValidator.createValidator(Strategy.USER);
            vali.validate(user);
            cont.addUser(user);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Registration","The User has been added succefully!");
        }catch(Exception e){

            MessageAlert.showErrorMessage(null, e.getDescription());
        }
        catch(NullPointerException e){
            //e.printStackTrace();
            MessageAlert.showErrorMessage(null, "The text field must contain an username!");
        }
        finally {
            registerStage.close();
        }
    }
    public void handleCancel(ActionEvent ev){
            registerStage.close();
    }
}
