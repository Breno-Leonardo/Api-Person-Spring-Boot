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

import com.onerb.apiattornatus.model.Address;
import com.onerb.apiattornatus.model.Person;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AddressRepositoryTest {

	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	private Optional<Address> addressX;
	private Optional<Address> addressY;
	private Person person;

	@BeforeEach
	public void setUp() {
		LocalDate date = LocalDate.of(1998, 11, 23);
		person= new Person(UUID.randomUUID(),"Breno",date);
		addressX = Optional.of(new Address(UUID.randomUUID(), "Centro", "48005556", 0, "Cidade Dos Programadores", true, person));
		addressY = Optional.of(new Address(UUID.randomUUID(), "avenida", "48005556", 0, "Cidade Dos Programadores", false, person));
		personRepository.save(person);
	}
	
	@Test
	public void shouldReturnAddressById() {
		
		addressRepository.save(addressX.get());
		addressRepository.save(addressY.get());
		Optional<Address> result= addressRepository.findById(addressX.get().getId());
		assertEquals(addressX.get().getId(), result.get().getId());
	}
	
	@Test
	public void shouldReturnAllAddressUsingThePersonId() {
		
		addressRepository.save(addressX.get());
		addressRepository.save(addressY.get());
		List<Address> results= addressRepository.findAllByPersonId(person.getId());
		List<Address> tests=new ArrayList<Address>();
		tests.add(addressX.get());
		tests.add(addressY.get());
		assertEquals(results.containsAll(tests), tests.containsAll(results));
	}
	
	@Test
	public void shouldReturnAllAddressUsingThePersonIdAndIsMain() {
		addressRepository.save(addressX.get());
		addressRepository.save(addressY.get());
		Optional<Address> result= addressRepository.findAddressByPersonIdAndIsMain(person.getId(),true);
		assertEquals(result.get().getId(), addressX.get().getId());
	}
}
