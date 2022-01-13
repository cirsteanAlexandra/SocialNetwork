package com.example.Controller.NewController;

import com.example.Domain.Event;
import com.example.Repository.Db.EvenDbRepo;

public class EventController extends Controller<Long, Event> {
    public EventController(EvenDbRepo repo) {
        super.repo = repo;
    }

}
