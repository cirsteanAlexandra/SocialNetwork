package com.example.Domain;

import java.time.LocalDate;

public class Event  extends Entity<Long>{

    private String name, description;
    private LocalDate dtf;

    public Event(Long id,String name, String description, LocalDate dtf) {
        this.name = name;
        this.description = description;
        this.dtf = dtf;
        setId(id);
    }

    public LocalDate getDtf() {
        return dtf;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



    public void setDtf(LocalDate dtf) {
        this.dtf = dtf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dtf=" + dtf +
                '}';
    }
}
