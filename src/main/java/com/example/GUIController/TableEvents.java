package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Event;
import com.example.Domain.User;

import com.example.Repository.PagingRepo.PageType;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import com.example.Utils.Observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class TableEvents  implements Observer {



    public VBox vertical_box;
    private MainController cont;
    private AnchorPane[] eventsTable;
    private User user;

    Stage registerStage;


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

    @FXML
    public HBox hboxf2,hboxf1,hboxf3;




    public void  set(MainController cont,User user) {
        this.cont = cont;
        this.user=user;

        eventsTable =new AnchorPane[4];

        //eventsTable =new AnchorPane[4];
        cont.addObserver(this);
        updateListEvents(PageType.CURRENT);

        SetEventsTable();

    }


    //ObservableList<PrintedPersones> model = FXCollections.observableArrayList();

    private List<Event> events;


    private void SetEventsTable() {

        //probabil ceva for, nu s prea sigura cum vine cu paginarea
        //Lista de event

        List<Event> events=cont.getAllEvents();


       // =cont.getAllEvents();
            if(!events.isEmpty())

           for(int i=1;i<=3;i++)
           {
               if(i==1 && i<events.size())
               {
                   Event ev=events.get(i-1);
                   NameEvent1.setText(ev.getName());
                   Date_event1.setText(ev.getDtf().toString());
                   DecriptionEvent1.setText(ev.getDescription());


                   Participate1.setText("Cancel");

                   Notify1.setText("Notify me!");

               }
               if(i==2 && i<events.size())
               {
                   Event ev=events.get(i-1);
                   NameEvent2.setText(ev.getName());
                   Date_event2.setText(ev.getDtf().toString());
                   DecriptionEvent2.setText(ev.getDescription());

                   Participate2.setText("Cancel");

                   Notify2.setText("Notify me!");

               }
               if(i==3 && i<events.size())
               {
                   Event ev=events.get(i-1);
                   NameEvent3.setText(ev.getName());
                   Date_event3.setText(ev.getDtf().toString());
                   DecriptionEvent3.setText(ev.getDescription());

                   Participate3.setText("Cancel");

                   Notify3.setText("Notify me!");

               }

               }
        for(int i=events.size()+1;i<=3;i++){

            if(i==1)hboxf1.setVisible(false);
            if(i==2)hboxf2.setVisible(false);
            if(i==3)hboxf3.setVisible(false);


           }

    }


    public void handleEventPreviousPage(ActionEvent ev){
        if(events.size()==3){
            updateListEvents(PageType.PREVIOUS);
        }

    }

    public void handleRequestNextPage(ActionEvent ev){
        if(events.size()==3){
            updateListEvents(PageType.NEXT);
        }
    }



    public void updateListEvents( PageType type){
        events=cont.getPageEvents(type);
        SetEventsTable();
    }



    @Override
    public void update() {
        updateListEvents(PageType.CURRENT);

    }


}
