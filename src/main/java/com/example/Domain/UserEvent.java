package com.example.Domain;

public class UserEvent extends Entity<Long> {
    Long id_user;
    Long id_event;

    public UserEvent(Long id_user, Long id_event) {
        this.id_user = id_user;
        this.id_event = id_event;
    }

    public Long getId_user() {
        return id_user;
    }

    public Long getId_event() {
        return id_event;
    }
}
