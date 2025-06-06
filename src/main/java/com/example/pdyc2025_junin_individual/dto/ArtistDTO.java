package com.example.pdyc2025_junin_individual.dto;

import com.example.pdyc2025_junin_individual.model.Genre;

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