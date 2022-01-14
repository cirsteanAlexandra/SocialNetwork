package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Event;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TableEvents {

    public VBox vertical_box;
    private MainController cont;
    private AnchorPane[] eventsTable;
    private User user;

    public void  set(MainController cont,User user) {
        this.cont = cont;
        this.user=user;
        eventsTable =new AnchorPane[4];
        SetEventsTable();

    }

    private void SetEventsTable() {

        //probabil ceva for, nu s prea sigura cum vine cu paginarea
        //Lista de event
        List<Event> events=cont.getAllEvents();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(new File(Algoritm.getFullPath("Event_view.fxml")).toURI().toURL());
                eventsTable[1]=fxmlLoader.load();
                EventsPageController eventsPageController=fxmlLoader.getController();
                eventsPageController.setEventPageController(cont,events.get(0),user);
                vertical_box.getChildren().add(eventsTable[1]);


            }
            catch (IOException | InterruptedException | Exception e) {
                e.printStackTrace();
                MessageAlert.showErrorMessage(null, e.getMessage() + "\n" + e.getCause());
            }
    }


}
