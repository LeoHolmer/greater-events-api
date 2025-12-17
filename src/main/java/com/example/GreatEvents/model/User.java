package com.example.GreatEvents.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToMany
    private Set<Artist> followedArtists = new HashSet<>();

    @ManyToMany
    private Set<Event> favoriteEvents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Artist> getFollowedArtists() {
        return followedArtists;
    }

    public void setFollowedArtists(Set<Artist> followedArtists) {
        this.followedArtists = followedArtists;
    }

    public Set<Event> getFavoriteEvents() {
        return favoriteEvents;
    }

    public void setFavoriteEvents(Set<Event> favoriteEvents) {
        this.favoriteEvents = favoriteEvents;
    }
}