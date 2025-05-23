package com.example.pdyc2025_junin_individual.repository;

import com.example.pdyc2025_junin_individual.model.Artist;
import com.example.pdyc2025_junin_individual.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByGenre(Genre genre);
}