package com.disney.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.disney.domain.Event;
import com.disney.domain.User;
import com.disney.service.EventService;

@RestController
@RequestMapping("event")
public class EventController {

	@Autowired
	private EventService eventService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Event> post(@RequestBody Event event) throws Exception {
		event.setCreatedDate(LocalDateTime.now());
		return new ResponseEntity<Event>(eventService.save(event), HttpStatus.CREATED);
	}

	@RequestMapping(value = "{eventId}", method = RequestMethod.GET)
	public ResponseEntity<Event> get(@PathVariable("eventId") Long eventId) throws Exception {
		return new ResponseEntity<Event>(eventService.findOne(eventId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getAll() throws Exception {
		return new ResponseEntity<List<Event>>(eventService.findAll(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Event> put(@RequestBody Event event) throws Exception {
		event.setUpdatedDate(LocalDateTime.now());
		return new ResponseEntity<Event>(eventService.save(event), HttpStatus.OK);
	}

	@RequestMapping(value = "{eventId}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("eventId") Long eventId) throws Exception {
		eventService.delete(eventId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "{eventId}/users", method = RequestMethod.GET)
	public ResponseEntity<Set<User>> getUsersForEvent(@PathVariable("eventId") Long eventId) throws Exception {
		return new ResponseEntity<Set<User>>(eventService.findOne(eventId).getUsers(), HttpStatus.OK);
	}

	@RequestMapping(value = "{eventId}/users/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> addUsertoEvent(@PathVariable("eventId") Long eventId,
			@PathVariable("userId") Long userId) throws Exception {
		eventService.addUser(eventId, userId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "{eventId}/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> removeUserFromEvent(@PathVariable("eventId") Long eventId,
			@PathVariable("userId") Long userId) throws Exception {
		eventService.removeUser(eventId, userId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
