package com.speedtime.chrono.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.speedtime.chrono.dao.TrackDAO;
import com.speedtime.chrono.model.Track;
import com.speedtime.chrono.model.User;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class TrackController {

	@Autowired
	TrackDAO trackDAO;

	//Obtiene track por id
	@GetMapping("/track/{id}")
	public Track getTrackById(@PathVariable Long id, Model model) {
		Track track = trackDAO.getTrackById(id);
		if (track == null) {
			return null;
		}
		model.addAttribute("track", track);
		return track;
	}
	
	//Crea Track
	@PostMapping("/track")
	public String submitTrack(@Valid @RequestBody Track track, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "success";
		}
		trackDAO.save(track);
		return "error";
	}

	//Obtiene track por id en JSON
	@GetMapping(path = "/trackjson/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Optional<Track> getTrackByIdJson(@PathVariable Long id, Model model) {
		// Buscar usuario por email y pass en tu base de datos
		Optional<Track> track = trackDAO.findById(id);
		if (track == null) {
			// Si no se encuentra el track, null
			return null;
		}
		// Si se encuentra el track, agregarlo al modelo y mostrar la vista
		model.addAttribute("track", track);
		return track;
	}

	//Obtiene lista de track en JSON
	@GetMapping(path = "/alltracks", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Track> getAllTracks() {
		List<Track> tracklist = trackDAO.findAll();
		return tracklist;
	}

	//Borra track por id
	@DeleteMapping("/track/delete/{id}")
	public String deleteTrack(@PathVariable Long id, Model model) {
		// Buscar usuario por email y pass en tu base de datos
		Track track = trackDAO.getTrackById(id);
		if (track == null) {
			// Si no se encuentra el track, error
			return "error";
		}
		// Si se encuentra el track, eliminar
		trackDAO.delete(track);
		return "success";
	}

	//Actualiza datos de track por id
	@PutMapping("/track/{trackId}")
	public ResponseEntity<?> updateTrack(@PathVariable Long trackId, @RequestBody Track updatedTrack) {
		try {
			Optional<Track> optionalTrack = trackDAO.findById(trackId);
			if (optionalTrack.isPresent()) {
				Track existingTrack = optionalTrack.get();
				if (updatedTrack.getName() != null && !updatedTrack.getName().isEmpty()) {
					existingTrack.setName(updatedTrack.getName());
				}
				if (updatedTrack.getIniLat() != 0) {
					existingTrack.setIniLat(updatedTrack.getIniLat());
				}
				if (updatedTrack.getIniLong() != 0) {
					existingTrack.setIniLong(updatedTrack.getIniLong());
				}
				if (updatedTrack.getP1Lat() != 0) {
					existingTrack.setP1Lat(updatedTrack.getP1Lat());
				}
				if (updatedTrack.getP1Long() != 0) {
					existingTrack.setP1Long(updatedTrack.getP1Long());
				}
				if (updatedTrack.getP2Lat() != 0) {
					existingTrack.setP2Lat(updatedTrack.getP2Lat());
				}
				if (updatedTrack.getP2Long() != 0) {
					existingTrack.setP2Long(updatedTrack.getP2Long());
				}
				trackDAO.save(existingTrack);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}