package com.example.pdyc2025_junin_individual.controller;

import com.example.pdyc2025_junin_individual.dto.ArtistDTO;
import com.example.pdyc2025_junin_individual.dto.EventDTO;
import com.example.pdyc2025_junin_individual.dto.UserLoginDTO;
import com.example.pdyc2025_junin_individual.dto.UserRegisterDTO;
import com.example.pdyc2025_junin_individual.model.User;
import com.example.pdyc2025_junin_individual.service.UserAuthenticationService;
import com.example.pdyc2025_junin_individual.service.UserAuthorizationService;
import com.example.pdyc2025_junin_individual.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired private UserService userService;
    @Autowired private UserAuthenticationService authService;
    @Autowired private UserAuthorizationService authorizationService;
    @Autowired private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO dto) {
        User user = modelMapper.map(dto, User.class);
        userService.register(user);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody UserLoginDTO dto) {
        try {
            User user = modelMapper.map(dto, User.class);
            String token = authService.authenticate(user);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @PostMapping("/follow/{artistId}")
    public void follow(@RequestHeader("Authorization") String token, @PathVariable Long artistId) throws Exception {
        String username = authorizationService.authorize(token).getUsername();
        userService.followArtist(username, artistId);
    }

    @DeleteMapping("/unfollow/{artistId}")
    public void unfollow(@RequestHeader("Authorization") String token, @PathVariable Long artistId) throws Exception {
        String username = authorizationService.authorize(token).getUsername();
        userService.unfollowArtist(username, artistId);
    }

    @GetMapping("/following")
    public List<ArtistDTO> following(@RequestHeader("Authorization") String token) throws Exception {
        String username = authorizationService.authorize(token).getUsername();
        return userService.getFollowedArtists(username).stream()
                .map(a -> modelMapper.map(a, ArtistDTO.class)).toList();
    }

    @PostMapping("/favorite/{eventId}")
    public void favorite(@RequestHeader("Authorization") String token, @PathVariable Long eventId) throws Exception {
        String username = authorizationService.authorize(token).getUsername();
        userService.favoriteEvent(username, eventId);
    }

    @DeleteMapping("/unfavorite/{eventId}")
    public void unfavorite(@RequestHeader("Authorization") String token, @PathVariable Long eventId) throws Exception {
        String username = authorizationService.authorize(token).getUsername();
        userService.unfavoriteEvent(username, eventId);
    }

    @GetMapping("/favorites")
    public List<EventDTO> favorites(@RequestHeader("Authorization") String token) throws Exception {
        String username = authorizationService.authorize(token).getUsername();
        return userService.getFavoriteEvents(username).stream()
                .map(e -> {
                    EventDTO dto = modelMapper.map(e, EventDTO.class);
                    dto.setState(e.getState().name());
                    dto.setArtists(e.getArtists().stream()
                            .map(a -> modelMapper.map(a, ArtistDTO.class)).toList());
                    return dto;
                }).toList();
    }

    @GetMapping("/feed")
    public List<EventDTO> feed(@RequestHeader("Authorization") String token) throws Exception {
        String username = authorizationService.authorize(token).getUsername();
        return userService.getUpcomingEventsFromFollowedArtists(username).stream()
                .map(e -> {
                    EventDTO dto = modelMapper.map(e, EventDTO.class);
                    dto.setState(e.getState().name());
                    dto.setArtists(e.getArtists().stream()
                            .map(a -> modelMapper.map(a, ArtistDTO.class)).toList());
                    return dto;
                }).toList();
    }
}