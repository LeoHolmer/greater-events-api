package com.example.pdyc2025_junin_individual.controller;

import com.example.pdyc2025_junin_individual.dto.ArtistDTO;
import com.example.pdyc2025_junin_individual.dto.EventDTO;
import com.example.pdyc2025_junin_individual.model.Artist;
import com.example.pdyc2025_junin_individual.model.EventState;
import com.example.pdyc2025_junin_individual.repository.ArtistRepository;
import com.example.pdyc2025_junin_individual.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private ModelMapper modelMapper;

    @GetMapping("/artists")
    public List<ArtistDTO> getActiveArtists() {
        return artistRepository.findAll().stream()
                .filter(Artist::isActive)
                .map(a -> modelMapper.map(a, ArtistDTO.class))
                .toList();
    }

    @GetMapping("/artists/{id}/events")
    public List<EventDTO> getArtistUpcomingEvents(@PathVariable Long id) {
        return eventRepository.findAll().stream()
                .filter(e -> e.getArtists().stream().anyMatch(a -> a.getId().equals(id)))
                .filter(e -> e.getStartDate().isAfter(LocalDate.now()) || e.getStartDate().isEqual(LocalDate.now()))
                .filter(e -> e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED)
                .map(e -> {
                    EventDTO dto = modelMapper.map(e, EventDTO.class);
                    dto.setState(e.getState().name());
                    dto.setArtists(e.getArtists().stream().map(a -> modelMapper.map(a, ArtistDTO.class)).toList());
                    return dto;
                })
                .toList();
    }

    @GetMapping("/events")
    public List<EventDTO> getPublicEvents() {
        return eventRepository.findAll().stream()
                .filter(e -> e.getStartDate().isAfter(LocalDate.now()) || e.getStartDate().isEqual(LocalDate.now()))
                .filter(e -> e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED)
                .map(e -> {
                    EventDTO dto = modelMapper.map(e, EventDTO.class);
                    dto.setState(e.getState().name());
                    dto.setArtists(e.getArtists().stream().map(a -> modelMapper.map(a, ArtistDTO.class)).toList());
                    return dto;
                })
                .toList();
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> getEventDetails(@PathVariable Long id) {
        return eventRepository.findById(id)
                .filter(e -> e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED)
                .map(e -> {
                    EventDTO dto = modelMapper.map(e, EventDTO.class);
                    dto.setState(e.getState().name());
                    dto.setArtists(e.getArtists().stream().map(a -> modelMapper.map(a, ArtistDTO.class)).toList());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}