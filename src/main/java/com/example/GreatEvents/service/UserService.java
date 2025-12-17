package com.example.GreatEvents.service;

import com.example.GreatEvents.model.Artist;
import com.example.GreatEvents.model.Event;
import com.example.GreatEvents.model.User;

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