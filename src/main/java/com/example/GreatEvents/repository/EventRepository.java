package com.example.GreatEvents.repository;

import com.example.GreatEvents.model.Event;
import com.example.GreatEvents.model.EventState;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByState(EventState state);
}