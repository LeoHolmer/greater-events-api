package com.example.pdyc2025_junin_individual.controller;

import com.example.pdyc2025_junin_individual.model.*;
import com.example.pdyc2025_junin_individual.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/artists")
public class ArtistController {
    @Autowired
    private ArtistService service;

    @GetMapping
    public List<Artist> getAll(@RequestParam(required = false) Genre genre) {
        return service.getAll(genre);
    }

    @GetMapping("/{id}")
    public Optional<Artist> get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Artist create(@RequestBody Artist a) {
        return service.create(a);
    }

    @PutMapping("/{id}")
    public Artist update(@PathVariable Long id, @RequestBody Artist a) {
        return service.update(id, a.getName(), a.getGenre());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}