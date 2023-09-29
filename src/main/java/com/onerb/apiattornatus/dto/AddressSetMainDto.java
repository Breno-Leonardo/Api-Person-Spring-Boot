package com.onerb.apiattornatus.dto;

import java.util.UUID;

public class AddressSetMainDto {

	private UUID id;
	private UUID personId;
	
	public AddressSetMainDto() {
		super();
		
	}

	public AddressSetMainDto(UUID id, UUID personId) {
		super();
		this.id = id;
		this.personId = personId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	

}
