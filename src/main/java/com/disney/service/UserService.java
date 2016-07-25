package com.disney.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disney.domain.User;
import com.disney.exception.ResourceNotFoundException;
import com.disney.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User save(User user) throws Exception{
		return userRepository.save(user);
	}
	
	public void delete(Long userId) throws Exception{
		if(this.findOne(userId) == null){
			throw new ResourceNotFoundException("User with "+userId+" not found");
		}
		userRepository.delete(userId);
	}
	
	public User findOne(Long userId) throws Exception{
		return userRepository.findOne(userId);
	}
	
	public List<User>findAll() throws Exception{
		return userRepository.findAll();
	}
}
