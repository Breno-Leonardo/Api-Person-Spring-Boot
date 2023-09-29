package com.onerb.apiattornatus.dto;

import java.util.UUID;

public class AddressDto {

	private UUID id;
	private String publicPlace;
	private String cep;
	private String city;
	private Integer number;
	private Boolean isMain;
	private UUID personId;
	
	public AddressDto() {
		super();
		
	}

	public AddressDto(UUID id, String publicPlace, String cep, String city, Integer number, Boolean isMain,
			UUID personId) {
		super();
		this.id = id;
		this.publicPlace = publicPlace;
		this.cep = cep;
		this.city = city;
		this.number = number;
		this.isMain = isMain;
		this.personId = personId;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}
	
	
}
