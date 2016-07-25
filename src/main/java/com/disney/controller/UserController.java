package com.disney.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;

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
import com.disney.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> post(@RequestBody User user) throws Exception {
		user.setCreatedDate(LocalDateTime.now());
		return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
	}

	@RequestMapping(value = "{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> get(@PathVariable("userId") Long userId) throws Exception {
		return new ResponseEntity<User>(userService.findOne(userId), HttpStatus.OK);
	}

	@RequestMapping(value = "{userId}/events", method = RequestMethod.GET)
	public ResponseEntity<Set<Event>> getEvents(@PathVariable("userId") Long userId) throws Exception {
		return new ResponseEntity<Set<Event>>(userService.findOne(userId).getEvents(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAll() throws Exception {
		return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<User> put(@RequestBody User user) throws Exception {
		user.setUpdatedDate(LocalDateTime.now());
		User updatedUser = userService.save(user);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}

	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> delete(@PathParam("userId") Long userId) throws Exception {
		userService.delete(userId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
