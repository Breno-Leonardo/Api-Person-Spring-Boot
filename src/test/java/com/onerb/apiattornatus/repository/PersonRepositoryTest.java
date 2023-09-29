package com.onerb.apiattornatus.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.onerb.apiattornatus.model.Person;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PersonRepositoryTest {
	
	@Autowired
	PersonRepository personRepository;
	
	private Optional<Person> personX;
	private Optional<Person> personY;
	
	@BeforeEach
	public void setUp() {
		LocalDate date = LocalDate.of(1998, 11, 23);
		personX= Optional.of(new Person(UUID.randomUUID(),"X",date));
		personY= Optional.of(new Person(UUID.randomUUID(),"Y",date));
	}
	
	@Test
	public void shouldReturnPersonById() {
		
		personRepository.save(personX.get());
		personRepository.save(personY.get());
		Optional<Person> result= personRepository.findById(personX.get().getId());
		assertEquals(personX.get().getId(), result.get().getId());
	}
	
	@Test
	public void shouldReturnAllPerson() {
		
		personRepository.save(personX.get());
		personRepository.save(personY.get());
		List<Person> results= personRepository.findAll();
		List<Person> tests=new ArrayList<Person>();
		tests.add(personX.get());
		tests.add(personY.get());
		assertEquals(results.containsAll(tests), tests.containsAll(results));
	}
	

}
