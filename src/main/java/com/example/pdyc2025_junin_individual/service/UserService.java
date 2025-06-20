package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.Artist;
import com.example.pdyc2025_junin_individual.model.Event;
import com.example.pdyc2025_junin_individual.model.User;

import java.util.List;

public interface UserService {
    User register(User user);
    User findByUsername(String username);
    List<Artist> getFollowedArtists(String username);
    List<Event> getFavoriteEvents(String username);
    List<Event> getUpcomingEventsFromFollowedArtists(String username);
    void followArtist(String username, Long artistId);
    void unfollowArtist(String username, Long artistId);
    void favoriteEvent(String username, Long eventId);
    void unfavoriteEvent(String username, Long eventId);
}