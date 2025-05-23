package com.example.pdyc2025_junin_individual.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private boolean active = true;

    @ManyToMany(mappedBy = "artists")
    private Set<Event> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Event> getEvents() {
        return events;
    }

}
