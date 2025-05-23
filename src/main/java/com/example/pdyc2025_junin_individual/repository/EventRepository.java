package com.example.pdyc2025_junin_individual.repository;

import com.example.pdyc2025_junin_individual.model.Event;
import com.example.pdyc2025_junin_individual.model.EventState;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByState(EventState state);
}