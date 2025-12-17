package com.example.GreatEvents.service;

import com.example.GreatEvents.model.Artist;
import com.example.GreatEvents.model.Event;
import com.example.GreatEvents.model.EventState;
import com.example.GreatEvents.model.User;
import com.example.GreatEvents.repository.ArtistRepository;
import com.example.GreatEvents.repository.EventRepository;
import com.example.GreatEvents.repository.UserRepository;
import com.example.GreatEvents.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private ArtistRepository artistRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public void followArtist(String username, Long artistId) {
        User user = findByUsername(username);
        Artist artist = artistRepository.findById(artistId).orElseThrow();
        user.getFollowedArtists().add(artist);
        userRepository.save(user);
    }

    public void unfollowArtist(String username, Long artistId) {
        User user = findByUsername(username);
        Artist artist = artistRepository.findById(artistId).orElseThrow();
        user.getFollowedArtists().remove(artist);
        userRepository.save(user);
    }

    public void favoriteEvent(String username, Long eventId) {
        User user = findByUsername(username);
        Event event = eventRepository.findById(eventId).orElseThrow();
        user.getFavoriteEvents().add(event);
        userRepository.save(user);
    }

    public void unfavoriteEvent(String username, Long eventId) {
        User user = findByUsername(username);
        Event event = eventRepository.findById(eventId).orElseThrow();
        user.getFavoriteEvents().remove(event);
        userRepository.save(user);
    }

    public List<Artist> getFollowedArtists(String username) {
        return new ArrayList<>(findByUsername(username).getFollowedArtists());
    }

    public List<Event> getFavoriteEvents(String username) {
        return findByUsername(username).getFavoriteEvents().stream()
                .filter(e -> !e.getStartDate().isBefore(LocalDate.now()) &&
                        (e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED))
                .toList();
    }

    public List<Event> getUpcomingEventsFromFollowedArtists(String username) {
        User user = findByUsername(username);
        Set<Artist> followed = user.getFollowedArtists();
        return eventRepository.findAll().stream()
                .filter(e -> !e.getStartDate().isBefore(LocalDate.now()) &&
                        (e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED) &&
                        e.getArtists().stream().anyMatch(followed::contains))
                .sorted(Comparator.comparing(Event::getStartDate))
                .toList();
    }
}