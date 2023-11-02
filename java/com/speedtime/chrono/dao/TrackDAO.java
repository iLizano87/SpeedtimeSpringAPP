package com.speedtime.chrono.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.speedtime.chrono.model.Track;


public interface TrackDAO extends CrudRepository<Track, Long>{
	
	public Track getTrackById(Long id);
	
	public List <Track> findAll();

}