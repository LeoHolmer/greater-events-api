package com.example.GreatEvents.repository;

import com.example.GreatEvents.model.Artist;
import com.example.GreatEvents.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByGenre(Genre genre);
}