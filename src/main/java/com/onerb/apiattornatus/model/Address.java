package com.onerb.apiattornatus.model;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "address", indexes = { @Index(name = "idx_person_id", columnList = "id, person_id", unique = true) })
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private UUID id;

	@Column(name = "public_place", unique = false, nullable = false, updatable = true, length = 150)
	private String publicPlace;

	@Column(name = "cep", unique = false, nullable = false, updatable = true)
	private String cep;

	@Column(name = "number", unique = false, nullable = false, updatable = true)
	private Integer number;

	@Column(name = "city", unique = false, nullable = false, updatable = true)
	private String city;

	@Column(name = "is_main", unique = false, nullable = false, updatable = true)
	private Boolean isMain;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JoinColumn(name = "person_id",referencedColumnName = "id" , unique = false, nullable = false, updatable = false)
	private Person person;

	public Address() {
		super();

	}

	public Address(UUID id, String publicPlace, String cep, Integer number, String city, Boolean isMain,
			Person person) {
		super();
		this.id = id;
		this.publicPlace = publicPlace;
		this.cep = cep;
		this.number = number;
		this.city = city;
		this.isMain = isMain;
		this.person = person;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getPublicPlace() {
		return publicPlace;
	}

	public void setPublicPlace(String publicPlace) {
		this.publicPlace = publicPlace;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
