package com.disney.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disney.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
