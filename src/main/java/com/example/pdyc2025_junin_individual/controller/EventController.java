package com.example.pdyc2025_junin_individual.controller;

import com.example.pdyc2025_junin_individual.model.*;
import com.example.pdyc2025_junin_individual.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/events")
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping
    public List<Event> getAll(@RequestParam(required = false) EventState state) {
        return service.getAll(state);
    }

    @GetMapping("/{id}")
    public Optional<Event> get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Event create(@RequestBody Event e) {
        return service.create(e);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event e) {
        return service.update(id, e.getName(), e.getDescription(), e.getStartDate());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/artists")
    public Event addArtist(@PathVariable Long id, @RequestBody Artist a) {
        return service.addArtist(id, a.getId());
    }

    @DeleteMapping("/{id}/artist/{artistId}")
    public Event removeArtist(@PathVariable Long id, @PathVariable Long artistId) {
        return service.removeArtist(id, artistId);
    }

    @PutMapping("/{id}/confirmed")
    public Event confirm(@PathVariable Long id) {
        return service.confirm(id);
    }

    @PutMapping("/{id}/rescheduled")
    public Event reschedule(@PathVariable Long id, @RequestBody Event e) {
        return service.reschedule(id, e.getStartDate());
    }

    @PutMapping("/{id}/canceled")
    public Event cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}