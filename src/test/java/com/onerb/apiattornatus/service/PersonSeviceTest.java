package com.onerb.apiattornatus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.onerb.apiattornatus.dto.PersonDto;
import com.onerb.apiattornatus.error.NotFoundPersonException;
import com.onerb.apiattornatus.model.Person;
import com.onerb.apiattornatus.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonSeviceTest {

	@InjectMocks
	PersonService personService;
	
	@InjectMocks
	ModelMapper modelMapper=mock(ModelMapper.class);

	@Mock
	PersonRepository personRepository;
	
	private PersonDto personDto;
	private PersonDto personDtoEdit;
	private PersonDto personDtoWithIncorrectId;
	private Optional<Person> person;
	private Optional<Person> personEdit;
	private UUID id;
	private UUID incorrectId;
	
	@BeforeEach
	public void setUp() {
		LocalDate date= LocalDate.of(1998, 11, 23);
		id=UUID.fromString("2eae6a49-dbc0-493c-89cc-56f14e039cb2");
		person = Optional.of(new Person(id, "Breno",date));
		personEdit = Optional.of(new Person(id, "Breno Edit",date));
		personDto= new PersonDto(id, "Breno",date);
		personDtoEdit= new PersonDto(id, "Breno Edit",date);
		incorrectId=UUID.fromString("3eae6a49-dbc0-493c-89cc-56f14e039cb2");
		personDtoWithIncorrectId= new PersonDto(incorrectId, "Breno",date);

	}
	
	@Test
	public void shouldCreatePersonByIdSuccessfully() throws NotFoundPersonException {
        when(personRepository.save(modelMapper.map(personDto, Person.class))).thenReturn(person.get());
		Person personTest=personService.createPerson(personDto);
		assertEquals(person.get().getId(), personTest.getId());
		assertEquals(person.get().getName(),personTest.getName());
		assertEquals(person.get().getBirthday(), personTest.getBirthday());
	}
	
	@Test
	public void shouldSearchPersonByIdSuccessfully() throws NotFoundPersonException {
		when(personRepository.findById(person.get().getId())).thenReturn(person);
		
		Person personTest=personService.getOnePerson(person.get().getId());
		
		assertEquals(person.get(),personTest);
		verify(personRepository).findById(person.get().getId());// checks if there was interaction with the repository
		verifyNoMoreInteractions(personRepository); // checks that there has been no further interaction with the repository
	}
	
	@Test
	public void shouldThrowsExceptionPersonNotExist() throws NotFoundPersonException {
		Optional<Person> personTest=personRepository.findById(incorrectId);
		assertEquals(personTest.isEmpty(), true);
		
		final NotFoundPersonException exceptionInGetOnePerson = assertThrows(NotFoundPersonException.class, ()->{
			personService.getOnePerson(incorrectId);
		});
		assertEquals(exceptionInGetOnePerson.getMessage(), String.format("Person with id %s not exist",incorrectId));
	
		
		final NotFoundPersonException exceptionInEditPerson = assertThrows(NotFoundPersonException.class, ()->{
			personService.editPerson(personDtoWithIncorrectId);
		});
		assertEquals(exceptionInEditPerson.getMessage(), String.format("Person with id %s not exist",incorrectId));
	
	}

	@Test
	public void shouldSearchAllPerson() throws NotFoundPersonException {
		when(personRepository.findAll()).thenReturn(Collections.singletonList(person.get()));
		
		List<Person> personTest=personService.getAllPerson();
		
		assertEquals(Collections.singletonList(person.get()),personTest);
		verify(personRepository).findAll();// checks if there was interaction with the repository
		verifyNoMoreInteractions(personRepository); // checks that there has been no further interaction with the repository
	}
	
	@Test
	public void shouldEditPersonByIdSuccessfully() throws NotFoundPersonException {
		 when(personRepository.save(modelMapper.map(personDtoEdit, Person.class))).thenReturn(personEdit.get());
		 when(personRepository.findById(personEdit.get().getId())).thenReturn(person);
			
		 	Person personTest=personService.editPerson(personDtoEdit);
		 	assertEquals(person.get().getId(), personTest.getId());
			assertNotEquals(person.get().getName(),personTest.getName());
			assertEquals(person.get().getBirthday(), personTest.getBirthday());
	}
	
}
