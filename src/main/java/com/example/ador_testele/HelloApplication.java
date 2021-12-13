package com.example.ador_testele;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("C:\\Users\\Alexandra\\Desktop\\anul 2\\LastTry\\src\\main\\resources\\com\\example\\ador_testele"));

        FXMLLoader fxmlLoader=new FXMLLoader(
                new File("C:\\Users\\Alexandra\\Desktop\\anul 2\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\hello-view.fxml")
                        .toURI().toURL()
        );
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}