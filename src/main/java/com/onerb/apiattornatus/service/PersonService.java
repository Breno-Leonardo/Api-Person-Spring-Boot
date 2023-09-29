package com.onerb.apiattornatus.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onerb.apiattornatus.dto.PersonDto;
import com.onerb.apiattornatus.error.NotFoundPersonException;
import com.onerb.apiattornatus.model.Person;
import com.onerb.apiattornatus.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	ModelMapper modelMapper;

	public Person createPerson(PersonDto personDto) {
		Person person = personRepository.save(modelMapper.map(personDto, Person.class));
		return person;
	}

	public Person editPerson(PersonDto personDto) throws NotFoundPersonException  {
		// verify if exists
		Optional<Person> personOptional = personRepository.findById(personDto.getId());
		
		verifyIfPersonExists(personOptional,personDto.getId());
		
		Person person = personRepository.save(modelMapper.map(personDto, Person.class));
		return person;
	}

	public Person getOnePerson(UUID id) throws NotFoundPersonException {

		Optional<Person> personOptional = personRepository.findById(id);

		verifyIfPersonExists(personOptional,id);

		return personOptional.get();		
	}
	
	public List<Person> getAllPerson() {
		
		return personRepository.findAll();		
	}
	
	private void verifyIfPersonExists(Optional<Person> personOptional, UUID id) throws NotFoundPersonException {
		if (personOptional.isEmpty()) {
			throw new NotFoundPersonException(id);
		}		
	}
}
