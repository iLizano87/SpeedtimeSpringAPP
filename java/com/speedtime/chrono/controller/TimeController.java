package com.speedtime.chrono.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.speedtime.chrono.dao.TimeDAO;
import com.speedtime.chrono.model.Time;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class TimeController {

	@Autowired
	TimeDAO timeDAO;

	//Devuelve mejores tiempos de usuario
	@GetMapping(path = "/timesuser/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Time> getUserTimes(@PathVariable Long idUser, Model model) {
		List<Time> times = timeDAO.findByIdUser(idUser);
		
		// Ordenar la lista de times por el campo "time" en orden ascendente
	    Collections.sort(times, Comparator.comparing(Time::getTime));  
	    
	    Set<Long> visitedTrackIds = new HashSet<>();
	    List<Time> firstTimes = new ArrayList<>();
		
	    for (Time time : times) {
	        Long userId = time.getIdTrack();

	        if (!visitedTrackIds.contains(userId)) {
	            visitedTrackIds.add(userId);
	            firstTimes.add(time);
	        }
	    }
	    return firstTimes;
	}

	//Devuelve Lista de records de pista
	@GetMapping(path = "/timestrack/{idTrack}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Time> getTrackTimes(@PathVariable Long idTrack, Model model) {
	    List<Time> times = timeDAO.findByIdTrack(idTrack);

	    // Ordenar la lista de times por el campo "time" en orden ascendente
	    Collections.sort(times, Comparator.comparing(Time::getTime));    

	    Set<Long> visitedUserIds = new HashSet<>();
	    List<Time> firstTimes = new ArrayList<>();

	    for (Time time : times) {
	        Long userId = time.getIdUser();

	        if (!visitedUserIds.contains(userId)) {
	            visitedUserIds.add(userId);
	            firstTimes.add(time);
	        }
	    }
	    return firstTimes;
	}
	    

	//Devuelve Lista de tiempos del usuario X en circuito X
	@GetMapping(path = "/timesusertrack/{idTrack}/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Time> getUserTrackTimes(@PathVariable Long idTrack, @PathVariable Long idUser, Model model) {
		List<Time> times = timeDAO.findByIdTrack(idTrack);
		List<Time> userTimes = new ArrayList<Time>();
		for (int i = 0; i < times.size(); i++) {
			if (times.get(i).getIdUser() == idUser) {
				userTimes.add(times.get(i));
			}
		}
		return userTimes;
	}
	
	//Nuevo tiempo
	@PostMapping("/time")
	public String submitForm(@Valid @RequestBody Time time, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "success";
		}
		timeDAO.save(time);
		return "error";
	}
	
}
