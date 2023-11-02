package com.speedtime.chrono.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.speedtime.chrono.model.User;

public interface UserDAO extends CrudRepository<User, Long> {
	
	public User findUserById(Long id);

	public User findByEmail(String email);
	
	public List <User> findAll();
	
}
