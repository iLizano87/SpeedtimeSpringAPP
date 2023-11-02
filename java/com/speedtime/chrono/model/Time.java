package com.speedtime.chrono.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Time implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long idUser;

	private Long idTrack;

	private Long time;

	private Long p1time;

	private Long p2time;

	private String kmh;

	private String date;

	public Time() {
		super();
	}

	public Time(Long id, Long idUser, Long idTrack, Long time, Long p1time, Long p2time, String kmh, String date) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.idTrack = idTrack;
		this.time = time;
		this.p1time = p1time;
		this.p2time = p2time;
		this.kmh = kmh;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdTrack() {
		return idTrack;
	}

	public void setIdTrack(Long idTrack) {
		this.idTrack = idTrack;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getP1time() {
		return p1time;
	}

	public void setP1time(Long p1time) {
		this.p1time = p1time;
	}

	public Long getP2time() {
		return p2time;
	}

	public void setP2time(Long p2time) {
		this.p2time = p2time;
	}

	public String getKmh() {
		return kmh;
	}

	public void setKmh(String kmh) {
		this.kmh = kmh;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
