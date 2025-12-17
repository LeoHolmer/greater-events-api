package com.example.GreatEvents.controller;

import com.example.GreatEvents.dto.ArtistDTO;
import com.example.GreatEvents.model.*;
import com.example.GreatEvents.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/artists")
@Tag(name = "Artists", description = "API para gestión de artistas (solo administradores)")
@SecurityRequirement(name = "bearerAuth")
public class ArtistController {
    @Autowired
    private ArtistService service;
    @Autowired
    private AdminAuthorizationService authService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @Operation(summary = "Obtener todos los artistas", description = "Obtiene una lista de todos los artistas con filtro opcional por género")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de artistas obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public List<ArtistDTO> getAll(@Parameter(description = "Filtrar por género musical") @RequestParam(required = false) Genre genre,
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
    @Operation(summary = "Crear un nuevo artista", description = "Crea un nuevo artista en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artista creado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
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