package com.example.Controller.NewController;

import com.example.Domain.Event;
import com.example.Repository.Db.EventDbRepo;

public class EventController extends Controller<Long,Event>{
    public EventController(EventDbRepo repo) {
        super.repo=repo;
    }
}