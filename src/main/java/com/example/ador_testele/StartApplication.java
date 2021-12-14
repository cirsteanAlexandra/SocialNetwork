package com.example.ador_testele;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader(
                new File("C:\\Users\\Alexandra\\Desktop\\anul 2\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\first_try.fxml")
                        .toURI().toURL()
        );
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage f=new Stage();
        f.setTitle("Hello");
        f.setScene(scene);
        f.show();



    }

    public static void main(String[] args) {

        launch();
    }


}
