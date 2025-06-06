package com.example.pdyc2025_junin_individual.controller;

import com.example.pdyc2025_junin_individual.dto.ArtistDTO;
import com.example.pdyc2025_junin_individual.model.*;
import com.example.pdyc2025_junin_individual.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/artists")
public class ArtistController {
    @Autowired
    private ArtistService service;
    @Autowired
    private AdminAuthorizationService authService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ArtistDTO> getAll(@RequestParam(required = false) Genre genre,
                                  @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        return service.getAll(genre).stream()
                .map(artist -> modelMapper.map(artist, ArtistDTO.class))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> get(@PathVariable Long id,
                                         @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        return service.get(id)
                .map(artist -> ResponseEntity.ok(modelMapper.map(artist, ArtistDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ArtistDTO create(@RequestBody ArtistDTO dto,
                            @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Artist saved = service.create(modelMapper.map(dto, Artist.class));
        return modelMapper.map(saved, ArtistDTO.class);
    }

    @PutMapping("/{id}")
    public ArtistDTO update(@PathVariable Long id, @RequestBody ArtistDTO dto,
                            @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        Artist updated = service.update(id, dto.getName(), dto.getGenre());
        return modelMapper.map(updated, ArtistDTO.class);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("Authorization") String token) throws Exception {
        authService.authorize(token);
        service.delete(id);
    }
}