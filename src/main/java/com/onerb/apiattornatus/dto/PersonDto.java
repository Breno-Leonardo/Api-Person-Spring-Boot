package com.onerb.apiattornatus.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PersonDto {
	
	private UUID id;
    private String name;
    private LocalDate birthday;
    
    public PersonDto() {
		super();
		
	}
	public PersonDto(UUID id, String name, LocalDate birthday) {
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
    
    

}
