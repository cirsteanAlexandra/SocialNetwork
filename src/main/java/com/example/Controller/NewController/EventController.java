package com.example.Controller.NewController;

import com.example.Domain.Event;
import com.example.Repository.Db.EventDbRepo;

import java.util.List;

public class EventController extends Controller<Long,Event>{
    private EventDbRepo backupRepo;
    public EventController(EventDbRepo repo) {
        super.repo=repo;
        backupRepo=repo;
    }

    public List<Event> getUserEvents(Long id){
        return backupRepo.getUserEvents(id);
    }
}