package com.example.pdyc2025_junin_individual.service;


import com.example.pdyc2025_junin_individual.model.*;
import com.example.pdyc2025_junin_individual.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAll(Genre genre) {
        return genre != null ? artistRepository.findByGenre(genre) : artistRepository.findAll();
    }

    public Artist create(Artist a) {
        return artistRepository.save(a);
    }

    public Optional<Artist> get(Long id) {
        return artistRepository.findById(id);
    }

    public Artist update(Long id, String name, Genre genre) {
        Artist a = artistRepository.findById(id).orElseThrow();
        a.setName(name);
        a.setGenre(genre);
        return artistRepository.save(a);
    }

    public void delete(Long id) {
        Artist a = artistRepository.findById(id).orElseThrow();
        if (a.getEvents().isEmpty()) artistRepository.delete(a);
        else {
            a.setActive(false);
            artistRepository.save(a);
        }
    }
}