package com.disney.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disney.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
