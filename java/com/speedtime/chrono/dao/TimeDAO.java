package com.speedtime.chrono.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.speedtime.chrono.model.Time;


public interface TimeDAO extends CrudRepository<Time, Long> {

	public Time getTimeById (Long idTime);
	
	public List findByIdUser (Long idUser);
	
	public List findByIdTrack (Long idTrack);
	
	
	
}