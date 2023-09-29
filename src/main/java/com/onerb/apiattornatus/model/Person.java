package com.onerb.apiattornatus.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private UUID id;

	@Column(name = "name", unique = false, nullable = false, updatable = true, length = 75)
	private String name;

	@JsonFormat(pattern = "dd/MM/yyyy",shape = JsonFormat.Shape.STRING)
	@Column(name = "birthday", unique = false, nullable = false, updatable = true)
	private LocalDate birthday;


	public Person() {
		super();

	}

	public Person(UUID id, String name, LocalDate birthday) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
