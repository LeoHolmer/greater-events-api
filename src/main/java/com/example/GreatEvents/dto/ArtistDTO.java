package com.example.GreatEvents.dto;

import com.example.GreatEvents.model.Genre;

public class ArtistDTO {
    private Long id;
    private String name;
    private Genre genre;
    private boolean active;

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public Long getId() {
        return id;
    }
}