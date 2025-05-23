package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.*;
import com.example.pdyc2025_junin_individual.repository.ArtistRepository;
import com.example.pdyc2025_junin_individual.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ArtistRepository artistRepository;

    public List<Event> getAll(EventState state) {
        return state != null ? eventRepository.findByState(state) : eventRepository.findAll();
    }

    public Optional<Event> get(Long id) {
        return eventRepository.findById(id);
    }

    public Event create(Event e) {
        e.setState(EventState.TENTATIVE);
        return eventRepository.save(e);
    }

    public Event update(Long id, String name, String desc, LocalDate date) {
        Event e = eventRepository.findById(id).orElseThrow();
        if (e.getState() != EventState.TENTATIVE) throw new RuntimeException("Only tentative events can be updated");
        e.setName(name);
        e.setDescription(desc);
        e.setStartDate(date);
        return eventRepository.save(e);
    }

    public void delete(Long id) {
        Event e = eventRepository.findById(id).orElseThrow();
        if (e.getState() == EventState.TENTATIVE) eventRepository.delete(e);
    }

    public Event addArtist(Long eventId, Long artistId) {
        Event e = eventRepository.findById(eventId).orElseThrow();
        if (e.getState() != EventState.TENTATIVE) throw new RuntimeException("Only tentative events can be edited");
        Artist a = artistRepository.findById(artistId).orElseThrow();
        if (!a.isActive()) throw new RuntimeException("Artist inactive");
        e.getArtists().add(a);
        return eventRepository.save(e);
    }

    public Event removeArtist(Long eventId, Long artistId) {
        Event e = eventRepository.findById(eventId).orElseThrow();
        if (e.getState() != EventState.TENTATIVE) throw new RuntimeException("Only tentative events can be edited");
        Artist a = artistRepository.findById(artistId).orElseThrow();
        e.getArtists().remove(a);
        return eventRepository.save(e);
    }

    public Event confirm(Long id) {
        Event e = eventRepository.findById(id).orElseThrow();
        if (e.getStartDate().isAfter(LocalDate.now())) e.setState(EventState.CONFIRMED);
        return eventRepository.save(e);
    }

    public Event reschedule(Long id, LocalDate date) {
        Event e = eventRepository.findById(id).orElseThrow();
        if ((e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED)
                && e.getStartDate().isAfter(LocalDate.now()) && date.isAfter(LocalDate.now())) {
            e.setStartDate(date);
            e.setState(EventState.RESCHEDULED);
        }
        return eventRepository.save(e);
    }

    public Event cancel(Long id) {
        Event e = eventRepository.findById(id).orElseThrow();
        if (e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED) {
            e.setState(EventState.CANCELED);
        }
        return eventRepository.save(e);
    }
}