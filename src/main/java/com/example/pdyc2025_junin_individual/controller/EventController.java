package com.example.pdyc2025_junin_individual.controller;

import com.example.pdyc2025_junin_individual.dto.ArtistDTO;
import com.example.pdyc2025_junin_individual.dto.EventDTO;
import com.example.pdyc2025_junin_individual.model.*;
import com.example.pdyc2025_junin_individual.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
public class EventController {
    @Autowired
    private EventService service;
    @Autowired
    private AdminAuthorizationService authService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<EventDTO> getAll(@RequestParam(required = false) EventState state,
                                 @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        return service.getAll(state).stream()
                .map(event -> {
                    EventDTO dto = modelMapper.map(event, EventDTO.class);
                    dto.setState(event.getState().name());
                    dto.setArtists(event.getArtists().stream()
                            .map(artist -> modelMapper.map(artist, ArtistDTO.class))
                            .toList());
                    return dto;
                }).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> get(@PathVariable Long id,
                                        @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        return service.get(id)
                .map(event -> {
                    EventDTO dto = modelMapper.map(event, EventDTO.class);
                    dto.setState(event.getState().name());
                    dto.setArtists(event.getArtists().stream()
                            .map(artist -> modelMapper.map(artist, ArtistDTO.class))
                            .toList());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EventDTO create(@RequestBody EventDTO dto,
                           @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Event event = modelMapper.map(dto, Event.class);
        Event saved = service.create(event);
        EventDTO response = modelMapper.map(saved, EventDTO.class);
        response.setState(saved.getState().name());
        return response;
    }

    @PutMapping("/{id}")
    public EventDTO update(@PathVariable Long id, @RequestBody EventDTO dto,
                           @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Event updated = service.update(id, dto.getName(), dto.getDescription(), dto.getStartDate());
        EventDTO response = modelMapper.map(updated, EventDTO.class);
        response.setState(updated.getState().name());
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        service.delete(id);
    }

    @PostMapping("/{id}/artists")
    public EventDTO addArtist(@PathVariable Long id, @RequestBody ArtistDTO dto,
                              @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Event updated = service.addArtist(id, dto.getId());
        EventDTO response = modelMapper.map(updated, EventDTO.class);
        response.setState(updated.getState().name());
        response.setArtists(updated.getArtists().stream()
                .map(artist -> modelMapper.map(artist, ArtistDTO.class))
                .toList());
        return response;
    }

    @DeleteMapping("/{id}/artist/{artistId}")
    public EventDTO removeArtist(@PathVariable Long id, @PathVariable Long artistId,
                                 @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Event updated = service.removeArtist(id, artistId);
        EventDTO response = modelMapper.map(updated, EventDTO.class);
        response.setState(updated.getState().name());
        response.setArtists(updated.getArtists().stream()
                .map(artist -> modelMapper.map(artist, ArtistDTO.class))
                .toList());
        return response;
    }

    @PutMapping("/{id}/confirmed")
    public EventDTO confirm(@PathVariable Long id,
                            @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Event updated = service.confirm(id);
        EventDTO response = modelMapper.map(updated, EventDTO.class);
        response.setState(updated.getState().name());
        return response;
    }

    @PutMapping("/{id}/rescheduled")
    public EventDTO reschedule(@PathVariable Long id, @RequestBody EventDTO dto,
                               @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Event updated = service.reschedule(id, dto.getStartDate());
        EventDTO response = modelMapper.map(updated, EventDTO.class);
        response.setState(updated.getState().name());
        return response;
    }

    @PutMapping("/{id}/canceled")
    public EventDTO cancel(@PathVariable Long id,
                           @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Event updated = service.cancel(id);
        EventDTO response = modelMapper.map(updated, EventDTO.class);
        response.setState(updated.getState().name());
        return response;
    }
}