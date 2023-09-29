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

import com.onerb.apiattornatus.dto.AddressDto;
import com.onerb.apiattornatus.dto.AddressSetMainDto;
import com.onerb.apiattornatus.error.NotFoundAddressException;
import com.onerb.apiattornatus.error.NotFoundPersonException;
import com.onerb.apiattornatus.model.Address;
import com.onerb.apiattornatus.model.Person;
import com.onerb.apiattornatus.repository.AddressRepository;
import com.onerb.apiattornatus.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

	@InjectMocks
	AddressService addressService;

	@InjectMocks
	ModelMapper modelMapper = mock(ModelMapper.class);;

	@Mock
	AddressRepository addressRepository;
	
	@Mock
	PersonRepository personRepository;

	private AddressDto addressDto;
	private AddressSetMainDto addressSetNewMainDto;
	private AddressSetMainDto addressSetMainDtoWithPersonIdIncorrect;
	private AddressSetMainDto addressSetMainDtoWithAddressIdIncorrect;
	private Optional<Address> address;
	private Optional<Address> newMainAddress;
	private Person person;
	private UUID id;
	private UUID newMainId;
	private UUID incorrectId;

	@BeforeEach
	public void setUp() {
		LocalDate date = LocalDate.of(1998, 11, 23);
		id = UUID.fromString("2eae6a49-dbc0-493c-89cc-56f14e039cb2");
		newMainId = UUID.fromString("7eae6a49-dbc0-493c-89cc-56f14e039cb2");
		person = new Person(UUID.fromString("12f8af6d-a316-4485-b905-2bd47429020a"), "Breno", date);
		address = Optional.of(new Address(id, "Centro", "48005556", 0, "Cidade Dos Programadores", true, person));
		newMainAddress = Optional
				.of(new Address(id, "Novo Main", "48005556", 0, "Cidade Dos Programadores", true, person));
		addressDto = new AddressDto(id, "Centro", "48005556", "Cidade Dos Programadores", 0, false, person.getId());
		incorrectId = UUID.fromString("9eae6a49-dbc0-493c-89cc-56f14e039cb2");
		addressSetNewMainDto = new AddressSetMainDto(newMainId, person.getId());
		addressSetMainDtoWithPersonIdIncorrect = new AddressSetMainDto(id, incorrectId);
		addressSetMainDtoWithAddressIdIncorrect = new AddressSetMainDto(incorrectId, person.getId());
	}

	@Test
	public void shouldCreateAddressByIdSuccessfully() throws NotFoundPersonException {
		when(addressRepository.save(modelMapper.map(addressDto, Address.class))).thenReturn(address.get());

        Address addressTest=addressService.createAddress(addressDto);
		assertEquals(address.get().getId(), addressTest.getId());
		assertEquals(address.get().getCep(), addressTest.getCep());
		assertEquals(address.get().getCity(), addressTest.getCity());
		assertNotEquals(address.get().getIsMain(), false);// because as he is the first he is the main one
		assertEquals(address.get().getNumber(), addressTest.getNumber());
		assertEquals(address.get().getPublicPlace(), addressTest.getPublicPlace());
		assertEquals(address.get().getPerson(), addressTest.getPerson());
	}

	@Test
	public void shouldSearchAllAddressByPerson() throws NotFoundPersonException {
		when(addressRepository.findAllByPersonId(person.getId())).thenReturn(Collections.singletonList(address.get()));
		
		List<Address> addressTest=addressService.getAllAddressOfPerson(person.getId());
		
		assertEquals(Collections.singletonList(address.get()),addressTest);
		verify(addressRepository).findAllByPersonId(person.getId());// checks if there was interaction with the repository
		verifyNoMoreInteractions(addressRepository); // checks that there has been no further interaction with the repository
	}

	@Test
	public void shouldsetMainAddressOfPersonSuccessfully() throws NotFoundAddressException, NotFoundPersonException {
		when(addressRepository.findAddressByPersonIdAndIsMain(person.getId(), true)).thenReturn(address);
		when(addressRepository.findById(id)).thenReturn(address);
		when(addressRepository.findById(newMainId)).thenReturn(newMainAddress);

		addressService.setMainAddressOfPerson(addressSetNewMainDto);
		Address addressOldMainTest=addressRepository.findById(id).get();
		Address addressNewMainTest=addressRepository.findById(newMainId).get();
		assertEquals(addressOldMainTest.getIsMain(),false );
		assertEquals(addressNewMainTest.getIsMain(),true );

	}

	@Test
	public void shouldThrowsExceptionPersonNotExist() throws NotFoundPersonException {
		when(addressRepository.findById(id)).thenReturn(address);
		
		final NotFoundPersonException exceptionPerson = assertThrows(NotFoundPersonException.class, () -> {
			addressService.setMainAddressOfPerson(addressSetMainDtoWithPersonIdIncorrect);
		});
		assertEquals(exceptionPerson.getMessage(), String.format("Person with id %s not exist", incorrectId));

	}
	@Test
	public void shouldThrowsExceptionAddressNotExist() throws NotFoundAddressException {

		final NotFoundAddressException exceptionAddress = assertThrows(NotFoundAddressException.class, ()->{
			addressService.setMainAddressOfPerson(addressSetMainDtoWithAddressIdIncorrect);
		});
		assertEquals(exceptionAddress.getMessage(), String.format("Address with id %s not exist",incorrectId));
	
	}

	

}
