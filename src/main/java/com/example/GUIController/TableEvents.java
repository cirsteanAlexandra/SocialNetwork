package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Event;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

    @FXML
    private Button Participate1,Participate2,Participate3;
    @FXML
    private TextField Date_event1,Date_event2,Date_event3;
    @FXML
    private Button Notify1,Notify2,Notify3;
    @FXML
    private ImageView ImageEvent1,ImageEvent2,ImageEvent3;
    @FXML
    public TextField NameEvent1,NameEvent2,NameEvent3;
    @FXML
    public TextArea DecriptionEvent1,DecriptionEvent2,DecriptionEvent3;



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

           for(int i=1;i<=3;i++)
           {
               if(i==1 && i<events.size())
               {
                   Event ev=events.get(i-1);
                   NameEvent1.setText(ev.getName());
                   Date_event1.setText(ev.getDtf().toString());
                   DecriptionEvent1.setText(ev.getDescription());
                   Participate1.setText("participate");
                   Notify1.setText("Notify me!");

               }
               if(i==2 && i<events.size())
               {
                   Event ev=events.get(i-1);
                   NameEvent2.setText(ev.getName());
                   Date_event2.setText(ev.getDtf().toString());
                   DecriptionEvent2.setText(ev.getDescription());
                   Participate2.setText("participate");
                   Notify2.setText("Notify me!");

               }
               if(i==3 && i<events.size())
               {
                   Event ev=events.get(i-1);
                   NameEvent3.setText(ev.getName());
                   Date_event3.setText(ev.getDtf().toString());
                   DecriptionEvent3.setText(ev.getDescription());
                   Participate3.setText("participate");
                   Notify3.setText("Notify me!");

               }

               }
        for(int i=events.size()+1;i<=3;i++){
           // if(i==1)hboxf1.setVisible(false);
            //if(i==2)hboxf2.setVisible(false);
            //if(i==3)hboxf3.setVisible(false);

           }

    }


}
