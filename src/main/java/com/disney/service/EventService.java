package com.disney.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disney.domain.Event;
import com.disney.domain.User;
import com.disney.exception.ResourceNotFoundException;
import com.disney.repositories.EventRepository;
import com.disney.repositories.UserRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	public void addUser(Long eventId, Long userId) throws Exception {
		Event event = eventRepository.findOne(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Event with " + eventId + " not found");
		}
		User user = userRepository.findOne(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User with " + userId + " is not under event " + eventId);
		}
		event.getUsers().add(user);
		eventRepository.save(event);
	}

	public void removeUser(Long eventId, Long userId) throws Exception {
		Event event = eventRepository.findOne(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Event with " + eventId + " not found");
		}
		Optional<User> userOpt = event.getUsers().stream().filter(u -> u.getId().equals(userId)).findFirst();
		if (!userOpt.isPresent()) {
			throw new ResourceNotFoundException("User with " + userId + " is not under event " + eventId);
		}

		event.getUsers().remove(userOpt.get());
		eventRepository.save(event);
	}

	public Event save(Event event) throws Exception {
		return eventRepository.save(event);
	}

	public void delete(Long eventId) throws Exception {
		Event event = eventRepository.findOne(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Event with " + eventId + " not found");
		}
		eventRepository.delete(eventId);
	}

	public Event findOne(Long eventId) throws Exception {
		return eventRepository.findOne(eventId);
	}
	
	public List<Event>findAll() throws Exception{
		return eventRepository.findAll();
	}

}
