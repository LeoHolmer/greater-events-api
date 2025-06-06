package com.example.pdyc2025_junin_individual.dto;

import java.time.LocalDate;
import java.util.List;

public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private String state;
    private List<ArtistDTO> artists;

    public String getName() {
        return name;
    }

    public List<ArtistDTO> getArtists() {
        return artists;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setArtists(List<ArtistDTO> artists) {
        this.artists = artists;
    }
}